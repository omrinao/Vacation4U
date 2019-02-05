package Controllers;

import Controllers.Controller;
import Models.Model;
import Views.AView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage pStage;


    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        pStage = primaryStage;

        // receiving fxml loader and setting primary node
        FXMLLoader loader = new FXMLLoader();
        String fxmlFile = "/fxmls/UnRegistered.fxml";
        //Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));


        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxmls/Unregistered.fxml"));
        primaryStage.setTitle("Vacation4U");

        Canvas canvas = new Canvas(700, 500);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        String ams = getClass().getResource("/Pics/ams.jpg").toString();
        Image amsIM = new Image(ams);
        gc.drawImage(amsIM, 60, 80, 250, 180);

        String london = getClass().getResource("/Pics/london.jpg").toString();
        Image londonIM = new Image(london);
        gc.drawImage(londonIM,410,80,250, 180);

        String paris = getClass().getResource("/Pics/paris.jpg").toString();
        Image parisIM = new Image(paris);
        gc.drawImage(parisIM,410,335,250, 180);

        String rome = getClass().getResource("/Pics/rome.jpg").toString();
        Image romeIM = new Image(rome);
        gc.drawImage(romeIM,60,335,250, 180);
        Pane root = new Pane();
        root = fxml.load();
        root.getChildren().add(canvas);


        // setting up
        Model model = new Model();
        Controller controller = new Controller();
        AView view = fxml.getController();
        controller.set_model(model);
        view.set_controller(controller);

        // displaying the first presentation
        primaryStage.setTitle("EveryVacation4U");
        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(getClass().getResource("/ViewStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);

        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
