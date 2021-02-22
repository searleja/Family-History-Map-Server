package Results;

import Models.Person;

public class PersonResult {
  private Person[] data;
  private String message;

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
