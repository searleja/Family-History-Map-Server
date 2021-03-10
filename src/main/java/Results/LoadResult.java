package Results;

public class LoadResult {
  private String message;
  private boolean success;

  /**
   * Constructor for LoadResult. Contains the message to be output.
   * @param message
   */
  public LoadResult(String message, boolean success) {
    this.message=message;
    this.success=success;
  }

  public boolean isSuccessful() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success=success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }
}
