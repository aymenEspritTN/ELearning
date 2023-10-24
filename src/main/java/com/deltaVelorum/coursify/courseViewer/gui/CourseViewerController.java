package com.deltaVelorum.coursify.courseViewer.gui;

import com.deltaVelorum.coursify.chapitre.entities.Chapitre;
import com.deltaVelorum.coursify.chapitre.entities.ChapitreType;
import com.deltaVelorum.coursify.chapitre.services.ChapitreFileService;
import com.deltaVelorum.coursify.chapitre.services.ChapitreService;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CourseViewerController {

    @FXML
    public Label courseTitleLabel;
    @FXML
    public VBox mainContainer;
    @FXML
    public AnchorPane anchorPaneContainer;
    public ScrollPane mainContainerScrollPane;

    public void initialize()
    {
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.prefWidthProperty().bind(mainContainerScrollPane.widthProperty());

        addChapitre(mainContainer, ChapitreService.getInstance().getOne(9));
        addChapitre(mainContainer, ChapitreService.getInstance().getOne(22));
        addChapitre(mainContainer, ChapitreService.getInstance().getOne(24));
        addChapitre(mainContainer, ChapitreService.getInstance().getOne(25));
    }

    /*private static void addCourse(Label courseTitleLabel, VBox mainContainer, Course course)
    {
        courseTitleLabel.setText(course.getTitle());
        Label descLabel = new Label(course.getDescription());
        descLabel.setStyle("-fx-font-size: 16px");
        mainContainer.getChildren().add(descLabel);
    }*/
    private static void addChapitre(VBox mainContainer, Chapitre chapitre)
    {
        Label chapterTitleLabel = new Label("# " + chapitre.getName());
        chapterTitleLabel.setStyle("-fx-font-size: 35px; -fx-font-weight: bold;");
        mainContainer.getChildren().add(chapterTitleLabel);

        Label chapterDescLabel = new Label("Description: " + chapitre.getDescription());
        chapterDescLabel.setStyle("-fx-font-size: 20px; -fx-font-style: italic;");
        mainContainer.getChildren().add(chapterDescLabel);

        if(chapitre.getType() == ChapitreType.Quizz)
        {

        }
        else if(chapitre.getType() == ChapitreType.Text)
        {
            var file = ChapitreFileService.getInstance().getOneByChapitreId(chapitre.getId());
            Label textView = new Label(new String(file.getContent(), StandardCharsets.UTF_8));
            textView.setStyle("-fx-font-size: 16px; -fx-text-alignment: center;");
            mainContainer.getChildren().add(textView);
        }
        else if (chapitre.getType() == ChapitreType.PDF)
        {
            var file = ChapitreFileService.getInstance().getOneByChapitreId(chapitre.getId());
            var pdfFile = file.makeTempFileFromContent("pdf");

            Label openPdfLabel = new Label("Open PDF");
            openPdfLabel.setStyle("-fx-font-size: 30px; -fx-underline: true; -fx-text-fill: #0000FF; -fx-cursor: hand;");
            mainContainer.getChildren().add(openPdfLabel);

            openPdfLabel.setOnMouseClicked(event -> {
                try {
                    Desktop.getDesktop().open(pdfFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }
        else if(chapitre.getType() == ChapitreType.Image)
        {
            var file = ChapitreFileService.getInstance().getOneByChapitreId(chapitre.getId());
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(file.getContent());
            Image image = new Image(byteArrayInputStream);
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(700);
            mainContainer.getChildren().add(imageView);
        }
        else if(chapitre.getType() == ChapitreType.Video)
        {
            var file = ChapitreFileService.getInstance().getOneByChapitreId(chapitre.getId());
            var path = file.makeTempFileFromContent("tmp").toURI().toString();
            System.out.println("Reading video from: " + path);
            Media media = new Media(path);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView mediaView = new MediaView(mediaPlayer);
            mediaView.setPreserveRatio(true);
            mediaView.setFitWidth(700);
            mainContainer.getChildren().add(mediaView);

            mediaView.setOnMouseClicked(e -> {
                if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.play();
                }
            });

            Label helpLabel = new Label("click on the video to play/pause");
            helpLabel.setStyle("-fx-font-size: 16px");
            mainContainer.getChildren().add(helpLabel);
        }
    }
}
