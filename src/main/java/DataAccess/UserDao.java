package DataAccess;

import Models.Person;
import Models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

  private final Connection conn;

  public UserDao(Connection conn)
  {
    this.conn = conn;
  }

  /**
   * Inserts the User into the database
   * @param user
   */
  public void insert(User user) throws DataAccessException {
    String sql = "INSERT INTO Users (Username, Password, Email, FirstName, LastName, Gender, " +
            "PersonID) VALUES(?,?,?,?,?,?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      //Using the statements built-in set(type) functions we can pick the question mark we want
      //to fill in and give it a proper value. The first argument corresponds to the first
      //question mark found in our sql String
      stmt.setString(1, user.getUsername());
      stmt.setString(2, user.getPassword());
      stmt.setString(3, user.getEmail());
      stmt.setString(4, user.getFirstName());
      stmt.setString(5, user.getLastName());
      stmt.setString(6, user.getGender());
      stmt.setString(7, user.getPersonID());

      stmt.executeUpdate();

    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting into the database");
    }
  }

  /**
   * Searches for the user based on the ID
   * @param username
   * @return User object
   */
  public User find(String username) throws DataAccessException {
    User user;
    ResultSet rs = null;
    String sql = "SELECT * FROM Users WHERE Username = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      if (rs.next()) {
        user = new User(rs.getString("Username"), rs.getString("Password"), rs.getString("Email"),
                rs.getString("FirstName"), rs.getString("LastName"), rs.getString("Gender"),
                rs.getString("PersonID"));
        return user;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding user");
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
   * deletes data from the database
   */
  public void clear() {
    String sql = "DELETE FROM Users";
    try(PreparedStatement statement =conn.prepareStatement(sql)) {
      statement.execute();
    }
    catch(SQLException e) {
      e.printStackTrace();
    }
  }
}
