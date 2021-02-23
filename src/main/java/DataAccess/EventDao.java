package DataAccess;

import Models.Event;

import java.sql.Connection;
import java.util.ArrayList;

public class EventDao {
  private final Connection conn;

  public EventDao(Connection conn) {
    this.conn = conn;
  }

  /**
   * Inserts the event into the database
   * @param event
   */
  public void insert(Event event) {

  }

  /**
   * Returns an event based on the ID
   * @param eventID
   * @return single event
   */
  public Event find(String eventID) {
    return null;
  }

  /**
   * Returns all events for family members of the user
   * @param associatedUsername
   * @return ArrayList of Events
   */
  public ArrayList<Event> findEvents(String associatedUsername) {
    return null;
  }

  /**
   * deletes data from the database
   */
  public void delete() {

  }
}
