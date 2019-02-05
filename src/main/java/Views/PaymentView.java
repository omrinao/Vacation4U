package Views;

import Users.User;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PaymentView extends ARegisteredView {
    public CheckBox cb_visa;
    public CheckBox cb_paypal;
    public TextField tf_cardNum;
    public TextField tf_cardDate;
    public TextField tf_cardCVV;
    public TextField tf_username;
    public PasswordField pf_password;
    public Button btn_visa;
    public Button btn_paypal;
    public ChoiceBox<String> cb_tash;

    String _price;
    String _id;
    String _seller;

    @Override
    public void prepareView(User username, boolean isManager) {
        cb_tash.getItems().add("1");
        cb_tash.getItems().add("2");
        cb_tash.getItems().add("3");
    }

    public void setVisa (MouseEvent mouseEvent){
        mouseEvent.consume();
        if (cb_visa.isSelected()) {
            tf_cardNum.setDisable(false);
            tf_cardDate.setDisable(false);
            tf_cardCVV.setDisable(false);
            cb_tash.setDisable(false);
            btn_visa.setDisable(false);
            tf_username.setDisable(true);
            pf_password.setDisable(true);
            btn_paypal.setDisable(true);
            cb_paypal.setSelected(false);
        }
        else{
            tf_cardNum.setDisable(true);
            tf_cardDate.setDisable(true);
            tf_cardCVV.setDisable(true);
            cb_tash.setDisable(true);
            btn_visa.setDisable(true);
            tf_username.setDisable(true);
            pf_password.setDisable(true);
            btn_paypal.setDisable(true);
            cb_paypal.setSelected(false);
        }
    }

    public void setPaypal (MouseEvent mouseEvent){
        mouseEvent.consume();
        if (cb_paypal.isSelected()) {
            tf_cardNum.setDisable(true);
            tf_cardDate.setDisable(true);
            tf_cardCVV.setDisable(true);
            cb_tash.setDisable(true);
            btn_visa.setDisable(true);
            tf_username.setDisable(false);
            pf_password.setDisable(false);
            btn_paypal.setDisable(false);
            cb_visa.setSelected(false);
        }
        else{
            tf_cardNum.setDisable(true);
            tf_cardDate.setDisable(true);
            tf_cardCVV.setDisable(true);
            cb_tash.setDisable(true);
            btn_visa.setDisable(true);
            tf_username.setDisable(true);
            pf_password.setDisable(true);
            btn_paypal.setDisable(true);
            cb_paypal.setSelected(false);
        }
    }

    public void payVisa (MouseEvent mouseEvent){
        if (tf_cardNum.getText().isEmpty() || tf_cardDate.getText().isEmpty() || tf_cardCVV.getText().isEmpty() || cb_tash.getSelectionModel().getSelectedItem().equals(""))
            popProblem("Please fill all the details!");
        else if (!isNumber(tf_cardNum.getText()) || !isNumber(tf_cardDate.getText()) || !isNumber(tf_cardCVV.getText()))
            popProblem("Please fill valid details!");
        else {
            int PAYMENT_NUMBER = 6; /* to change */
            String response = _controller.payForVacation(_id, _loggedUser.get_userName(), _seller, _price, "Visa", Integer.valueOf(cb_tash.getSelectionModel().getSelectedItem().toString()));
            if (response.contains("vacation")) {
                popInfo(response);
                Stage parent = (Stage) tf_cardCVV.getScene().getWindow();
                parent.close();
            }
            else
                popProblem(response);
        }

    }

    public void payPaypal (MouseEvent mouseEvent){
        if (tf_username.getText().isEmpty() || pf_password.getText().isEmpty())
            popProblem("Please fill all the details!");
        else {
            String response = _controller.payForVacation(_id, _loggedUser.get_userName(), _seller, _price, "Paypal", 1);
            if (response.contains("vacation")) {
                popInfo(response);
                Stage parent = (Stage) tf_cardCVV.getScene().getWindow();
                parent.close();
            }
            else
                popProblem(response);
        }
    }

    private boolean isNumber(String str){
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
