package Views;

import Controllers.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

public abstract class AView {

     protected Controller _controller;
     protected Scene _cameFrom;


     public void set_cameFrom(Scene old){
         _cameFrom = old;
     }

     public void set_controller(Controller c){
        this._controller = c;
    }

    /**
     * method to pop problem easily
     * @param description - description of the problem to show
     */
    protected void popProblem(String description) {
        Alert prob = new Alert(Alert.AlertType.ERROR);

        prob.setContentText(description);
        prob.showAndWait();
    }

    /**
     * method to pop info easily
     * @param data - the data of which to show
     */
    protected void popInfo(String data){

        Alert prob = new Alert(Alert.AlertType.INFORMATION);
        prob.setContentText(data);
        prob.showAndWait();
    }

}
