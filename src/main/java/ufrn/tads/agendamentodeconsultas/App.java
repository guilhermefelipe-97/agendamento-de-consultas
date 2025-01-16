package ufrn.tads.agendamentodeconsultas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
    private static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(loadFXML("PrimaryController"), 1280, 768);
        stage.setTitle("Clínica Bem-Estar");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws Exception {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/Fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}

