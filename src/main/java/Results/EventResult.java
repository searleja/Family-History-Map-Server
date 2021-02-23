package Results;

import Models.Event;

public class EventResult {
  private Event[] data;
  private String message;

  /**
   * Constructor for EventResult. Stores data about all events for all family members of the user.
   * @param data
   */
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
