package Results;

public class LoginResult {
  private String authtoken;
  private String username;
  private String personID;
  private String message;
  private boolean success;



  /**
   * Constructor for LoginResult. Contains data about the current User.
   * @param authtoken
   * @param username
   * @param personID
   */
  public LoginResult(String authtoken, String username, String personID) {
    this.authtoken=authtoken;
    this.username=username;
    this.personID=personID;
    success=true;
  }

  public LoginResult(String failMessage) {
    this.message = failMessage;
     success = false;
  }

  public boolean isSuccessful() {
    return success;
  }

  public void setSuccessful(boolean success) {
    this.success=success;
  }

  public String getAuthtoken() {
    return authtoken;
  }

  public void setAuthtoken(String authtoken) {
    this.authtoken=authtoken;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }
}
