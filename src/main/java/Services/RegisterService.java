package Services;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Models.User;
import Requests.FillRequest;
import Requests.LoginRequest;
import Requests.RegisterRequest;
import Results.LoginResult;
import Results.RegisterResult;

import java.sql.Connection;

import static java.util.UUID.randomUUID;

public class RegisterService {

  /**
   * Creates a new user account, generates 4 generations of ancestor data for the new user, logs the user in,
   * and returns an auth token.
   * @param reg
   * @return RegisterResult
   */
  public RegisterResult register(RegisterRequest reg) throws DataAccessException {
    Database db = new Database();
    Connection conn = db.openConnection();
    RegisterResult result;
    UserDao userDao = new UserDao(conn);
    User myUser;

    if (userDao.find(reg.getUsername()) != null) {
      result = new RegisterResult("Error: Username already exists.");
      db.closeConnection(false);
      return result;
    }

    String personID = randomUUID().toString().substring(0,8); //from TA in slack

    myUser = new User(reg.getUsername(), reg.getPassword(), reg.getEmail(), reg.getFirstName(), reg.getLastName(),
            reg.getGender(), personID);

    try {
      userDao.insert(myUser);
    }
    catch(DataAccessException e) {
      result = new RegisterResult("Failed to register user");
      db.closeConnection(false);
      e.printStackTrace();
      return result;
    }

    //close the connection first because otherwise new user can't be found in the login functions.
    db.closeConnection(true);

    //this will generate the authtoken and log in the user after registration.
    LoginRequest loginRequest = new LoginRequest(reg.getUsername(), reg.getPassword());
    LoginResult loginResult = new LoginService().login(loginRequest);


    result = new RegisterResult(loginResult.getAuthtoken(), myUser.getUsername(), myUser.getPersonID());

    new FillService().fill(new FillRequest(result.getUsername(), 4));

    return result;
  }
}