package Views;

import Users.User;
import Vacations.Vacation;
import Vacations.VacationTrade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;


public class VacationsTableView extends ARegisteredView{

    @FXML
    public ListView<String> sellList = new ListView<>();


    @Override
    public void prepareView(User username, boolean isManager) {
        ArrayList <Vacation> vacations = _controller.getAllVacations(null);
        if (username != null)
            vacations = _controller.getAllVacations(username.get_userName());

        if (vacations != null) {
            _loggedUser = username;
            String id, dest, depart, arrive, quant, price, seller, trade, origin, full;
            for (Vacation v : vacations) {
                    id = v._id;
                    seller = v._sellingUser;
                    dest = v._destination;
                    depart = v._departureDate;
                    arrive = v._returnDate;
                    quant = v._quantity;
                    price = v._price;
                    trade = v._forTrade;
                    origin = v._origin;
                    full = "Vacation ID: " + id + "\t" + "Seller: " + seller + "\t" + "Destination: " + dest
                            + "\tOrigin: " + origin + "\t" + " Departure Date: " + depart + "\t" +
                            " Arrival Date: " + arrive + "\t" + " Quantity: " + quant + "\t"
                            + " Price: " + price + "\t" + " For Trade: " + trade;
                sellList.getItems().add(full);
            }
        }

    }

    public void setSelectVacation (MouseEvent event){
        event.consume();
        if (_loggedUser != null) {
            String entry = sellList.getSelectionModel().getSelectedItem();
            if (entry != null) {
                int start = entry.indexOf(':');
                int end = entry.indexOf("Seller");
                String[] selectedVacationDetails = new String[4];
                selectedVacationDetails[0] = entry.substring(start + 2, end - 1);//id
                entry = entry.substring(end);
                start = entry.indexOf(':');
                end = entry.indexOf("Destination");
                selectedVacationDetails[1] = entry.substring(start + 2, end - 1);//seller
                entry = entry.substring(end);
                start = entry.indexOf("Price");
                end = entry.indexOf("For");
                selectedVacationDetails[2] = entry.substring(start + 7, end).trim();//price
                selectedVacationDetails[3] = _loggedUser.get_userName(); //logged user

                if (selectedVacationDetails[1].equals(_loggedUser.get_userName())) {
                    popProblem("Can't bid on your own vacation!! :P");
                    return;
                }

                String response = _controller.bidVacation(selectedVacationDetails[1], selectedVacationDetails[3], selectedVacationDetails[0], selectedVacationDetails[2]);
                if (response.contains("Bid success"))
                    popInfo(response);
                else if (response.contains("error"))

                    popProblem(response);

            } else
                popProblem("No selection was made");
        }
        else
            popProblem("Login to purchase!");

    }

    public void setSelectTradeVacation (MouseEvent event){
        event.consume();
        if (_loggedUser != null) {
            String entry = sellList.getSelectionModel().getSelectedItem();
            if (entry != null) {
                VacationTrade vTrade = new VacationTrade();
                int start = entry.indexOf(':');
                int end = entry.indexOf("Seller");
                String[] selectedVacationDetails = new String[5];

                /* vTrade needs */
                vTrade.set_vID1(entry.substring(start + 2, end - 1));//id
                entry = entry.substring(end);
                start = entry.indexOf(':');
                end = entry.indexOf("Destination");
                vTrade.set_user1(entry.substring(start + 2, end - 1));//seller
                /* end of vTrade needs */

                entry = entry.substring(end);
                start = entry.indexOf("Price");
                end = entry.length();
                selectedVacationDetails[2] = entry.substring(start + 7, end);//price
                selectedVacationDetails[3] = _loggedUser.get_userName(); //logged user
                start = entry.indexOf("For Trade");
                end = entry.length();
                selectedVacationDetails[4] = entry.substring(start + 11, end);

                if (selectedVacationDetails[4].equals("false")) {
                    popProblem("Vacation is not available for trade!");
                    return;
                }

                if (vTrade.get_user1().equals(_loggedUser.get_userName())) {
                    popProblem("Can't offer trade on your own vacation!");
                    return;
                }


                try {
                    Stage myVacationsStage = new Stage();
                    FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxmls/MyVacationsForTradeXML.fxml"));
                    myVacationsStage.setTitle("Choose your vacation");
                    Parent root = fxml.load();
                    Scene scene = new Scene(root, 600, 300);
                    scene.getStylesheets().add(getClass().getResource("/ViewStyle.css").toExternalForm());

                    MyVacationsForTradeView control = fxml.getController();
                    control._controller = _controller;
                    control._cameFrom = _cameFrom;
                    control.prepareView(_loggedUser, _manager);
                    control._bidFor = vTrade;
                    
                    myVacationsStage.setScene(scene);
                    myVacationsStage.show();
                }
                catch (Exception e){}
            } else
                popProblem("No selection was made");
        }
        else
            popProblem("Login to purchase!");

    }


}
