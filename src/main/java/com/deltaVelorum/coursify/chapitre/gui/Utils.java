package com.deltaVelorum.coursify.chapitre.gui;

import com.deltaVelorum.coursify.chapitre.entities.*;
import com.mysql.cj.util.StringUtils;
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
import org.json.JSONArray;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

    public static List<String> getGoogleAutocompleteSuggestions(String query) throws IOException {
        String apiUrl = "http://suggestqueries.google.com/complete/search?client=firefox&q=" + query.replace(" ", "%20");
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        List<String> suggestions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Parse the JSON response
            JSONArray jsonArray = new JSONArray(response.toString());
            JSONArray suggestionsArray = jsonArray.getJSONArray(1);
            for (int i = 0; i < suggestionsArray.length(); i++) {
                String suggestion = suggestionsArray.getString(i);
                suggestions.add(suggestion);
            }

        } finally {
            connection.disconnect();
        }

        return suggestions;
    }

    public static void sendMail(String targetUserMail, String title, String content)
    {
        // https://stackoverflow.com/a/45212730
        // https://stackoverflow.com/a/45212730
        // https://stackoverflow.com/a/45212730
        // https://stackoverflow.com/a/45212730
        // https://stackoverflow.com/a/45212730
        // https://stackoverflow.com/a/45212730

        // Email properties
        String host = //"smtp.example.com"; // Your SMTP server address
                        "smtp.gmail.com";

        // the sender email
        String username = "aymen.khiari@esprit.tn";
        String password = "223JMT6166";

        // Set up properties for the mail session
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587"); // Use port 587 for TLS

        // Create a mail session with the specified properties
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender and recipient addresses
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(targetUserMail));

            // Set the email subject and text
            message.setSubject(title);
            message.setText(content);

            // Send the message
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
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


    public static boolean AssertNotNullOrALERT(String name, String prefix) {
        if(StringUtils.isNullOrEmpty(name))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, prefix + " cannot be empty!", ButtonType.OK);
            alert.showAndWait();
            return false;
        }
        return true;
    }
    public static boolean AssertNotNullOrALERT(String name){
        return AssertNotNullOrALERT(name, "Name");
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
        //nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setCellFactory(column -> {
            var q = new AutoCompletionTextFieldCellFactory();
            var textField = q.getTextField();
            textField.textProperty().addListener((observable, oldValue, newValue) ->
            {
                if(StringUtils.isNullOrEmpty(newValue))
                    return;
                if(!textField.isFocused())
                    return;
                //System.out.println("textfield changed from " + oldValue + " to " + newValue);
                try {
                    var suggestions = getGoogleAutocompleteSuggestions(newValue);
                    //System.out.println(suggestions);
                    textField.getEntries().clear();
                    textField.getEntries().addAll(suggestions);
                } catch (IOException e) {
                    System.err.println("Google API error: can't get suggestions");
                }
            });
            return q;
        });

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
            System.out.println("Name commit. " + event.getNewValue());
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
