package com.deltaVelorum.coursify.chapitre.gui;

import com.deltaVelorum.coursify.chapitre.entities.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.text.DecimalFormat;

public class Utils {
    public static String getStringSizeLengthFile(long size) {

        DecimalFormat df = new DecimalFormat("0.00");

        float sizeKb = 1024.0f;
        float sizeMb = sizeKb * sizeKb;
        float sizeGb = sizeMb * sizeKb;
        float sizeTerra = sizeGb * sizeKb;


        if(size < sizeMb)
            return df.format(size / sizeKb)+ " Kb";
        else if(size < sizeGb)
            return df.format(size / sizeMb) + " Mb";
        else if(size < sizeTerra)
            return df.format(size / sizeGb) + " Gb";

        return "";
    }
    public static void quitCurrentWindow(ActionEvent actionEvent) {
        // Platform.exit();
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public static void setupQuestionsList(ListView<String> list, ChapitreQuizz quizz)
    {
        list.setEditable(true);
        list.setCellFactory(param -> {
            TextFieldListCell<String> cell = new TextFieldListCell<>();
            cell.setConverter(new StringConverter<String>() {
                @Override
                public String toString(String object) {
                    return object;
                }

                @Override
                public String fromString(String string) {
                    return string;
                }
            });
            cell.itemProperty().addListener((obs, oldItem, newItem) -> {
                if (newItem != null) {
                    TextField textField = (TextField) cell.getGraphic();
                    if (textField != null) {
                        // Add focus listener to the TextField inside the cell
                        textField.focusedProperty().addListener((focusObs, wasFocused, isNowFocused) -> {
                            if (!isNowFocused) {
                                int index = cell.getIndex();
                                String newText = textField.getText();
                                if (index >= 0 && index < quizz.getQuestions().size()) {
                                    quizz.getQuestions().get(index).text = newText;
                                }
                            }
                        });
                    }
                }
            });

            return cell;
        });
    }
    public static void resetAndFillQuestionsList(ListView<String> list, ChapitreQuizz quizz)
    {
        list.getItems().clear();
        for (ChapitreQuizzQuestion question : quizz.getQuestions()) {
            list.getItems().add(question.getText());
        }
    }

    public static void resetAndFillAnswersList(TableView<ChapitreQuizzAnswer> answersList,
                                               ChapitreQuizz quizz, int selectedQuestion) {
        answersList.getItems().clear();
        for (ChapitreQuizzAnswer answer : quizz.getQuestions().get(selectedQuestion).answers) {
            answersList.getItems().add(answer);
        }
    }

    public static void removeSelectedQuestion(ListView<String> list)
    {
        SelectionModel<String> selectionModel = list.getSelectionModel();
        String selectedItem = selectionModel.getSelectedItem();
        if (selectedItem != null) {
            //System.out.println("Selected item: " + selectedItem.getName());
            list.getItems().remove(selectedItem);
        }
    }

    public static boolean assertAnyQuestionSelected(ListView<String> list) {

        SelectionModel<String> selectionModel = list.getSelectionModel();
        String selectedItem = selectionModel.getSelectedItem();
        if (selectedItem != null) {
            return true;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR, "No question selected!", ButtonType.OK);
        alert.showAndWait();
        return false;
    }

    public static void setupQuizzEditorAnswersTreeView(TableView<ChapitreQuizzAnswer> table)
    {
        TableColumn<ChapitreQuizzAnswer, String> answerTextCol = new TableColumn<>("Answer");
        answerTextCol.setMinWidth(150);
        answerTextCol.setCellValueFactory(new PropertyValueFactory<>("text"));
        answerTextCol.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<ChapitreQuizzAnswer, Boolean> isCorrectCol = new TableColumn<>("Correct?");
        isCorrectCol.setMinWidth(100);
        // option 1: use "isCorrect() and getIsCorrect() as getter/setter"
        //isCorrectCol.setCellValueFactory(new PropertyValueFactory<>("isCorrect"));
        // option 2: custom jfx value loader
        isCorrectCol.setCellValueFactory(cellData -> {
            ChapitreQuizzAnswer answer = cellData.getValue();
            BooleanProperty property = new SimpleBooleanProperty(answer.getIsCorrect());
            property.addListener((observable, oldValue, newValue) -> {
                answer.setIsCorrect(newValue);
                System.out.println("Answer was modified!");
            });
            return property;
        });
        isCorrectCol.setCellFactory(col -> {
            CheckBoxTableCell<ChapitreQuizzAnswer, Boolean> checkBoxCell = new CheckBoxTableCell<>();
            checkBoxCell.setAlignment(Pos.CENTER);
            return checkBoxCell;
        });

        // Updating the data (rather than the UI)
        answerTextCol.setOnEditCommit(event -> {
            ChapitreQuizzAnswer answer = event.getRowValue();
            answer.setText(event.getNewValue());
            System.out.println("Answer was modified!");
        });
        isCorrectCol.setOnEditCommit(event -> {
            ChapitreQuizzAnswer answer = event.getRowValue();
            answer.setIsCorrect(event.getNewValue());
            System.out.println("Answer was modified!");
        });

        table.getColumns().addAll(answerTextCol, isCorrectCol);
        table.setEditable(true);
    }

    public static void addAnswersToTreeView(TableView<ChapitreQuizzAnswer> table, ChapitreQuizzAnswer c)
    {
        var data = table.getItems();
        data.add(c);
    }
    public static void addAnswersToTreeView(TableView<ChapitreQuizzAnswer> table, ObservableList<ChapitreQuizzAnswer> data)
    {
        table.setItems(data);
    }

    public static void removeSelectedAnswer(TableView<ChapitreQuizzAnswer> table)
    {
        SelectionModel<ChapitreQuizzAnswer> selectionModel = table.getSelectionModel();
        ChapitreQuizzAnswer selectedItem = selectionModel.getSelectedItem();
        if (selectedItem != null) {
            //System.out.println("Selected item: " + selectedItem.getName());
            table.getItems().remove(selectedItem);
        }
    }

    public static void setupChaptersTable(TableView<Chapitre> table)
    {
        TableColumn<Chapitre, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(150);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<Chapitre, ChapitreType> typeCol = new TableColumn<>("Type");
        typeCol.setMinWidth(50);
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeCol.setCellFactory(ComboBoxTableCell.forTableColumn(ChapitreType.values()));

        TableColumn<Chapitre, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setMinWidth(250);
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());

        nameCol.setOnEditCommit(event -> {
            Chapitre chapitre = event.getRowValue();
            chapitre.setName(event.getNewValue());
        });
        typeCol.setOnEditCommit(event -> {
            Chapitre chapitre = event.getRowValue();
            chapitre.setType(event.getNewValue());
        });
        descriptionCol.setOnEditCommit(event -> {
            Chapitre chapitre = event.getRowValue();
            chapitre.setDescription(event.getNewValue());
        });


        table.getColumns().addAll(nameCol, typeCol, descriptionCol);
        table.setEditable(true);
    }
    public static void addChaptersToChaptersTable(TableView<Chapitre> table, Chapitre c)
    {
        var data = table.getItems();
        data.add(c);
    }
    public static void addChaptersToChaptersTable(TableView<Chapitre> table, ObservableList<Chapitre> data)
    {
        table.setItems(data); // overrides existing items.
    }

