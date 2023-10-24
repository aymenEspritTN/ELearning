
import com.deltaVelorum.coursify.DatabaseConnection;
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
import com.deltaVelorum.coursify.courseViewer.gui.CourseViewerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        /*URL url = getClass().getResource("com/deltaVelorum/coursify/chapitre/gui/ChapterEditor.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        ChapterEditorController controller = loader.getController();

        primaryStage.setTitle("Chapitre Editor");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();*/

        URL url = getClass().getResource("com/deltaVelorum/coursify/courseViewer/gui/CourseViewer.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        CourseViewerController controller = loader.getController();
        primaryStage.setTitle("Course Viewer");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

