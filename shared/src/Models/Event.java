package Models;

public class Event {
  private String eventID;
  private String associatedUsername;
  private String personID;
  private float latitude;
  private float longitude;
  private String country;
  private String city;
  private String eventType;
  private int year;

  /**
   * Constructor for the Event class. Stores data about the person, type, location, and year of the event.
   * @param eventID
   * @param associatedUsername
   * @param personID
   * @param latitude
   * @param longitude
   * @param country
   * @param city
   * @param eventType
   * @param year
   */
  public Event(String eventID, String associatedUsername, String personID, float latitude, float longitude,
               String country, String city, String eventType, int year) {
    this.eventID=eventID;
    this.associatedUsername=associatedUsername;
    this.personID=personID;
    this.latitude=latitude;
    this.longitude=longitude;
    this.country=country;
    this.city=city;
    this.eventType=eventType;
    this.year=year;
  }

  public String getEventID() {
    return eventID;
  }

  public void setEventID(String eventID) {
    this.eventID=eventID;
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

  public float getLatitude() {
    return latitude;
  }

  public void setLatitude(float latitude) {
    this.latitude=latitude;
  }

  public float getLongitude() {
    return longitude;
  }

  public void setLongitude(float longitude) {
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

  @Override
  public boolean equals(Object o) {
    if (o == null)
      return false;
    if (o instanceof Event) {
      Event oEvent = (Event) o;
      return oEvent.getEventID().equals(getEventID()) &&
              oEvent.getAssociatedUsername().equals(getAssociatedUsername()) &&
              oEvent.getPersonID().equals(getPersonID()) &&
              oEvent.getLatitude() == (getLatitude()) &&
              oEvent.getLongitude() == (getLongitude()) &&
              oEvent.getCountry().equals(getCountry()) &&
              oEvent.getCity().equals(getCity()) &&
              oEvent.getEventType().equals(getEventType()) &&
              oEvent.getYear() == (getYear());
    } else {
      return false;
    }
  }
}
