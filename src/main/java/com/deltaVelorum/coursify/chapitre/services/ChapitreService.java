package com.deltaVelorum.coursify.chapitre.services;

import com.deltaVelorum.coursify.chapitre.entities.Chapitre;
import com.deltaVelorum.coursify.chapitre.entities.ChapitreType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChapitreService implements IService<Chapitre> {
    private static final ChapitreService instance = new ChapitreService();
    public static ChapitreService getInstance()
    {
        return instance;
    }
    private ChapitreService()
    {

    }
    @Override
    public void createTableIfNotExist() {
        Connection cnx = DatabaseConnection.getInstance().getCnx();
        try (Statement stmt = cnx.createStatement())
        {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS chapitres (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(255)," +
                    "description TEXT," +
                    "creationDate DATE," +
                    "type INT," +
                    "courseId int)";
            stmt.executeUpdate(createTableQuery);
            System.out.println("Table created or already exists: chapitres");
        } catch (SQLException ex) {
            System.err.println("Error creating table: " + ex.getMessage());
        }
    }
    @Override
    public void add(Chapitre chapitre)
    {
        Connection cnx = DatabaseConnection.getInstance().getCnx();
        String query = "INSERT INTO chapitres (name, description, creationDate, type, courseId) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
        {
            pst.setString(1, chapitre.getName());
            pst.setString(2, chapitre.getDescription());
            pst.setDate(3, new java.sql.Date(chapitre.getCreationDate().getTime()));
            pst.setInt(4, chapitre.getType().ordinal());
            pst.setInt(5, chapitre.getCourseId());

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    chapitre.setId(generatedKeys.getInt(1));
                }
                System.out.println("Chapitre added successfully with ID: " + chapitre.getId());
            } else {
                System.err.println("Failed to add Chapitre");
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    @Override
    public void delete(int id)
    {
        Connection cnx = DatabaseConnection.getInstance().getCnx();
        String query = "DELETE FROM chapitres WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setLong(1, id);
            pst.executeUpdate();
            System.out.println("Chapitre deleted successfully");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void fullDelete(Chapitre c)
    {
        // need to delete everything related to it too, in other tables
        // (otherwise, if linked through foreign key in another table, SQL will throw err
        if(c.getType() == ChapitreType.Quizz)
        {
            var quizz = ChapitreQuizzService.getInstance().getOneByChapitreId(c.getId());
            if(quizz != null) ChapitreQuizzService.getInstance().delete(quizz.getId());
        }
        else
        {
            var file = ChapitreFileService.getInstance().getOneByChapitreId(c.getId());
            if(file != null) ChapitreFileService.getInstance().delete(file.getId());
        }
        delete(c.getId());
        System.out.println("deleted chapitre & all files/quizzes related to chapitre: "
                + c.getId() + ": " + c.getName());
    }

    @Override
    public Chapitre getOne(int id) {
        List<Chapitre> all = getAll();
        for(Chapitre c : all)
        {
            if(c.getId() == id)
                return c;
        }
        return null;
    }

    @Override
    public void update(Chapitre chapitre)
    {
        Connection cnx = DatabaseConnection.getInstance().getCnx();
        String query = "UPDATE chapitres SET " +
                "name=?, " +
                "description=?, " +
                "creationDate=?, " +
                "type=?, " +
                "courseId=? " +
                "WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query))
        {
            pst.setString(1, chapitre.getName());
            pst.setString(2, chapitre.getDescription());
            pst.setDate(3, new java.sql.Date(chapitre.getCreationDate().getTime()));
            pst.setInt(4, chapitre.getType().ordinal());
            pst.setInt(5, chapitre.getCourseId());
            pst.setInt(6, chapitre.getId());

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Chapitre " + chapitre.getId() + " updated successfully");
            } else {
                System.err.println("Failed to update Chapitre " + chapitre.getId());
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    @Override
    public List<Chapitre> getAll()
    {
        Connection cnx = DatabaseConnection.getInstance().getCnx();
        List<Chapitre> mylist = new ArrayList<>();
        String query = "SELECT * FROM chapitres";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Chapitre chapitre = new Chapitre();
                chapitre.setId(rs.getInt("id"));
                chapitre.setName(rs.getString("name"));
                chapitre.setDescription(rs.getString("description"));
                chapitre.setType(ChapitreType.values()[rs.getInt("type")]);
                Timestamp timestamp = rs.getTimestamp("creationDate");
                if (timestamp != null) {
                    chapitre.setCreationDate(new Date(timestamp.getTime()));
                }
                chapitre.setCourseId(rs.getInt("courseId"));
                mylist.add(chapitre);
            }
        } catch (SQLException ex) {
            //System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
        return mylist;
    }
}
