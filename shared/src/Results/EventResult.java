package Results;

import Models.Event;

import java.util.ArrayList;

public class EventResult {
  private ArrayList<Event> data;
  private String message;
  private boolean success;

  /**
   * Constructor for EventResult. Stores data about all events for all family members of the user.
   * @param data
   */
  public EventResult(ArrayList<Event> data) {
    this.data=data;
    success = true;
  }

  public EventResult(String message) {
    this.message = message;
    success = false;
  }

  public boolean isSuccessful() {
    return success;
  }

  public ArrayList<Event> getData() {
    return data;
  }

  public void setData(ArrayList<Event> data) {
    this.data=data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }
}
