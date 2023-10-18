package com.deltaVelorum.coursify.chapitre.gui;

import com.deltaVelorum.coursify.chapitre.entities.Chapitre;
import com.deltaVelorum.coursify.chapitre.entities.ChapitreType;
import com.deltaVelorum.coursify.chapitre.services.ChapitreService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.util.ArrayList;
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
    @FXML
    public void onSaveButtonClick(ActionEvent actionEvent)
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
