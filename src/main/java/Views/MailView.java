package Views;

import Users.User;
import Vacations.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MailView extends ARegisteredView {

    public AnchorPane ap_mail;
    public Button btn_authorize;
    public ListView<String> waitingForSellAuthorizationList;
    public ListView<String> confirmationsSellList;
    public ListView<String> confirmationsTradeList;
    public Button btn_approvePayment;
    public Button btn_approveTrade;

    @Override
    public void prepareView(User username, boolean isManager) {
        _loggedUser = username;
        _manager = isManager;

        waitingForSellAuthorizationList.getItems().clear();
        confirmationsSellList.getItems().clear();
        confirmationsTradeList.getItems().clear();

        ArrayList<VacationRequest> waitingForAuthorization = _controller.getVacationsForApproval(username.get_userName());
        String buyer, date, id, full, buyer2, price, tickets;
        if (waitingForAuthorization != null) {
            for (VacationRequest v : waitingForAuthorization) {
                id = v._vacationId;
                buyer = v._buyer;
                date = v._date;
                full = "VacationID: " + id + "\t" + "Buyer: " + buyer + "\t" + "Date: " + date;
                waitingForSellAuthorizationList.getItems().add(full);
            }
        }

        ArrayList<VacationSell> vacationsToPay = _controller.getVacationForApprovePayment(username.get_userName());
        if (vacationsToPay != null) {
            for (VacationSell v : vacationsToPay) {
                id = v.get_vacationId();
                buyer2 = v.get_buyer();
                date = v.get_date();
                price = v.get_price();
                full = "VacationID: " + id + "\t" + "Buyer: " + buyer2 + "\t" + "Date: " + date + "\t" + "Price: " + price;
                confirmationsSellList.getItems().add(full);
            }
        }

        Map<String, List<Vacation>>  vacationsToTrade = _controller.vacationsTradesForApprove(username.get_userName());
        if (vacationsToTrade != null) {
            String vacationID = "";
            for (String vID :
                    vacationsToTrade.keySet()) {

                List<Vacation> vacationsPropose = vacationsToTrade.get(vID);
                for (Vacation v : vacationsPropose) {
                    id = v._id;

                    full = "Your Vacation: " + vID + "    Trade Offer:  " + "VacationID: " + id + "    " + "Seller:  " + v._sellingUser
                            + "    Destination: " + v._destination +   "    Origin: " + v._origin +  "    Departure: " + v._departureDate
                            + "    Return: " + v._returnDate + "    Airline: " + v._airline + "    Tickets: " + v._quantity;
                    confirmationsTradeList.getItems().add(full);
                }
            }
        }
    }

    public void setSelectApprove (MouseEvent event){
        event.consume();
        String entry = waitingForSellAuthorizationList.getSelectionModel().getSelectedItem();
        if (entry != null) {
            int start = entry.indexOf(':');
            int end = entry.indexOf("Buyer");
            String id = entry.substring(start + 2, end - 1);
            entry = entry.substring(end);
            start = 7;
            end = entry.indexOf("Date");
            String buyer = entry.substring(start, end - 1);

            String response = _controller.approveVacation(_loggedUser.get_userName(), id, buyer);
            if (response.contains("approved"))
                popInfo(response);
            else if (response.contains("error"))
                popProblem(response);
        }
        else
            popProblem("No selection was made");

        prepareView(_loggedUser, _manager);
    }

    //not in use in this part!
    public void setSelectPay (MouseEvent event){
        String entry = confirmationsSellList.getSelectionModel().getSelectedItem();
        if (entry != null) {
            String id, username, seller, price;
            int start = 12;
            int end = entry.indexOf("Seller");
            id = entry.substring(start, end - 1);
            entry = entry.substring(end);
            start = 8 + entry.indexOf("Seller");
            end = entry.indexOf("Date");
            seller = entry.substring(start, end - 1);
            entry = entry.substring(end);
            start = 7 + entry.indexOf("Price");
            end = entry.length();
            price = entry.substring(start, end);

            try {
                Stage pStage = new Stage();
                FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxmls/paymentXML.fxml"));
                pStage.setTitle("Vacation4U");
                Parent root = fxml.load();

                PaymentView v = fxml.getController();
                v.set_controller(_controller);
                v._loggedUser = _loggedUser;
                v._manager = _manager;
                v._price = price;
                v._id = id;
                v._seller = seller;
                v.prepareView(_loggedUser, _manager);

                pStage.setScene(new Scene(root, 900, 600));
                pStage.initModality(Modality.APPLICATION_MODAL);
                pStage.showAndWait();
                pStage.setResizable(false);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        else
            popProblem("No selection was made");


        prepareView(_loggedUser, _manager);
    }


    public void confirmPayment(MouseEvent mouseEvent){
        mouseEvent.consume();
        String entry = confirmationsSellList.getSelectionModel().getSelectedItem();
        if (entry != null) {
            String id, buyer, seller, price;
            int start = 12;
            int end = entry.indexOf("Buyer");
            id = entry.substring(start, end - 1);
            entry = entry.substring(end);
            start = 7 + entry.indexOf("Buyer");
            end = entry.indexOf("Date");
            buyer = entry.substring(start, end - 1);
            entry = entry.substring(end);
            start = 7 + entry.indexOf("Price");
            end = entry.length();
            price = entry.substring(start, end);
            seller = _loggedUser.get_userName();

            String response = _controller.confirmPayment(id, seller, buyer, price);
            if (response.toLowerCase().contains("error")){
                popProblem(response);
            }
            else{
                popInfo(response);
            }
        }
        else{
            popProblem("No selection was made");
        }

        prepareView(_loggedUser, _manager);
    }

    public void approveTrade(MouseEvent mouseEvent) {
        mouseEvent.consume();

        String entry = confirmationsTradeList.getSelectionModel().getSelectedItem();
        VacationTrade vTrade = new VacationTrade();
        int start = entry.indexOf(":");
        int end = entry.indexOf("Trade");
        vTrade.set_vID1(entry.substring(start+1, end).trim());
        vTrade.set_user1(_loggedUser.get_userName());

        start = entry.indexOf("ID:");
        end = entry.indexOf("Seller");
        vTrade.set_vID2(entry.substring(start+3, end).trim());

        start = end;
        end = entry.indexOf("Destination");
        vTrade.set_user2(entry.substring(start+7, end).trim());

        String response = _controller.approveTrade(vTrade);
        if (response.equals("ok")){
            popInfo("Trade has been made!");
        }
        else{
            popProblem(response);
        }

        prepareView(_loggedUser, _manager);
    }
}
