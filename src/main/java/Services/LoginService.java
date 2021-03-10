package Services;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Models.AuthToken;
import Models.User;
import Requests.LoginRequest;
import Results.LoginResult;

import java.sql.Connection;

import static java.util.UUID.randomUUID;

public class LoginService {

  /**
   * Logs in the user and returns an auth token.
   * @param log
   * @return LoginResult
   */
  public LoginResult login(LoginRequest log) throws DataAccessException {
    LoginResult result;
    Database db = new Database();
    Connection conn = db.openConnection();
    UserDao userDao = new UserDao(conn);
    User testUser = userDao.find(log.getUsername());

    if (testUser == null) {
      result = new LoginResult("Error:  Username doesn't exist");
      db.closeConnection(false);
      return result;
    }
    else if (!testUser.getPassword().equals(log.getPassword())) {
      result = new LoginResult("Error:  Wrong password");
      db.closeConnection(false);
      return result;
    }
    else {
      String token = randomUUID().toString().substring(0,8);

      AuthToken a = new AuthToken(token, log.getUsername());
      AuthTokenDao aDao = new AuthTokenDao(conn);
      aDao.insert(a);

      result = new LoginResult(token, log.getUsername(), userDao.find(log.getUsername()).getPersonID());
      db.closeConnection(true);
      return result;
    }
  }
}
