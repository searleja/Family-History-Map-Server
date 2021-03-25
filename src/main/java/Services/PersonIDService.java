package Services;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Models.Person;
import Requests.PersonIDRequest;
import Results.PersonIDResult;

import java.sql.Connection;

public class PersonIDService {

  /**
   * Returns the single Person object with the specified ID.
   * @param id
   * @return PersonIDResult
   */
  public PersonIDResult getPerson(PersonIDRequest id) throws DataAccessException {
    Database db = new Database();
    Connection conn = db.openConnection();
    PersonIDResult result;
    AuthTokenDao authDao = new AuthTokenDao(conn);
    PersonDao personDao = new PersonDao(conn);

    if (authDao.find(id.getAuthToken()) == null) {
      result = new PersonIDResult("Error: Invalid auth token");
      db.closeConnection(false);
      return result;
    }

    Person myPerson = personDao.find(id.getPersonID());

    if (myPerson.getUsername() == null) {
      result = new PersonIDResult("Error: Invalid personID parameter");
      db.closeConnection(false);
      return result;
    }
    String test = authDao.findUsername(id.getAuthToken());
    if (!myPerson.getUsername().equals(authDao.findUsername(id.getAuthToken()))) {
      result = new PersonIDResult("Error: Requested person does not belong to this user");
      db.closeConnection(false);
      return result;
    }

    result = new PersonIDResult(myPerson.getUsername(), myPerson.getPersonID(), myPerson.getFirstName(), myPerson.getLastName(),
            myPerson.getGender(), myPerson.getFatherID(), myPerson.getMotherID(), myPerson.getSpouseID(), "Success");
    db.closeConnection(true);
    return result;
  }
}
