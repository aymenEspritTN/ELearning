package com.deltaVelorum.coursify.chapitre.gui;

import com.deltaVelorum.coursify.chapitre.entities.Chapitre;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;

public class AutoCompletionTextFieldCellFactory extends TableCell<Chapitre, String> {
    private final AutoCompletionTextField textField;

    public AutoCompletionTextField getTextField()
    {
        return textField;
    }

    public AutoCompletionTextFieldCellFactory() {
        // Initialize your AutoCompletionTextField
        textField = new AutoCompletionTextField();
        setGraphic(textField);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        // Commit cell value on focus out (optional)
        textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) {
                commitEdit(textField.getText());
            }else
            {
                startEdit();
            }
        });
        // Commit cell value on Enter key press
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                commitEdit(textField.getText());
            } else if (event.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(null);
            textField.setText(item);
            setGraphic(textField);
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        textField.setText(getItem());
        setText(null);
        setGraphic(textField);
        textField.requestFocus();
        textField.selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getItem());
        setGraphic(null);
    }

}

