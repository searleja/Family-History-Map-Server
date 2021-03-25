package DataAccess;

import Models.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
  public void insert(Event event) throws DataAccessException {
    String sql = "INSERT INTO Events (EventID, AssociatedUsername, PersonID, Latitude, Longitude, " +
            "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      //Using the statements built-in set(type) functions we can pick the question mark we want
      //to fill in and give it a proper value. The first argument corresponds to the first
      //question mark found in our sql String
      stmt.setString(1, event.getEventID());
      stmt.setString(2, event.getAssociatedUsername());
      stmt.setString(3, event.getPersonID());
      stmt.setFloat(4, event.getLatitude());
      stmt.setFloat(5, event.getLongitude());
      stmt.setString(6, event.getCountry());
      stmt.setString(7, event.getCity());
      stmt.setString(8, event.getEventType());
      stmt.setInt(9, event.getYear());

      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting into the database");
    }
  }

  /**
   * Returns an event based on the ID
   * @param eventID
   * @return single event
   */
  public Event find(String eventID) throws DataAccessException {
    Event event;
    ResultSet rs = null;
    String sql = "SELECT * FROM Events WHERE EventID = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, eventID);
      rs = stmt.executeQuery();
      if (rs.next()) {
        event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                rs.getInt("Year"));
        return event;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding event");
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
   * Returns all events for family members of the user
   * @param associatedUsername
   * @return ArrayList of Events
   */
  public ArrayList<Event> findEvents(String associatedUsername) throws DataAccessException {
    ArrayList<Event> events= new ArrayList<>();
    Event currentEvent;
    ResultSet rs = null;
    String sql = "SELECT * FROM Events WHERE AssociatedUsername = ?";
    try (PreparedStatement stmt = conn.prepareStatement(sql)){
      stmt.setString(1, associatedUsername);
      rs = stmt.executeQuery();
      while (rs.next()) {
        currentEvent = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                rs.getString("PersonID"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                rs.getString("Country"), rs.getString("City"), rs.getString("EventType"),
                rs.getInt("Year"));
        events.add(currentEvent);
      }
      if (events.size() == 0) return null;
      return events;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding events");
    }finally {
      if(rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * deletes data from the database
   */
  public void clear(String username) {
    String sql="DELETE FROM Events WHERE AssociatedUsername = ?";
    try (PreparedStatement statement=conn.prepareStatement(sql)) {
      statement.setString(1, username);
      statement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
