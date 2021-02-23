package Results;

public class EventIDResult {
  private String associatedUsername;
  private String eventID;
  private String personID;
  private long latitude;
  private long longitude;
  private String country;
  private String city;
  private String eventType;
  private int year;
  private String message;

  /**
   * Constructor for EventIDResult. Contains all data about the resulting event.
   * @param associatedUsername
   * @param eventID
   * @param personID
   * @param latitude
   * @param longitude
   * @param country
   * @param city
   * @param eventType
   * @param year
   * @param message
   */
  public EventIDResult(String associatedUsername, String eventID, String personID, long latitude, long longitude,
                       String country, String city, String eventType, int year, String message) {
    this.associatedUsername=associatedUsername;
    this.eventID=eventID;
    this.personID=personID;
    this.latitude=latitude;
    this.longitude=longitude;
    this.country=country;
    this.city=city;
    this.eventType=eventType;
    this.year=year;
    this.message=message;
  }

  public String getAssociatedUsername() {
    return associatedUsername;
  }

  public void setAssociatedUsername(String associatedUsername) {
    this.associatedUsername=associatedUsername;
  }

  public String getEventID() {
    return eventID;
  }

  public void setEventID(String eventID) {
    this.eventID=eventID;
  }

  public String getPersonID() {
    return personID;
  }

  public void setPersonID(String personID) {
    this.personID=personID;
  }

  public long getLatitude() {
    return latitude;
  }

  public void setLatitude(long latitude) {
    this.latitude=latitude;
  }

  public long getLongitude() {
    return longitude;
  }

  public void setLongitude(long longitude) {
    this.longitude=longitude;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country=country;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city=city;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType=eventType;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year=year;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message=message;
  }
}
