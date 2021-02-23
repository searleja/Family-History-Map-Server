package Models;

public class Person {
  private String personID;
  private String associatedUsername;
  private String firstName;
  private String lastName;
  private String gender;
  private String FatherID;
  private String MotherID;
  private String SpouseID;

  /**
   * Constructor for the Person class. Stores data about the person and immediate family.
   * @param personID
   * @param associatedUsername
   * @param firstName
   * @param lastName
   * @param gender
   * @param fatherID
   * @param motherID
   * @param spouseID
   */
  public Person(String personID, String associatedUsername, String firstName, String lastName, String gender,
                String fatherID, String motherID, String spouseID) {
    this.personID=personID;
    this.associatedUsername=associatedUsername;
    this.firstName=firstName;
    this.lastName=lastName;
    this.gender=gender;
    FatherID=fatherID;
    MotherID=motherID;
    SpouseID=spouseID;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
  }

  public String getUsername() {
    return associatedUsername;
  }

  public void setUsername(String username) {
    this.associatedUsername=username;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName=firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName=lastName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender=gender;
  }

  public String getFatherID() {
    return FatherID;
  }

  public void setFatherID(String fatherID) {
    FatherID=fatherID;
  }

  public String getMotherID() {
    return MotherID;
  }

  public void setMotherID(String motherID) {
    MotherID=motherID;
  }

  public String getSpouseID() {
    return SpouseID;
  }

  public void setSpouseID(String spouseID) {
    SpouseID=spouseID;
  }
}
