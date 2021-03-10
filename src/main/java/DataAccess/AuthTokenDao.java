package DataAccess;

import Models.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthTokenDao {
  private final Connection conn;

  public AuthTokenDao(Connection conn) {this.conn=conn;}

  /**
   * Inserts the AuthToken into the database
   * @param token
   */
  public void insert(AuthToken token) throws DataAccessException {
    String sql = "INSERT INTO AuthorizationToken (AuthToken, AssociatedUsername) VALUES(?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      //Using the statements built-in set(type) functions we can pick the question mark we want
      //to fill in and give it a proper value. The first argument corresponds to the first
      //question mark found in our sql String
      stmt.setString(1, token.getAuthToken());
      stmt.setString(2, token.getAssociatedUsername());

      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting into the database");
    }
  }

  /**
   * Searches for the given authToken
   * @param authToken
   * @return authToken if successful, else null
   */
  public AuthToken find(String authToken) throws DataAccessException {
    AuthToken token;
    ResultSet rs = null;
    String sql = "SELECT * FROM AuthorizationToken WHERE AuthToken = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, authToken);
      rs = stmt.executeQuery();
      if (rs.next()) {
        token = new AuthToken(rs.getString("AuthToken"), rs.getString("AssociatedUsername"));
        return token;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding authtoken");
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

  public String findUsername(String token) {
    String username;
    ResultSet rs = null;
    String query = "SELECT AssociatedUsername FROM AuthorizationToken WHERE AuthToken = ?";
    try (PreparedStatement stmt = conn.prepareStatement(query);){
      stmt.setString(1, token);
      rs = stmt.executeQuery();
      username = rs.getString(1);
      stmt.close();
      rs.close();
      return username;
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * deletes data from the database
   */
  public void delete() {
    String sql="DELETE FROM AuthorizationToken";
    try (PreparedStatement statement=conn.prepareStatement(sql)) {
      statement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
