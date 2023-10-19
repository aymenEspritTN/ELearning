
import com.deltaVelorum.coursify.chapitre.entities.Chapitre;
import com.deltaVelorum.coursify.chapitre.entities.ChapitreType;
import com.deltaVelorum.coursify.chapitre.gui.ChapterEditorController;
/*public class Main {
    public Connection cnx;
    public static void main(String[] args)
    {
        DatabaseConnection.getInstance().getCnx();
        com.deltaVelorum.coursify.chapitre.Test.testGetAll();
    }
}*/
import java.net.URL;

import com.deltaVelorum.coursify.chapitre.gui.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = //new java.net.URL("file:///C:/esprit/projets/ELearning/src/main/java/com/deltaVelorum/coursify/chapitre/gui/ChapterEditor.fxml");
                     getClass().getResource("com/deltaVelorum/coursify/chapitre/gui/ChapterEditor.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        ChapterEditorController controller = loader.getController();

        primaryStage.setTitle("Chapitre Editor");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

