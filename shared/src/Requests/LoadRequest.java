package Requests;

import Models.Event;
import Models.Person;
import Models.User;

import java.util.ArrayList;

public class LoadRequest {
  private ArrayList<User> users;
  private ArrayList<Person> persons;
  private ArrayList<Event> events;

  /**
   * Constructor for LoadRequest. Stores posted user, person, and event data.
   * @param users
   * @param persons
   * @param events
   */
  public LoadRequest(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) {
    this.users=users;
    this.persons=persons;
    this.events=events;
  }

  public ArrayList<User> getUsers() {
    return users;
  }

  public void setUsers(ArrayList<User> users) {
    this.users=users;
  }

  public ArrayList<Person> getPersons() {
    return persons;
  }

  public void setPersons(ArrayList<Person> persons) {
    this.persons=persons;
  }

  public ArrayList<Event> getEvents() {
    return events;
  }

  public void setEvents(ArrayList<Event> events) {
    this.events=events;
  }
}
