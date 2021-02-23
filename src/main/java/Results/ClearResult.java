package Results;

public class ClearResult {
  private String message;

  /**
   * Constructor for ClearResult. Stores the message to be output.
   * @param message
   */
  public ClearResult(String message) {
    this.message=message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }
}
