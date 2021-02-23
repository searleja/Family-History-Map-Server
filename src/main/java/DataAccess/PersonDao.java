package DataAccess;

import Models.Person;

import java.util.ArrayList;

public class PersonDao {

  /**
   * Inserts the Person into the database
   * @param person
   */
  public void insert(Person person) {

  }

  /**
   * Searches for the person associated with the personID
   * @param personID
   * @return Person object
   */
  public Person find(String personID) {
    return null;
  }

  /**
   * Searches for all ancestors of the given user
   * @param associatedUsername
   * @return ArrayList of ancestors
   */
  public ArrayList<Person> findPeople(String associatedUsername) {
    return null;
  }

  /**
   * deletes data from the database
   */
  public void delete() {

  }
}
