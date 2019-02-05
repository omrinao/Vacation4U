package Views;

import Users.User;
import Vacations.Vacation;
import Vacations.VacationTrade;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class MyVacationsForTradeView extends ARegisteredView {

    public ListView<String> ls_tradeList;
    public Button btn_select;
    public VacationTrade _bidFor;

    @Override
    public void prepareView(User username, boolean isManager) {
        _loggedUser = username;
        _manager = isManager;

        ArrayList<Vacation> vacations = _controller.getMyVacationsForTrade(_loggedUser.get_userName());
        if (vacations != null) {
            String id, dest, depart, arrive, quant, price, seller, full;
            for (Vacation v : vacations) {
                id = v._id;
                seller = v._sellingUser;
                dest = v._destination;
                depart = v._departureDate;
                arrive = v._returnDate;
                quant = v._quantity;
                price = v._price;
                full = "Vacation ID: " + id + "\t" + "Seller: " + seller + "\t" + "Destination: " + dest + "\t" + " Departure Date: " + depart + "\t" +
                        " Arrival Date: " + arrive + "\t" + " Quantity: " + quant + "\t" + " Price: " + price;
                ls_tradeList.getItems().add(full);
            }

            if (ls_tradeList.getItems().isEmpty()){
                ls_tradeList.getItems().add("You don't have any vacations to trade :/");
            }
        }
        else {
            popProblem("Error occurred getting your info!");
        }
    }

    public void selectForTrade(MouseEvent mouseEvent) {
        mouseEvent.consume();

        String entry = ls_tradeList.getSelectionModel().getSelectedItem();
        int start = entry.indexOf(":");
        int end = entry.indexOf("Seller");
        _bidFor.set_vID2(entry.substring(start+2, end).trim());

        _bidFor.set_user2(_loggedUser.get_userName());

        String response = _controller.bidTrade(_bidFor);
        if (response.equals("ok")){
            popInfo("Submitted trade successfully");
        }
        else {
            popProblem(response);
        }

    }



}
