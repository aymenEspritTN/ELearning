package com.deltaVelorum.coursify.chapitre.gui;

import com.deltaVelorum.coursify.chapitre.entities.Chapitre;
import com.deltaVelorum.coursify.chapitre.entities.ChapitreFile;
import com.deltaVelorum.coursify.chapitre.entities.ChapitreQuizz;
import com.deltaVelorum.coursify.chapitre.services.ChapitreFileService;
import com.deltaVelorum.coursify.chapitre.services.ChapitreQuizzService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileEditorController {
    @FXML
    private Label selectedFileLabel;
    @FXML
    private Label selectedFileSizeLabel;
    private Stage stage;
    private Chapitre chapitre;
    private ChapitreFile file;
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void initialize(Chapitre selectedItem)
    {
        ChapitreFileService.getInstance().createTableIfNotExist();

        if(selectedItem == null)
            System.err.println("[FileEditorController] Chapitre cannot be null!");
        chapitre = selectedItem;
        initFile();
        if(file.getContent() != null)
        {
            selectedFileLabel.setText("Current file: " + file.getName());
            selectedFileSizeLabel.setText(Utils.getStringSizeLengthFile(file.getContent().length));
        }
    }
    private void initFile()
    {
        var all = ChapitreFileService.getInstance().getAll();
        for(ChapitreFile elem: all)
        {
            if(elem.getChapitreId() == chapitre.getId())
            {
                file = elem;
                break;
            }
        }
        if(file == null)
        {
            System.out.println("No quizz found for chapitre: " + chapitre.getName() + ". will add one.");
            file = new ChapitreFile(chapitre.getId());
            ChapitreFileService.getInstance().add(file);
        }
    }
    public void onOpenFileClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Media File");
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            selectedFileLabel.setText("Selected File: " + selectedFile.getName());
            selectedFileSizeLabel.setText(Utils.getStringSizeLengthFile(selectedFile.length()));
            file.setContentFromPath(selectedFile.getAbsolutePath());
        } else {
            selectedFileLabel.setText("No file selected");
            selectedFileSizeLabel.setText("0 kb");
            return;
        }

        ChapitreFileService.getInstance().update(file);
        String msg = "File of chapter: " + chapitre.getName() + " was updated to: " + selectedFile.getName();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}
