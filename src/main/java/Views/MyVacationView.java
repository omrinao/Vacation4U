package Views;

import Users.User;
import Vacations.Vacation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.effect.Reflection;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class MyVacationView extends ARegisteredView {


    @FXML
    public ListView<String> myList;

    @Override
    public void prepareView(User username, boolean isManager) {

        _loggedUser = username;
        ArrayList<Vacation> vacations = _controller.getMyVacations(username.get_userName());

        if (vacations != null) {
            String id, dest, depart, arrive, quant, price, seller, full, trade;
            if (vacations.isEmpty()){
                myList.getItems().add("You haven't purchased any vacation yet.. Go get em!");
                return;
            }

            for (Vacation v : vacations) {
                id = v._id;
                seller = v._sellingUser;
                dest = v._destination;
                depart = v._departureDate;
                arrive = v._returnDate;
                quant = v._quantity;
                price = v._price;
                trade = v._forTrade;
                full = "Vacation ID: " + id + "\t" + "Seller: " + seller + "\t" + "Destination: " + dest
                        + "    Origin: " + v._origin + "\t" + " Departure Date: " + depart + "\t" +
                        " Arrival Date: " + arrive + "\t" + " Quantity: " + quant + "\t" + " Price: " + price + "\t" + " For Trade: " + trade;
                myList.getItems().add(full);
            }
        }
    }
}
