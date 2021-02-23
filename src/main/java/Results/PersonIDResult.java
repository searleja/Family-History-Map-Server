package Results;

public class PersonIDResult {
  private String associatedUsername;
  private String personID;
  private String firstName;
  private String lastName;
  private String gender;
  private String fatherID;
  private String motherID;
  private String spouseID;
  private String message;

  /**
   * Constructor for PersonIDResult. Contains all data about the resulting Person.
   * @param associatedUsername
   * @param personID
   * @param firstName
   * @param lastName
   * @param gender
   * @param fatherID
   * @param motherID
   * @param spouseID
   * @param message
   */
  public PersonIDResult(String associatedUsername, String personID, String firstName, String lastName, String gender,
                        String fatherID, String motherID, String spouseID, String message) {
    this.associatedUsername=associatedUsername;
    this.personID=personID;
    this.firstName=firstName;
    this.lastName=lastName;
    this.gender=gender;
    this.fatherID=fatherID;
    this.motherID=motherID;
    this.spouseID=spouseID;
    this.message=message;
  }

  public String getAssociatedUsername() {
    return associatedUsername;
  }

  public void setAssociatedUsername(String associatedUsername) {
    this.associatedUsername=associatedUsername;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
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
    return fatherID;
  }

  public void setFatherID(String fatherID) {
    this.fatherID=fatherID;
  }

  public String getMotherID() {
    return motherID;
  }

  public void setMotherID(String motherID) {
    this.motherID=motherID;
  }

  public String getSpouseID() {
    return spouseID;
  }

  public void setSpouseID(String spouseID) {
    this.spouseID=spouseID;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }
}