    public static void removeSelectedChapter(TableView<Chapitre> table)
    {
        SelectionModel<Chapitre> selectionModel = table.getSelectionModel();
        Chapitre selectedItem = selectionModel.getSelectedItem();
        if (selectedItem != null) {
            //System.out.println("Selected item: " + selectedItem.getName());
            table.getItems().remove(selectedItem);
        }
    }
    public static void editSelectedChapter(TableView<Chapitre> table)
    {
        SelectionModel<Chapitre> selectionModel = table.getSelectionModel();
        Chapitre selectedItem = selectionModel.getSelectedItem();
        if (selectedItem != null) {
            //System.out.println("Selected item: " + selectedItem.getName());
            var type = selectedItem.getType();
            if(type == ChapitreType.Quizz)
            {
                try {
                    FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("com/deltaVelorum/coursify/chapitre/gui/QuizzEditor.fxml"));
                    Parent root = loader.load();
                    QuizzEditorController quizzEditorController = loader.getController();
                    quizzEditorController.initialize(selectedItem);

                    Stage quizzEditorStage = new Stage();
                    quizzEditorStage.initModality(Modality.APPLICATION_MODAL);
                    quizzEditorStage.setTitle("Quizz Editor");
                    quizzEditorStage.setScene(new Scene(root));
                    quizzEditorStage.showAndWait();

                } catch (Exception e) {
                    e.printStackTrace(); // Handle the exception appropriately
                }
            }
            else
            {
                try {
                    FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource("com/deltaVelorum/coursify/chapitre/gui/FileEditor.fxml"));
                    Parent root = loader.load();
                    FileEditorController controller = loader.getController();
                    controller.initialize(selectedItem);

                    Stage fileEditorStage = new Stage();
                    controller.setStage(fileEditorStage);
                    fileEditorStage.initModality(Modality.APPLICATION_MODAL);
                    fileEditorStage.setTitle("File Editor");
                    fileEditorStage.setScene(new Scene(root));
                    fileEditorStage.showAndWait();

                } catch (Exception e) {
                    e.printStackTrace(); // Handle the exception appropriately
                }

            }
        }
    }

}
