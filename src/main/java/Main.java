import com.deltaVelorum.coursify.chapitre.gui.ChapterEditorController;
import com.deltaVelorum.coursify.chapitre.gui.Utils;
import com.deltaVelorum.coursify.courseViewer.gui.CourseViewerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        //System.out.println(Utils.chatGPT("recite one shakespeare poem"));
        //System.out.println(Utils.translate("hello how are you?"));
        //System.out.println(Utils.getTriviaQuestions(5));
        //Utils.readTTS("hello how are u");

        URL url = getClass().getResource("com/deltaVelorum/coursify/chapitre/gui/ChapterEditor.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        ChapterEditorController controller = loader.getController();
        primaryStage.setTitle("Chapitre Editor");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        /*URL url = getClass().getResource("com/deltaVelorum/coursify/courseViewer/gui/CourseViewer.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        CourseViewerController controller = loader.getController();
        primaryStage.setTitle("Course Viewer");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}

