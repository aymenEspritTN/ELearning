package com.deltaVelorum.coursify.chapitre.services;

import com.deltaVelorum.coursify.DatabaseConnection;
import com.deltaVelorum.coursify.IService;
import com.deltaVelorum.coursify.chapitre.entities.ChapitreFile;
import com.deltaVelorum.coursify.chapitre.entities.ChapitreQuizz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChapitreFileService implements IService<ChapitreFile> {

    private static final ChapitreFileService instance = new ChapitreFileService();
    public static ChapitreFileService getInstance()
    {
        return instance;
    }

    private ChapitreFileService(){}
    @Override
    public void createTableIfNotExist() {
        Connection cnx = DatabaseConnection.getInstance().getCnx();
        try (Statement stmt = cnx.createStatement())
        {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS chapitreFiles ( " +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(255), " +
                    "content LONGBLOB, " +
                    "chapitreId INT, FOREIGN KEY (chapitreId) REFERENCES chapitres(id) " +
                    ")";
            stmt.executeUpdate(createTableQuery);
            System.out.println("Table created or already exists: chapitreFiles");
        } catch (SQLException ex) {
            System.err.println("Error creating table: " + ex.getMessage());
        }
    }

    @Override
    public void add(ChapitreFile instance) {
        Connection cnx = DatabaseConnection.getInstance().getCnx();
        String query = "INSERT INTO chapitreFiles (id, name, content, chapitreId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
        {
            pst.setInt(1, instance.getId());
            pst.setString(2, instance.getName());
            pst.setBytes(3, instance.getContent());
            pst.setInt(4, instance.getChapitreId());

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    instance.setId(generatedKeys.getInt(1));
                }
                System.out.println("File added successfully with ID: " + instance.getId());
            } else {
                System.err.println("Failed to add File");
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(ChapitreFile instance) {

        Connection cnx = DatabaseConnection.getInstance().getCnx();
        String query = "UPDATE chapitreFiles SET " +
                "name=?, " +
                "content=?, " +
                "chapitreId=? " +
                "WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query))
        {
            pst.setString(1, instance.getName());
            pst.setBytes(2, instance.getContent());
            pst.setInt(3, instance.getChapitreId());
            pst.setInt(4, instance.getId());

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("File " + instance.getId() + " updated successfully");
            } else {
                System.err.println("Failed to update File " + instance.getId());
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        Connection cnx = DatabaseConnection.getInstance().getCnx();
        String query = "DELETE FROM chapitreFiles WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setLong(1, id);
            pst.executeUpdate();
            System.out.println("File deleted successfully");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public ChapitreFile getOne(int id) {
        List<ChapitreFile> all = getAll();
        for(ChapitreFile e : all)
        {
            if(e.getId() == id)
                return e;
        }
        return null;
    }

    public ChapitreFile getOneByChapitreId(int chapitreId)
    {
        List<ChapitreFile> all = getAll();
        for(ChapitreFile e : all)
        {
            if(e.getChapitreId() == chapitreId)
                return e;
        }
        return null;
    }

    @Override
    public List<ChapitreFile> getAll() {
        Connection cnx = DatabaseConnection.getInstance().getCnx();
        List<ChapitreFile> mylist = new ArrayList<>();
        String query = "SELECT * FROM chapitreFiles";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                ChapitreFile file = new ChapitreFile(rs.getInt("chapitreId"));
                file.setId(rs.getInt("id"));
                file.setName(rs.getString("name"));
                file.setContent(rs.getBytes("content"));
                mylist.add(file);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return mylist;
    }

}
