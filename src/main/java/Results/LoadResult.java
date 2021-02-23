package Results;

public class LoadResult {
  private String message;

  /**
   * Constructor for LoadResult. Contains the message to be output.
   * @param message
   */
  public LoadResult(String message) {
    this.message=message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }
}
