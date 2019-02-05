package Views;

import Users.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;

public class ReadView extends ARegisteredView {

    @FXML
    public TextField tf_userNameRead;
    public Label lbl_userReadNameErr;
    public Button btn_sendRead;


    /**
     * method to read aa user from DB
     * @param mouseEvent - mouse click on the
     */
    public void send_read (MouseEvent mouseEvent) {
        String username = tf_userNameRead.getText();
        //set error labels to be not visible
        if (username.isEmpty()){
            lbl_userReadNameErr.setVisible(true);
        }
        else{
            lbl_userReadNameErr.setVisible(false);
            ArrayList<String> response = _controller.read_user(username);

            if (response == null){
                popProblem("Username does not exist!");
            }
            else {
                String s = "Username: " + response.get(0) + "\n" +
                        "Birthday: " + response.get(2) + "\n" +
                        "First Name: " + response.get(3) + "\n" +
                        "Last Name: " + response.get(4) + "\n" +
                        "Hometown: " + response.get(5) + "\n";
                popInfo(s);
            }
        }

        mouseEvent.consume();
    }


    @Override
    public void prepareView(User username, boolean isManager) {
        this._loggedUser = username;
        this._manager = isManager;

        if (!isManager){
            tf_userNameRead.setText(username.get_userName());
            //tf_userNameRead.setDisable(true);
        }
    }
}
