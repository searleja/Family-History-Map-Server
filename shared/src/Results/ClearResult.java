package Results;

public class ClearResult {
  private String message;
  private boolean success;
  /**
   * Constructor for ClearResult. Stores the message to be output.
   * @param message
   */
  public ClearResult(String message) {
    this.message=message;
    success = false;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success=success;
  }
}
