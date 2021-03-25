package Requests;

public class FillRequest {
  private String username;
  private int numGenerations;

  /**
   * Constructor for FillRequest. Stores the username and number of generations.
   * @param username
   * @param numGenerations
   */
  public FillRequest(String username, int numGenerations) {
    this.username=username;
    this.numGenerations=numGenerations;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username=username;
  }

  public int getNumGenerations() {
    return numGenerations;
  }

  public void setNumGenerations(int numGenerations) {
    this.numGenerations=numGenerations;
  }
}
