package Views;

import Controllers.Main;
import Users.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class RegisteredView extends ARegisteredView{

    @FXML
    public Button btn_exit;
    public Button btn_read;
    public Button btn_update;
    public Button btn_delete;
    public BorderPane lyt_mainPane;
    public Button btn_publishVacation;
    public Button btn_mailBox;
    public Button btn_search;
    public Label lbl_welcome;
    public Button btn_purchasedVacations;


    /**
     * method to set the read interface
     * @param mouseEvent - click event on the read button
     */
    public void setRead(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader();
        try {
            lyt_mainPane.setCenter(loader.load(getClass().getResourceAsStream("/fxmls/readXML.fxml")));

            ARegisteredView v = loader.getController();
            v.set_controller(_controller);
            v.prepareView(_loggedUser, _manager);

        } catch (IOException e) {
            popProblem("Error while trying to load read interface\n" + e.getMessage());
        }
        mouseEvent.consume();
    }


    /**
     * method to set the update interface
     * @param mouseEvent - click event of the update button
     */
    public void setUpdate(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader();
        try {
            lyt_mainPane.setCenter(loader.load(getClass().getResourceAsStream("/fxmls/updateXML.fxml")));

            ARegisteredView v = loader.getController();
            v.set_controller(_controller);
            v.prepareView(_loggedUser, _manager);

        } catch (IOException e) {
            popProblem("Error while trying to load update interface\n" + e.getMessage());
        }
        mouseEvent.consume();
    }


    /**
     * method to set the delete interface
     * @param mouseEvent - click even on the delete button
     */
    public void setDelete(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader();
        try {
            lyt_mainPane.setCenter(loader.load(getClass().getResourceAsStream("/fxmls/deleteXML.fxml")));

            ARegisteredView v = loader.getController();
            v.set_controller(_controller);
            v.set_cameFrom(_cameFrom);
            v.prepareView(_loggedUser, _manager);

        } catch (IOException e) {
            popProblem("Error while trying to load update interface\n" + e.getMessage());
        }
        mouseEvent.consume();
    }


    /**
     * method to to exit the program
     * @param mouseEvent - even of close
     */
    public void logOut(MouseEvent mouseEvent) {
        mouseEvent.consume();
        Main.pStage.setScene(_cameFrom);
        Main.pStage.show();
    }

    @Override
    public void prepareView(User username, boolean isManager) {
        this._loggedUser = username;
        lbl_welcome = new Label("Welcome back " + _loggedUser + "!");
        lbl_welcome.setLayoutX(500);
        lbl_welcome.setLayoutY(300);
        this._manager = isManager;

    }


    public void setPublish(MouseEvent mouseEvent) {
        mouseEvent.consume();
        FXMLLoader loader = new FXMLLoader();
        try {
            lyt_mainPane.setCenter(loader.load(getClass().getResourceAsStream("/fxmls/publishXML.fxml")));

            ARegisteredView v = loader.getController();
            v.set_controller(_controller);
            v.set_cameFrom(_cameFrom);
            v.prepareView(_loggedUser, _manager);

        } catch (IOException e) {
            popProblem("Error while trying to load update interface\n" + e.getMessage());
        }
    }

    /**
     * this method will present the view for searching vacations
     * @param mouseEvent
     */
    public void setSearch(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader();
        try {
            lyt_mainPane.setCenter(loader.load(getClass().getResourceAsStream("/fxmls/tableXML.fxml")));

            ARegisteredView v = loader.getController();
            v.set_controller(_controller);
            v.set_cameFrom(_cameFrom);
            v.prepareView(_loggedUser, _manager);

        } catch (IOException e) {
            popProblem("Error while trying to load search interface\n" + e.getMessage());
        }
        mouseEvent.consume();
    }

    /**
     * this method will present the mailbox of the user
     * @param mouseEvent
     */
    public void setMail(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader();
        try {
            lyt_mainPane.setCenter(loader.load(getClass().getResourceAsStream("/fxmls/mailXML.fxml")));

            ARegisteredView v = loader.getController();
            v.set_controller(_controller);
            v.set_cameFrom(_cameFrom);
            v.prepareView(_loggedUser, _manager);

        } catch (IOException e) {
            popProblem("Error while trying to load mail interface\n" + e.getMessage());
        }
        mouseEvent.consume();
    }

    public void setMyVacations(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader();
        try {
            lyt_mainPane.setCenter(loader.load(getClass().getResourceAsStream("/fxmls/MyVacations.fxml")));

            ARegisteredView v = loader.getController();
            v.set_controller(_controller);
            v.set_cameFrom(_cameFrom);
            v.prepareView(_loggedUser, _manager);

        } catch (IOException e) {
            popProblem("Error while trying to load search interface\n" + e.getMessage());
        }
        mouseEvent.consume();
    }
}
