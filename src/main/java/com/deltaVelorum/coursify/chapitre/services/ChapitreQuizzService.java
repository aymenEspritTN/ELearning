package com.deltaVelorum.coursify.chapitre.services;

import com.deltaVelorum.coursify.DatabaseConnection;
import com.deltaVelorum.coursify.IService;
import com.deltaVelorum.coursify.chapitre.entities.ChapitreQuizz;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChapitreQuizzService implements IService<ChapitreQuizz> {

    private static ChapitreQuizzService instance = new ChapitreQuizzService();
    public static ChapitreQuizzService getInstance()
    {
        return instance;
    }
    @Override
    public void createTableIfNotExist() {
        Connection cnx = DatabaseConnection.getInstance().getCnx();
        try (Statement stmt = cnx.createStatement())
        {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS chapitreQuizzes ( " +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(255), " +
                    "questions VARCHAR(65535), " +
                    "answers VARCHAR(255), " +
                    "chapitreId INT, FOREIGN KEY (chapitreId) REFERENCES chapitres(id) " +
                    ")";
            stmt.executeUpdate(createTableQuery);
            System.out.println("Table created or already exists: chapitreFiles");
        } catch (SQLException ex) {
            System.err.println("Error creating table: " + ex.getMessage());
        }
    }

    @Override
    public void add(ChapitreQuizz instance) {
        Connection cnx = DatabaseConnection.getInstance().getCnx();
        String query = "INSERT INTO chapitreQuizzes (questions, answers, chapitreId) VALUES (?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
        {
            pst.setString(1, String.join(",", instance.getQuestions()));
            int[] answersInt = instance.getAnswers();
            String[] answers = new String[answersInt.length];
            for (int i = 0; i < answers.length; i++) {
                answers[i] = String.valueOf(answersInt[i]);
            }
            pst.setString(2, String.join(",", answers));
            pst.setInt(3, instance.getChapitreId());

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    instance.setId(generatedKeys.getInt(1));
                }
                System.out.println("Quizz added successfully with ID: " + instance.getId());
            } else {
                System.err.println("Failed to add Quizz");
            }
        }
        catch (SQLException ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void update(ChapitreQuizz instance) {
        Connection cnx = DatabaseConnection.getInstance().getCnx();
        String query = "UPDATE chapitreQuizzes SET " +
                "questions=?, " +
                "answers=?, " +
                "chapitreId=? " +
                "WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query))
        {
            pst.setString(1, String.join(",", instance.getQuestions()));
            int[] answersInt = instance.getAnswers();
            String[] answers = new String[answersInt.length];
            for (int i = 0; i < answers.length; i++) {
                answers[i] = String.valueOf(answersInt[i]);
            }
            pst.setString(2, String.join(",", answers));
            pst.setInt(3, instance.getChapitreId());
            pst.setInt(4, instance.getId());

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Quizz " + instance.getId() + " updated successfully");
            } else {
                System.err.println("Failed to update Quizz " + instance.getId());
            }
        }
        catch (SQLException ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        Connection cnx = DatabaseConnection.getInstance().getCnx();
        String query = "DELETE FROM chapitreQuizzes WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Quizz deleted successfully");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public ChapitreQuizz getOne(int id) {
        List<ChapitreQuizz> all = getAll();
        for(ChapitreQuizz e : all)
        {
            if(e.getId() == id)
                return e;
        }
        return null;
    }
    @Override
    public List<ChapitreQuizz> getAll() {
        Connection cnx = DatabaseConnection.getInstance().getCnx();
        List<ChapitreQuizz> mylist = new ArrayList<>();
        String query = "SELECT * FROM chapitres";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                ChapitreQuizz quizz = new ChapitreQuizz();
                quizz.setId(rs.getInt("id"));
                String[] questions = rs.getString("questions").split(",");
                quizz.setQuestions(questions);
                String[] answersStr = rs.getString("answers").split(",");
                int[] answers = Arrays.stream(answersStr).mapToInt(Integer::parseInt).toArray();
                quizz.setAnswers(answers);
                quizz.setChapitreId(rs.getInt("chapitreId"));
                mylist.add(quizz);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return mylist;
    }
}
