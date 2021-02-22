package Models;

public class AuthToken {
  private String authToken;

  public AuthToken(String authToken) {
    this.authToken=authToken;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }
}
