package Views;

import Controllers.Main;
import Views.AView;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.util.ArrayList;

public class CreateView extends AView {

    @FXML
    public TextField tf_userName;
    public PasswordField pf_password;
    public DatePicker dp_date;
    public TextField tf_firstName;
    public TextField tf_lastName;
    public TextField tf_hometown;

    public Label lbl_createUser;
    public Label lbl_userNameErr;
    public Label lbl_passwordErr;
    public Label lbl_dateErr;
    public Label lbl_firstNameErr;
    public Label lbl_lastNameErr;
    public Label lbl_hometownErr;

    public Button btn_send;
    public Button btn_back;


    /**
     * method to create a new user
     * @param mouseEvent - mouse click event on 'create'
     */
    public void send_create(MouseEvent mouseEvent) {
        ArrayList<String> toSend = new ArrayList<String>();
        boolean allChecked = true;
        //temp string fields to get the text from the text field, will be checked before inserting to array list
        String username = tf_userName.getText(),
                password = pf_password.getText(),
                firstname = tf_firstName.getText(),
                lastname = tf_lastName.getText(),
                hometown = tf_hometown.getText();

        //set error labels to be not visible
        lbl_userNameErr.setVisible(false);
        lbl_firstNameErr.setVisible(false);
        lbl_lastNameErr.setVisible(false);
        lbl_passwordErr.setVisible(false);
        lbl_hometownErr.setVisible(false);
        lbl_dateErr.setVisible(false);


        //username check
        if (username.length() == 0){
            lbl_userNameErr.setVisible(true);
            allChecked = false;
        }

        //password check
        if (password.length() < 8){
            lbl_passwordErr.setVisible(true);
            allChecked = false;
        }

        //first name check
        if (firstname.length() < 2){
            lbl_firstNameErr.setVisible(true);
            allChecked = false;
        }

        //last name check
        if (lastname.length() < 2){
            lbl_lastNameErr.setVisible(true);
            allChecked = false;
        }

        //date check
        if (!isBiggerThen18(dp_date)){
            lbl_dateErr.setVisible(true);
            allChecked = false;
        }

        //hometown check
        if (hometown.length() < 2){
            lbl_hometownErr.setVisible(true);
            allChecked = false;
        }

        if (allChecked){
            toSend.add(username);
            toSend.add(password);
            toSend.add(dp_date.getValue().toString());
            toSend.add(firstname);
            toSend.add(lastname);
            toSend.add(hometown);
            String response = _controller.create_user(toSend);

            if (response.contains("fail"))
                popProblem(response);
            else
                popInfo(response);

        }


        mouseEvent.consume();

    }

    /**
     * this method checks if a user age is above 18
     * @param age - datepicker object
     * @return true if date is larger then 18, false otherwise
     */
    private boolean isBiggerThen18 (DatePicker age) {
        LocalDate Date = age.getValue();
        LocalDate today = LocalDate.now();
        if (Date == null || Date.getYear() + 18 > today.getYear())
            return false;
        else if (Date.getYear() + 18 == today.getYear()) {
            if (Date.getMonthValue() > today.getMonthValue())
                return false;
            else if (Date.getMonthValue() == today.getMonthValue()) {
                return Date.getDayOfMonth() <= today.getDayOfMonth();
            }
        }

        return true;
    }

    public void goBack(MouseEvent mouseEvent) {
        Main.pStage.setScene(_cameFrom);
        Main.pStage.show();

        mouseEvent.consume();
    }
}
