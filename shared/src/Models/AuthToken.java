package Models;

public class AuthToken {
  private String authToken;
  private String associatedUsername;

  /**
   * Constructor for the AuthToken class. Stores data about the user's username and AuthToken.
   * @param authToken
   */
  public AuthToken(String authToken, String associatedUsername) {
    this.authToken=authToken;
    this.associatedUsername=associatedUsername;
  }

  public String getAssociatedUsername() {
    return associatedUsername;
  }

  public void setAssociatedUsername(String associatedUsername) {
    this.associatedUsername=associatedUsername;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }


  @Override
  public boolean equals(Object o) {
    if (o == null)
      return false;
    if (o instanceof AuthToken) {
      AuthToken oAuthToken = (AuthToken) o;
      return oAuthToken.getAuthToken().equals(getAuthToken()) &&
              oAuthToken.getAssociatedUsername().equals(getAssociatedUsername());
    } else {
      return false;
    }
  }
}
