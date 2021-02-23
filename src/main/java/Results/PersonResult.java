package Results;

import Models.Person;

public class PersonResult {
  private Person[] data;
  private String message;

  /**
   * Constructor for PersonResult. Contains data about all ancestors of the User.
   * @param data
   * @param message
   */
  public PersonResult(Person[] data, String message) {
    this.data=data;
    this.message=message;
  }

  public Person[] getData() {
    return data;
  }

  public void setData(Person[] data) {
    this.data=data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }
}
