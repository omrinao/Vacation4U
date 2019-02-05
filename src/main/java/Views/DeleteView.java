package Views;

import Controllers.Main;
import Users.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class DeleteView extends ARegisteredView {

    @FXML
    public Label lbl_userName;
    public Label lbl_userNameDeleteErr;
    public TextField tf_userNameDelete;
    public Button btn_delete;


    /**
     * method to send a delete of a user
     * @param mouseEvent - mouse click on 'delete'
     */
    public void send_delete(MouseEvent mouseEvent) {
        String username = tf_userNameDelete.getText();
        mouseEvent.consume();

        //set error labels to be not visible
        if (username.isEmpty()){
            lbl_userNameDeleteErr.setVisible(true);
        }
        else {
            lbl_userNameDeleteErr.setVisible(false);

            String response = _controller.delete_user(username);
            if (response.equals("Delete success"))
                popInfo(response);
            else
                popProblem(response);
        }

        if (!_manager){
            popInfo("You are now logged out of the service");
            Main.pStage.setScene(_cameFrom);
            Main.pStage.show();
        }

    }

    @Override
    public void prepareView(User username, boolean isManager) {
        this._loggedUser = username;
        this._manager = isManager;

        if (!isManager){
            tf_userNameDelete.setText(username.get_userName());
            tf_userNameDelete.setDisable(true);
        }
    }
}
