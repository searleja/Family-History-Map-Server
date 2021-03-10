package JunitTests;

import java.sql.Connection;
import DataAccess.*;
import Models.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class DAOTests {
  private Database db;
  Connection conn;

  UserDao userDao;
  PersonDao personDao;

  /**
   * test users
   */
  private User myUser;
  private User myUser2;
  private User myUser3;

  /**
   * test persons
   */
  private Person myPerson;
  private Person myPerson2;
  private Person myPerson3;

  /**
   * opens the database connection before each test case
   * @throws DataAccessException
   */
  @BeforeEach
  @DisplayName("Set up DAO objects")
  public void setup() throws DataAccessException {
    db = new Database();
    conn = db.openConnection();
    userDao = new UserDao(conn);
    personDao = new PersonDao(conn);
    db.clearTables();

    myUser = new User("username","password","email","firstName",
            "lastName","M","personID");
    myUser2 = new User("username2","password2","email2","firstName2",
            "lastName2","F","personID2");
    myUser3 = new User("username3","password3","email3","firstName3",
            "lastName3","M","personID3");

    myPerson = new Person("personID","username","firstName",
            "lastName","M","fatherID","motherID","spouseID");
    myPerson2 = new Person("personID2","username2","firstName2",
            "lastName2","F","fatherID2","motherID2","spouseID2");
    myPerson3 = new Person("personID3","username3","firstName3",
            "lastName3","M","fatherID3","motherID3","spouseID3");
  }

  /**
   * To close the connection between test cases.  Prevents the database from locking just in case.
   * committing false rolls back the database, preventing actual change from happening.
   * @throws DataAccessException
   */
  @AfterEach
  public void closeConnection() throws DataAccessException {
    db.closeConnection(false);
  }


  @Test
  @DisplayName("User Insert Testing (pass)")
  public void testInsertUserPass() throws DataAccessException {
    //program will also throw an exception if the insert is invalid.
    userDao.insert(myUser);
    userDao.insert(myUser2);
    userDao.insert(myUser3);

    assertNotNull(userDao.find(myUser.getUsername()), "User wasn't inserted correctly");
    assertEquals(myUser, userDao.find(myUser.getUsername()), "User information was inserted incorrectly");
  }


  @Test
  @DisplayName("User Insert Testing (fail)")
  public void testInsertUserFail() throws DataAccessException {
    userDao.insert(myUser);
    assertThrows(DataAccessException.class, ()-> userDao.insert(myUser), "Inserted invalid user (inserted twice)");
  }


  @Test
  @DisplayName("User Find Testing (pass)")
  public void testFindUserPass() throws DataAccessException {
    userDao.insert(myUser);
    userDao.insert(myUser2);
    userDao.insert(myUser3);

    assertNotNull(userDao.find(myUser.getUsername()), "User 1 wasn't found");
    assertEquals(myUser, userDao.find(myUser.getUsername()), "Incorrect user was found (not 1)");

    assertNotNull(userDao.find(myUser2.getUsername()), "User 2 wasn't found");
    assertEquals(myUser2, userDao.find(myUser2.getUsername()), "Incorrect user was found (not 2)");

    assertNotNull(userDao.find(myUser3.getUsername()), "User 3 wasn't found");
    assertEquals(myUser3, userDao.find(myUser3.getUsername()), "Incorrect user was found (not 3)");
  }


  @Test
  @DisplayName("User Find Testing (fail)")
  public void testFindUserFail() throws DataAccessException {
    userDao.insert(myUser);
    userDao.insert(myUser2);
    userDao.insert(myUser3);

    assertNull(userDao.find("notUsername"), "Found nonexistent User");
  }


  @Test
  @DisplayName("User Clear Testing")
  public void testClearUsers() throws DataAccessException {
    userDao.insert(myUser);
    userDao.insert(myUser2);
    userDao.insert(myUser3);

    userDao.clear();
    assertNull(userDao.find(myUser.getUsername()), "Failed to clear all Users.");
  }


  @Test
  @DisplayName("Person Insert Testing (pass)")
  public void testInsertPersonPass() throws DataAccessException {
    //program will also throw an exception if the insert is invalid.
    personDao.insert(myPerson);
    personDao.insert(myPerson2);
    personDao.insert(myPerson3);

    assertNotNull(personDao.find(myPerson.getPersonID()), "Person wasn't inserted correctly");
    assertEquals(myPerson, personDao.find(myPerson.getPersonID()), "Person information was inserted incorrectly");
  }


  @Test
  @DisplayName("Person Insert Testing (fail)")
  public void testInsertPersonFail() throws DataAccessException {
    personDao.insert(myPerson);
    assertThrows(DataAccessException.class, ()-> personDao.insert(myPerson), "Inserted invalid Person (inserted twice)");
  }


  @Test
  @DisplayName("Person Find Testing (pass)")
  public void testFindPersonPass() throws DataAccessException {
    personDao.insert(myPerson);
    personDao.insert(myPerson2);
    personDao.insert(myPerson3);

    assertNotNull(personDao.find(myPerson.getPersonID()), "Person wasn't found");
    assertEquals(myPerson, personDao.find(myPerson.getPersonID()), "Incorrect Person was found (not 1)");

    assertNotNull(personDao.find(myPerson2.getPersonID()), "Person wasn't found");
    assertEquals(myPerson2, personDao.find(myPerson2.getPersonID()), "Incorrect Person was found (not 2)");

    assertNotNull(personDao.find(myPerson3.getPersonID()), "Person wasn't found");
    assertEquals(myPerson3, personDao.find(myPerson3.getPersonID()), "Incorrect Person was found (not 3)");
  }


  @Test
  @DisplayName("Person Find Testing (fail)")
  public void testFindPersonFail() throws DataAccessException {
    personDao.insert(myPerson);
    personDao.insert(myPerson2);
    personDao.insert(myPerson3);

    assertNull(personDao.find("notPersonID"), "Found nonexistent person");
  }


  @Test
  @DisplayName("Person Clear Testing")
  public void testClearPersons() throws DataAccessException {
    personDao.insert(myPerson);
    personDao.insert(myPerson2);
    personDao.insert(myPerson3);

    personDao.clear(myPerson.getUsername());
    personDao.clear(myPerson2.getUsername());
    personDao.clear(myPerson3.getUsername());
    assertNull(personDao.find(myPerson.getPersonID()), "Failed to clear all Persons");
  }
}
