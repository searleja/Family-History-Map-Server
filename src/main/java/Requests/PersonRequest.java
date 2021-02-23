package Requests;

public class PersonRequest {
  private String authToken;

  /**
   * Constructor for PersonRequest. Stores user's authToken.
   * @param authToken
   */
  public PersonRequest(String authToken) {
    this.authToken=authToken;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }
}
