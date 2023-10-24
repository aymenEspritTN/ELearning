package com.deltaVelorum.coursify.chapitre.gui;

import com.deltaVelorum.coursify.chapitre.entities.Chapitre;
import com.deltaVelorum.coursify.chapitre.entities.ChapitreQuizz;
import com.deltaVelorum.coursify.chapitre.entities.ChapitreQuizzAnswer;
import com.deltaVelorum.coursify.chapitre.entities.ChapitreQuizzQuestion;
import com.deltaVelorum.coursify.chapitre.services.ChapitreQuizzService;
import com.mysql.cj.util.StringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class QuizzEditorController {
    @FXML
    public ListView<String> questionsList;
    @FXML
    public TableView<ChapitreQuizzAnswer> answersList;
    private Chapitre chapitre;
    private ChapitreQuizz quizz;
    private int selectedQuestion;
    public void initialize(Chapitre selectedItem)
    {
        ChapitreQuizzService.getInstance().createTableIfNotExist();

        if(selectedItem == null)
            System.err.println("[FileEditorController] Chapitre cannot be null!");
        chapitre = selectedItem;
        initQuizz();

        Utils.setupQuizzEditorAnswersTreeView(answersList);
        Utils.setupQuestionsList(questionsList, quizz);
        Utils.resetAndFillQuestionsList(questionsList, quizz);

        SelectionModel<String> selectionModel = questionsList.getSelectionModel();
        // Add a change listener to the selection model
        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Selected item: " + newValue);
                selectedQuestion = selectionModel.getSelectedIndex();
                Utils.resetAndFillAnswersList(answersList, quizz, selectedQuestion);
            }
        });
    }
    private void initQuizz()
    {
        var allQuizzes = ChapitreQuizzService.getInstance().getAll();
        for(ChapitreQuizz elem: allQuizzes)
        {
            if(elem.getChapitreId() == chapitre.getId())
            {
                quizz = elem;
                break;
            }
        }
        if(quizz == null)
        {
            System.out.println("No quizz found for chapitre: " + chapitre.getName() + ". will add one.");
            quizz = new ChapitreQuizz(chapitre.getId());
            ChapitreQuizzService.getInstance().add(quizz);
        }
    }
    public void onAddQuestionButtonClick(ActionEvent actionEvent)
    {
        ChapitreQuizzQuestion newQuestion = new ChapitreQuizzQuestion();
        newQuestion.text = "new question";
        quizz.getQuestions().add(newQuestion);
        Utils.resetAndFillQuestionsList(questionsList, quizz);
    }

    public void onRemoveQuestionButtonClick(ActionEvent actionEvent)
    {
        Utils.removeSelectedQuestion(questionsList);
        quizz.getQuestions().remove(selectedQuestion);
        Utils.resetAndFillQuestionsList(questionsList, quizz);
    }

    public void onAddAnswerButtonClick(ActionEvent actionEvent) {
        if(!Utils.assertAnyQuestionSelected(questionsList))
            return;
        ChapitreQuizzAnswer newAnswer = new ChapitreQuizzAnswer();
        newAnswer.setText("new answer");
        Utils.addAnswersToTreeView(answersList, newAnswer);
        quizz.getQuestions().get(selectedQuestion).answers.add(newAnswer);
    }

    public void onRemoveAnswerButtonClick(ActionEvent actionEvent) {
        Utils.removeSelectedAnswer(answersList);

        SelectionModel<ChapitreQuizzAnswer> selectionModel = answersList.getSelectionModel();
        ChapitreQuizzAnswer selectedItem = selectionModel.getSelectedItem();
        quizz.getQuestions().get(selectedQuestion).answers.remove(selectedItem);
    }

    public void onSaveButtonClick(ActionEvent actionEvent) {
        for(var q : quizz.getQuestions())
        {
            if(!Utils.AssertNotNullOrALERT(q.getText(), "Question"))
                return;
            for(var a : q.getAnswers())
            {
                if(!Utils.AssertNotNullOrALERT(a.getText(), "Answer"))
                    return;
            }
        }
        ChapitreQuizzService.getInstance().update(quizz);
    }

    public void onQuitButtonClick(ActionEvent actionEvent) {
        Utils.quitCurrentWindow(actionEvent);
    }

}
