package com.DeltaVelorum.Coursify.Chapitre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChapitreService implements IService<Chapitre> {
    private static ChapitreService instance = new ChapitreService();
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
                    "type INT)";
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
        String query = "INSERT INTO chapitres (name, description, creationDate, type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
        {
            pst.setString(1, chapitre.getName());
            pst.setString(2, chapitre.getDescription());
            pst.setDate(3, new java.sql.Date(chapitre.getCreationDate().getTime()));
            pst.setInt(4, chapitre.getType().ordinal());

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
            System.err.println(ex.getMessage());
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
            System.err.println(ex.getMessage());
        }
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
        String query = "UPDATE chapitres SET name=?, " +
                "description=?, " +
                "creationDate=?, " +
                "type=? " +
                "WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query))
        {
            pst.setString(1, chapitre.getName());
            pst.setString(2, chapitre.getDescription());
            pst.setDate(3, new java.sql.Date(chapitre.getCreationDate().getTime()));
            pst.setInt(4, chapitre.getType().ordinal());
            pst.setInt(5, chapitre.getId());

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Chapitre " + chapitre.getId() + " updated successfully");
            } else {
                System.err.println("Failed to update Chapitre " + chapitre.getId());
            }
        }
        catch (SQLException ex)
        {
            System.err.println(ex.getMessage());
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
                mylist.add(chapitre);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return mylist;
    }
}
