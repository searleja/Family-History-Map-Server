package Results;

import Models.Event;

public class EventResult {
  private Event[] data;
  private String message;

  public EventResult(Event[] data) {
    this.data=data;
  }

  public Event[] getData() {
    return data;
  }

  public void setData(Event[] data) {
    this.data=data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }
}
