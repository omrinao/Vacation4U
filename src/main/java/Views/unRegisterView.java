package Views;

import Controllers.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class unRegisterView extends AView {

    @FXML
    public Button btn_exit;
    public Button btn_login;


    /**
     * method to exit the program
     * @param mouseEvent - mouse click on exit event
     */
    public void exit(MouseEvent mouseEvent) {
        Stage stage = (Stage) btn_exit.getScene().getWindow();
        mouseEvent.consume();
        stage.close();
    }


    /**
     * method to move to login zone
     * @param mouseEvent - mouse click on system enter
     */
    public void login(MouseEvent mouseEvent) {
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent root = loader.load(getClass().getResourceAsStream("/fxmls/loginXML.fxml"));
            AView loginView = loader.getController();
            loginView.set_controller(_controller);
            loginView.set_cameFrom(Main.pStage.getScene());
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("/ViewStyle.css").toExternalForm());
            Main.pStage.setScene(scene);
            Main.pStage.show();

            mouseEvent.consume();

        } catch (IOException e) {
            System.out.println("Something bad happened while trying to move to login screen :(");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void setVacationsUnRegistered(MouseEvent mouseEvent){
        try {
            Stage pStage = new Stage();
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxmls/tableXML.fxml"));
            pStage.setTitle("EveryVacation4U");
            Parent root = fxml.load();

            VacationsTableView v = fxml.getController();
            v.set_controller(_controller);
            v.prepareView(null, false);
            Scene scene = new Scene(root, 700, 300);
            scene.getStylesheets().add(getClass().getResource("/ViewStyle.css").toExternalForm());
            pStage.setScene(scene);
            pStage.initModality(Modality.APPLICATION_MODAL);
            pStage.showAndWait();
            pStage.setResizable(false);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
