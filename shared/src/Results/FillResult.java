package Results;

public class FillResult {
  private String message;
  private boolean success;



  /**
   * Constructor for FillResult. Contains message to be output.
   * @param message
   */
  public FillResult(String message) {
    this.message=message;
    success = false;
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
