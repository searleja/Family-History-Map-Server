package Services;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Requests.PersonRequest;
import Results.PersonResult;

import java.sql.Connection;

public class PersonService {

  /**
   * Returns ALL family members of the current user. The current user is determined from the provided auth token.
   * @param request
   * @return
   */
  public PersonResult getPeople(PersonRequest request) throws DataAccessException {
    Database db = new Database();
    Connection conn = db.openConnection();
    PersonResult result;
    AuthTokenDao authDao = new AuthTokenDao(conn);
    PersonDao personDao = new PersonDao(conn);

    if (authDao.find(request.getAuthToken()) == null) {
      result = new PersonResult("Error: Invalid auth token");
      db.closeConnection(false);
      return result;
    }

    result = new PersonResult(personDao.findPeople(authDao.findUsername(request.getAuthToken())));

    db.closeConnection(true);
    return result;
  }
}
