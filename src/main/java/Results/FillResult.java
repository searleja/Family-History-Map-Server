package Results;

public class FillResult {
  private String message;

  /**
   * Constructor for FillResult. Contains message to be output.
   * @param message
   */
  public FillResult(String message) {
    this.message=message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }
}
