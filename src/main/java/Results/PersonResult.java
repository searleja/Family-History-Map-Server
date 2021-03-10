package Results;

import Models.Person;

import java.util.ArrayList;

public class PersonResult {
  private ArrayList<Person> data;
  private String message;
  private boolean success;

  /**
   * Constructor for PersonResult. Contains data about all ancestors of the User.
   * @param data
   */
  public PersonResult(ArrayList<Person> data) {
    this.data=data;
    success = true;
  }

  public PersonResult(String message) {
    this.message=message;
    success = false;
  }

  public boolean isSuccessful() {
    return success;
  }

  public ArrayList<Person> getData() {
    return data;
  }

  public void setData(ArrayList<Person> data) {
    this.data=data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }
}
