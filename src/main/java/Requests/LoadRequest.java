package Requests;

import Models.Event;
import Models.Person;
import Models.User;

public class LoadRequest {
  private User[] users;
  private Person[] persons;
  private Event[] events;

  public LoadRequest(User[] users, Person[] persons, Event[] events) {
    this.users=users;
    this.persons=persons;
    this.events=events;
  }

  public boolean isValid() {
    return false;
  }

  public User[] getUsers() {
    return users;
  }

  public void setUsers(User[] users) {
    this.users=users;
  }

  public Person[] getPersons() {
    return persons;
  }

  public void setPersons(Person[] persons) {
    this.persons=persons;
  }

  public Event[] getEvents() {
    return events;
  }

  public void setEvents(Event[] events) {
    this.events=events;
  }
}
