package Requests;

public class PersonIDRequest {
  private String personID;
  private String authToken;

  /**
   * Constructor for PersonIDRequest. Stores specified ID and user's authToken.
   * @param personID
   * @param authToken
   */
  public PersonIDRequest(String personID, String authToken) {
    this.personID=personID;
    this.authToken=authToken;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }
}
