package Requests;

public class EventRequest {
  private String authToken;

  /**
   * Constructor for EventRequest. Stores the user's authToken.
   * @param authToken
   */
  public EventRequest(String authToken) {
    this.authToken=authToken;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }
}
