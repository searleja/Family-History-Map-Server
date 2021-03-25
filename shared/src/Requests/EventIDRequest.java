package Requests;

public class EventIDRequest {
  private String eventID;
  private String authToken;

  /**
   * Constructor for EventIDRequest. Stores the eventID and associated authToken for the user.
   * @param eventID
   * @param authToken
   */
  public EventIDRequest(String eventID, String authToken) {
    this.eventID=eventID;
    this.authToken=authToken;
  }

  public String getEventID() {
    return eventID;
  }

  public void setEventID(String eventID) {
    this.eventID=eventID;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }
}
