package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();

        Thread lexo = new Thread(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        controller.zgjedhFile(primaryStage);
                    }
                });
            }
        });

        lexo.setDaemon(true);
        controller.btZgjedhFile.setOnAction(e -> {
            lexo.start();
        });

        primaryStage.setResizable(false);
        primaryStage.setTitle("HEX 2 TEXT");
        primaryStage.setScene(new Scene(root, 340, 200));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
