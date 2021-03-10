package DataAccess;

import Models.Event;
import Models.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonDao {

  private final Connection conn;

  public PersonDao(Connection conn)
  {
    this.conn = conn;
  }

  /**
   * Inserts the Person into the database
   * @param person
   */
  public void insert(Person person) throws DataAccessException {
    String sql = "INSERT INTO Persons (PersonID, AssociatedUsername, FirstName, LastName, Gender, " +
            "FatherID, MotherID, SpouseID) VALUES(?,?,?,?,?,?,?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      //Using the statements built-in set(type) functions we can pick the question mark we want
      //to fill in and give it a proper value. The first argument corresponds to the first
      //question mark found in our sql String
      stmt.setString(1, person.getPersonID());
      stmt.setString(2, person.getUsername());
      stmt.setString(3, person.getFirstName());
      stmt.setString(4, person.getLastName());
      stmt.setString(5, person.getGender());
      stmt.setString(6, person.getFatherID());
      stmt.setString(7, person.getMotherID());
      stmt.setString(8, person.getSpouseID());

      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting into the database");
    }
  }

  /**
   * Searches for the person associated with the personID
   * @param personID
   * @return Person object
   */
  public Person find(String personID) throws DataAccessException {
    Person person;
    ResultSet rs = null;
    String sql = "SELECT * FROM Persons WHERE PersonID = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, personID);
      rs = stmt.executeQuery();
      if (rs.next()) {
        person = new Person(rs.getString("PersonID"), rs.getString("AssociatedUsername"),
                rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"),
                rs.getString("FatherID"), rs.getString("MotherID"), rs.getString("SpouseID"));
        return person;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding person");
    } finally {
      if(rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

    }
    return null;
  }

  /**
   * Searches for all ancestors of the given user
   * @param associatedUsername
   * @return ArrayList of ancestors
   */
  public ArrayList<Person> findPeople(String associatedUsername) throws DataAccessException {
    ArrayList<Person> people= new ArrayList<>();
    Person currentPerson;
    ResultSet rs = null;
    String sql = "SELECT * FROM Persons WHERE AssociatedUsername = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)){
      stmt.setString(1, associatedUsername);
      rs = stmt.executeQuery();
      while (rs.next()) {
        currentPerson = new Person(rs.getString("PersonID"), rs.getString("AssociatedUsername"),
                rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"),
                rs.getString("FatherID"), rs.getString("MotherID"), rs.getString("SpouseID"));
        people.add(currentPerson);
      }
      return people;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding events");
    }finally {
      if(rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * deletes data from the database
   */
  public void clear(String username) {
    String sql = "DELETE FROM Persons WHERE AssociatedUsername = ?";
    try(PreparedStatement statement =conn.prepareStatement(sql)) {
      statement.setString(1, username);
      statement.executeUpdate();
    }
    catch(SQLException e) {
      e.printStackTrace();
    }

  }
}
