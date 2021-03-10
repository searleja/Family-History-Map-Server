package Services;

import DataAccess.*;
import Requests.EventRequest;
import Results.EventResult;

import java.sql.Connection;

public class EventService {

  /**
   * Returns ALL events for ALL family members of the current user. The current user is determined from the provided auth token.
   * @param request
   * @return EventResult
   */
  public EventResult getEvents(EventRequest request) throws DataAccessException {
    Database db = new Database();
    Connection conn = db.openConnection();
    EventResult result;
    AuthTokenDao authDao = new AuthTokenDao(conn);
    EventDao eventDao = new EventDao(conn);

    if (authDao.find(request.getAuthToken()) == null) {
      result = new EventResult("Error: Invalid auth token");
      db.closeConnection(false);
      return result;
    }

    result = new EventResult(eventDao.findEvents(authDao.findUsername(request.getAuthToken())));

    db.closeConnection(true);
    return result;
  }
}
