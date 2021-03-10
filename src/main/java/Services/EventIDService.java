package Services;

import DataAccess.*;
import Models.Event;
import Requests.EventIDRequest;
import Results.EventIDResult;

import java.sql.Connection;

public class EventIDService {

  /**
   * Returns the single Event object with the specified ID.
   * @param request
   * @return EventIDResult
   */
  public EventIDResult getEvent(EventIDRequest request) throws DataAccessException {
    Database db = new Database();
    Connection conn = db.openConnection();
    EventIDResult result;
    AuthTokenDao authDao = new AuthTokenDao(conn);
    EventDao eventDao = new EventDao(conn);


    if (authDao.find(request.getAuthToken()) == null) {
      result = new EventIDResult("Error: Invalid auth token");
      db.closeConnection(false);
      return result;
    }

    Event myEvent = eventDao.find(request.getEventID());

    if (myEvent.getAssociatedUsername() == null) {
      result = new EventIDResult("Error: Invalid eventID parameter");
      db.closeConnection(false);
      return result;
    }
    if (!myEvent.getAssociatedUsername().equals(authDao.findUsername(request.getAuthToken()))) {
      result = new EventIDResult("Error: Requested event does not belong to this user");
      db.closeConnection(false);
      return result;
    }

    result = new EventIDResult(myEvent.getAssociatedUsername(), myEvent.getEventID(), myEvent.getPersonID(),
           myEvent.getLatitude(), myEvent.getLongitude(), myEvent.getCountry(), myEvent.getCity(), myEvent.getEventType(), myEvent.getYear());
    db.closeConnection(true);
    return result;
  }
}
