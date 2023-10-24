package com.deltaVelorum.coursify.chapitre.gui;

import com.deltaVelorum.coursify.chapitre.entities.Chapitre;
import com.deltaVelorum.coursify.chapitre.services.ChapitreService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;

import java.util.List;

public class ChapterEditorController {
    @FXML
    public TableView<Chapitre> chaptersView;
    private List<Chapitre> originalChapitres;
    public void initialize()
    {
        ChapitreService.getInstance().createTableIfNotExist();
        Utils.setupChaptersTable(chaptersView);
        // for testing, we load all chapters. but IRL need to load chapters only related to a course x.
        originalChapitres = ChapitreService.getInstance().getAll();
        chaptersView.getItems().addAll(ChapitreService.getInstance().getAll());
    }
    static class Course{ List<Integer> chapitresIds;}
    /*public void initialize(Course course)
    {
        ChapitreService.getInstance().createTableIfNotExist();
        Utils.setupChaptersTable(chaptersView);
        originalChapitres = new ArrayList<>();
        for(int chapitreId : course.chapitresIds)
        {
            originalChapitres.add(ChapitreService.getInstance().getOne(chapitreId))
        }
        chaptersView.getItems().addAll(originalChapitres);
    }*/
    @FXML
    public void onSaveButtonClick(ActionEvent actionEvent)
    {
        updateChapitresInDB();

        ButtonType sendButtonType = new ButtonType("Send Email");
        ButtonType cancelButtonType = new ButtonType("Cancel");
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION,
                "Do you want to send an email?", sendButtonType, cancelButtonType);
        confirmationDialog.setTitle("Email Confirmation");
        confirmationDialog.setHeaderText(null);
        confirmationDialog.showAndWait().ifPresent(response -> {
            if (response == sendButtonType)
            {
                String emailTitle = "Your Coursify course was changed successfully!";
                String emailContent = "Make sure to look at the new reviews for further adjustements!";
                Utils.sendMail("aymen.ayoo@gmail.com", emailTitle, emailContent);
            }
        });
    }

    void updateChapitresInDB()
    {
        for(Chapitre c: originalChapitres)
        {
            boolean wasDeleted = true;
            for(Chapitre newC: chaptersView.getItems())
            {
                if(c.getId() == newC.getId())
                {
                    wasDeleted = false;
                    break;
                }
            }
            if(wasDeleted)
            {
                ChapitreService.getInstance().fullDelete(c);
            }
        }

        for(Chapitre chapitre : chaptersView.getItems())
        {
            boolean contains = false;
            for(Chapitre c: originalChapitres)
            {
                if(c.getId() == chapitre.getId())
                {
                    contains = true;
                    break;
                }
            }
            // if existing, update it
            if(contains)
            {
                if(!Utils.AssertNotNullOrALERT(chapitre.getName()))
                    return;
            }
            if(contains)
            {
                ChapitreService.getInstance().update(chapitre);
            }
            else // otherwise add it to DB
            {
                ChapitreService.getInstance().add(chapitre);
            }
        }

        originalChapitres = ChapitreService.getInstance().getAll();
    }


    @FXML
    public void onAddButtonClick(ActionEvent actionEvent) {
        Chapitre c = new Chapitre();
        Utils.addChaptersToChaptersTable(chaptersView, c);
        // need to uupdate DB, otherwise when edit is clicked it will show error of foreign key not found
        updateChapitresInDB();
    }
    @FXML
    public void onRemoveButtonClick(ActionEvent actionEvent) {
        Utils.removeSelectedChapter(chaptersView);
    }
    @FXML
    public void onQuitButtonClick(ActionEvent actionEvent) {
        Utils.quitCurrentWindow(actionEvent);
    }
    @FXML
    public void onEditButtonClick(ActionEvent actionEvent) {
        Utils.editSelectedChapter(chaptersView);
    }
}
