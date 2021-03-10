package Services;

import DataAccess.*;
import Models.*;
import Requests.LoadRequest;
import Results.LoadResult;

import java.sql.Connection;
import java.util.ArrayList;

public class LoadService {

  /**
   * Clears all data from the database (just like the /clear API), and then loads the posted user, person, and event data into the database.
   * @param request
   * @return LoadResult
   */
  public LoadResult load(LoadRequest request) throws DataAccessException {

    new ClearService().clear();

    LoadResult loadResult;
    Database db = new Database();
    Connection conn = db.openConnection();

    EventDao eventDao = new EventDao(conn);
    UserDao userDao = new UserDao(conn);
    PersonDao personDao = new PersonDao(conn);

    ArrayList<Event> events = request.getEvents();
    ArrayList<User> users = request.getUsers();
    ArrayList<Person> persons = request.getPersons();

    int eventsAdded = 0;
    int usersAdded = 0;
    int personsAdded = 0;

    if (events.size() == 0 && users.size() == 0 && persons.size() == 0) {
      loadResult = new LoadResult("Error: missing values", false);
      db.closeConnection(false);
      return loadResult;
    }


    for (Event event : events) {
      try {
        eventDao.insert(event);
        eventsAdded++;
      }
      catch (DataAccessException e) {
        loadResult = new LoadResult("Error: Invalid request data (events)", false);
        e.printStackTrace();
        db.closeConnection(false);
        return loadResult;
      }
    }

    for (User user : users) {
      try {
        userDao.insert(user);
        usersAdded++;
      }
      catch (DataAccessException e) {
        loadResult = new LoadResult("Error: Invalid request data (users)", false);
        e.printStackTrace();
        db.closeConnection(false);
        return loadResult;
      }
    }

    for (Person person : persons) {
      try {
        personDao.insert(person);
        personsAdded++;
      }
      catch (DataAccessException e) {
        loadResult = new LoadResult("Error: Invalid request data (persons)", false);
        e.printStackTrace();
        db.closeConnection(false);
        return loadResult;
      }
    }

    loadResult = new LoadResult("Successfully added " + usersAdded + " users, " + personsAdded +
            " persons, and " + eventsAdded + " events to the database.", true);

    db.closeConnection(true);
    return loadResult;
  }
}
