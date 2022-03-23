package anagrammi;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Joonas
 * @version 4.7.2020
 *
 */
public class AnagrammiFxMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("AnagrammiFxGUIView.fxml"));
            final Pane root = ldr.load();
            //final AnagrammiFxGUIController anagrammifxCtrl = (AnagrammiFxGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("anagrammifx.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("AnagrammiFx");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei k�yt�ss�
     */
    public static void main(String[] args) {
        launch(args);
    }
}