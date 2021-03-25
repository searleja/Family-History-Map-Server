package JunitTests;

import java.sql.Connection;
import java.util.ArrayList;

import DataAccess.*;
import Models.*;
import Requests.*;
import Results.*;
import Services.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class DAOTests {
  private Database db;
  Connection conn;

  UserDao userDao;
  PersonDao personDao;
  EventDao eventDao;
  AuthTokenDao authTokenDao;

  /**
   * test users
   */
  private User myUser;
  private User myUser2;
  private User myUser3;
  ArrayList<User> myUsers = new ArrayList<>();

  /**
   * test persons
   */
  private Person myPerson;
  private Person myPerson2;
  private Person myPerson3;
  ArrayList<Person> myPersons = new ArrayList<>();

  /**
   * test events
   */
  private Event myEvent;
  private Event myEvent2;
  private Event myEvent3;
  ArrayList<Event> myEvents = new ArrayList<>();

  /**
   * test authTokens
   */
  private AuthToken myAuthToken;
  private AuthToken myAuthToken2;
  private AuthToken myAuthToken3;

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
    eventDao = new EventDao(conn);
    authTokenDao = new AuthTokenDao(conn);
    //db.clearTables();

    myUser = new User("username","password","email","firstName",
            "lastName","M","personID");
    myUser2 = new User("username2","password2","email2","firstName2",
            "lastName2","F","personID2");
    myUser3 = new User("username3","password3","email3","firstName3",
            "lastName3","M","personID3");
    myUsers.add(myUser);
    myUsers.add(myUser2);
    myUsers.add(myUser3);

    myPerson = new Person("personID","username","firstName",
            "lastName","M","fatherID","motherID","spouseID");
    myPerson2 = new Person("personID2","username","firstName2",
            "lastName2","F","fatherID2","motherID2","spouseID2");
    myPerson3 = new Person("personID3","username","firstName3",
            "lastName3","M","fatherID3","motherID3","spouseID3");
    myPersons.add(myPerson);
    myPersons.add(myPerson2);
    myPersons.add(myPerson3);


    myEvent = new Event("eventID", "AssociatedUsername", "personID", 10.01f,
            11.01f, "country", "city", "eventType", 2021);
    myEvent2 = new Event("eventID2", "AssociatedUsername", "personID2", 10.01f,
            11.01f, "country2", "city2", "eventType2", 2021);
    myEvent3 = new Event("eventID3", "AssociatedUsername", "personID3", 10.01f,
            11.01f, "country3", "city3", "eventType3", 2021);
    myEvents.add(myEvent);
    myEvents.add(myEvent2);
    myEvents.add(myEvent3);

    myAuthToken = new AuthToken("authToken", "AssociatedUsername");
    myAuthToken2 = new AuthToken("authToken2", "AssociatedUsername");
    //to be used in person tests
    myAuthToken3 = new AuthToken("authToken3", "username");


  }

  /**
   * To close the connection between test cases.  Prevents the database from locking just in case.
   * committing false rolls back the database, preventing actual change from happening.
   * @throws DataAccessException
   */
  @AfterEach
  public void closeConnection() throws DataAccessException {
    db.clearTables();
    db.closeConnection(true);
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




  @Test
  @DisplayName("Event Insert Testing (pass)")
  public void testInsertEventPass() throws DataAccessException {
    //program will also throw an exception if the insert is invalid.
    eventDao.insert(myEvent);
    eventDao.insert(myEvent2);
    eventDao.insert(myEvent3);

    assertNotNull(eventDao.find(myEvent.getEventID()), "Event wasn't inserted correctly");
    assertEquals(myEvent, eventDao.find(myEvent.getEventID()), "Event information was inserted incorrectly");
  }


  @Test
  @DisplayName("Event Insert Testing (fail)")
  public void testInsertEventFail() throws DataAccessException {
    eventDao.insert(myEvent);
    assertThrows(DataAccessException.class, ()-> eventDao.insert(myEvent), "Inserted invalid Event (inserted twice)");
  }


  @Test
  @DisplayName("Event Find Testing (pass)")
  public void testFindEventPass() throws DataAccessException {
    eventDao.insert(myEvent);
    eventDao.insert(myEvent2);
    eventDao.insert(myEvent3);

    assertNotNull(eventDao.find(myEvent.getEventID()), "Event 1 wasn't found");
    assertEquals(myEvent, eventDao.find(myEvent.getEventID()), "Incorrect Event was found (not 1)");

    assertNotNull(eventDao.find(myEvent2.getEventID()), "Event 2 wasn't found");
    assertEquals(myEvent2, eventDao.find(myEvent2.getEventID()), "Incorrect Event was found (not 2)");

    assertNotNull(eventDao.find(myEvent3.getEventID()), "Event 3 wasn't found");
    assertEquals(myEvent3, eventDao.find(myEvent3.getEventID()), "Incorrect Event was found (not 3)");
  }


  @Test
  @DisplayName("Event Find Testing (fail)")
  public void testFindEventFail() throws DataAccessException {
    eventDao.insert(myEvent);
    eventDao.insert(myEvent2);
    eventDao.insert(myEvent3);

    assertNull(eventDao.find("notEventID"), "Found nonexistent Event");
  }

  @Test
  @DisplayName("Events Find Testing (pass)")
  public void testFindEventsPass() throws DataAccessException {
    eventDao.insert(myEvent);
    eventDao.insert(myEvent2);
    eventDao.insert(myEvent3);

    assertNotNull(eventDao.findEvents(myEvent.getAssociatedUsername()), "Events were not found");
    assertEquals(3, eventDao.findEvents(myEvent.getAssociatedUsername()).size(), "Incorrect Event was found (not 1)");
  }

  @Test
  @DisplayName("Events Find Testing (fail)")
  public void testFindEventsFail() throws DataAccessException {
    eventDao.insert(myEvent);
    eventDao.insert(myEvent2);
    eventDao.insert(myEvent3);

    assertNull(eventDao.findEvents("notAssociatedUsername"), "Found nonexistent events");
  }


  @Test
  @DisplayName("Event Clear Testing")
  public void testClearEvents() throws DataAccessException {
    eventDao.insert(myEvent);
    eventDao.insert(myEvent2);
    eventDao.insert(myEvent3);

    eventDao.clear("AssociatedUsername");
    assertNull(eventDao.find(myEvent.getEventID()), "Failed to clear all Events.");
  }



  @Test
  @DisplayName("AuthToken Insert Testing (pass)")
  public void testInsertAuthTokenPass() throws DataAccessException {
    //program will also throw an exception if the insert is invalid.
    authTokenDao.insert(myAuthToken);
    authTokenDao.insert(myAuthToken2);
    authTokenDao.insert(myAuthToken3);

    assertNotNull(authTokenDao.find(myAuthToken.getAuthToken()), "AuthToken wasn't inserted correctly");
    assertEquals(myAuthToken.getAuthToken(), authTokenDao.find(myAuthToken.getAuthToken()).getAuthToken(),
            "AuthToken information was inserted incorrectly (token)");
    assertEquals(myAuthToken.getAssociatedUsername(), authTokenDao.find(myAuthToken.getAuthToken()).getAssociatedUsername(),
            "AuthToken information was inserted incorrectly (username)");
  }


  @Test
  @DisplayName("AuthToken Insert Testing (fail)")
  public void testInsertAuthTokenFail() throws DataAccessException {
    authTokenDao.insert(myAuthToken);
    assertThrows(DataAccessException.class, ()-> authTokenDao.insert(myAuthToken), "Inserted invalid AuthToken (inserted twice)");
  }


  @Test
  @DisplayName("AuthToken Find Testing (pass)")
  public void testFindAuthTokenPass() throws DataAccessException {
    authTokenDao.insert(myAuthToken);
    authTokenDao.insert(myAuthToken2);
    authTokenDao.insert(myAuthToken3);

    assertEquals(myAuthToken, authTokenDao.find(myAuthToken.getAuthToken()));
    assertEquals(myAuthToken2, authTokenDao.find(myAuthToken2.getAuthToken()));
    assertEquals(myAuthToken3, authTokenDao.find(myAuthToken3.getAuthToken()));
  }


  @Test
  @DisplayName("AuthToken Find Testing (fail)")
  public void testFindAuthTokenFail() throws DataAccessException {
    authTokenDao.insert(myAuthToken);
    authTokenDao.insert(myAuthToken2);
    authTokenDao.insert(myAuthToken3);

    assertNull(authTokenDao.find("notAuthToken"), "Found nonexistent AuthToken");
  }

  @Test
  @DisplayName("AuthToken Username Find Testing (pass)")
  public void testFindAuthTokenUsernamePass() throws DataAccessException {
    authTokenDao.insert(myAuthToken);
    authTokenDao.insert(myAuthToken2);
    authTokenDao.insert(myAuthToken3);

    assertEquals(myAuthToken.getAssociatedUsername(), authTokenDao.findUsername(myAuthToken.getAuthToken()),
            "AuthToken username wasn't found (username 1)");

    assertEquals(myAuthToken2.getAssociatedUsername(), authTokenDao.findUsername(myAuthToken2.getAuthToken()),
            "AuthToken username wasn't found (username 2)");

    assertEquals(myAuthToken3.getAssociatedUsername(), authTokenDao.findUsername(myAuthToken3.getAuthToken()),
            "AuthToken username wasn't found (username 3)");
  }


  @Test
  @DisplayName("AuthToken Username Find Testing (fail)")
  public void testFindAuthTokenUsernameFail() throws DataAccessException {
    authTokenDao.insert(myAuthToken);
    authTokenDao.insert(myAuthToken2);
    authTokenDao.insert(myAuthToken3);

    assertNull(authTokenDao.findUsername("notAuthToken"), "Found nonexistent AuthToken");
  }


  @Test
  @DisplayName("AuthToken Clear Testing")
  public void testClearAuthTokens() throws DataAccessException {
    authTokenDao.insert(myAuthToken);
    authTokenDao.insert(myAuthToken2);
    authTokenDao.insert(myAuthToken3);

    authTokenDao.clear();
    assertNull(authTokenDao.find(myAuthToken.getAuthToken()), "Failed to clear all AuthTokens.");
  }



  //service tests below
  @Test
  @DisplayName("ClearService test (pass)")
  public void testClearService() throws DataAccessException {
    userDao.insert(myUser);
    personDao.insert(myPerson);
    eventDao.insert(myEvent);
    authTokenDao.insert(myAuthToken);

    db.clearTables();
    assertNull(userDao.find(myUser.getUsername()), "Failed to clear all Users.");
    assertNull(personDao.find(myPerson.getPersonID()), "Failed to clear all Persons.");
    assertNull(eventDao.find(myEvent.getEventID()), "Failed to clear all Events.");
    assertNull(authTokenDao.find(myAuthToken.getAuthToken()), "Failed to clear all AuthTokens.");
  }

  @Test
  @DisplayName("EventIDService test (pass)")
  public void testEventIDServicePass() throws DataAccessException {
    eventDao.insert(myEvent);
    authTokenDao.insert(myAuthToken);

    //had to commit changes to database for EventIDService to find the authToken
    db.closeConnection(true);
    setup();

    EventIDRequest request = new EventIDRequest("eventID", "authToken");
    EventIDResult result = new EventIDService().getEvent(request);

    assertNotNull(result.getEventID(), "Didn't find the event");
    assertEquals(myEvent.getEventID(), result.getEventID(), "Found wrong event");
  }

  @Test
  @DisplayName("EventIDService test (fail)")
  public void testEventIDServiceFail() throws DataAccessException {
    eventDao.insert(myEvent);
    authTokenDao.insert(myAuthToken);

    EventIDRequest request = new EventIDRequest("notEventID", "authToken");
    EventIDResult result = new EventIDService().getEvent(request);

    assertNull(result.getEventID(), "Found nonexistent event");
  }

  @Test
  @DisplayName("EventService test (pass)")
  public void testEventServicePass() throws DataAccessException {
    eventDao.insert(myEvent);
    eventDao.insert(myEvent2);
    eventDao.insert(myEvent3);
    authTokenDao.insert(myAuthToken);

    //had to commit changes to database for EventIDService to find the authToken
    db.closeConnection(true);
    setup();

    EventRequest request = new EventRequest("authToken");
    EventResult result = new EventService().getEvents(request);

    for (Event event : result.getData()) {
      assertNotNull(event.getEventID(), "Didn't find the events");
    }
    assertEquals(3, result.getData().size(), "Returned wrong amount of events");
  }

  @Test
  @DisplayName("EventService test (fail)")
  public void testEventServiceFail() throws DataAccessException {
    eventDao.insert(myEvent);
    authTokenDao.insert(myAuthToken);
    EventRequest request = new EventRequest("authToken");
    EventResult result = new EventService().getEvents(request);

    assertNull(result.getData(), "Found nonexistent events");
  }

  @Test
  @DisplayName("FillService test (pass)")
  public void testFillServicePass() throws DataAccessException {
    userDao.insert(myUser);

    //had to commit changes to database for EventIDService to find the authToken
    db.closeConnection(true);

    FillRequest request = new FillRequest("username", 4);
    FillResult result = new FillService().fill(request);

    assertTrue(result.isSuccessful(), "Failed to fill");
    setup(); //to establish a new connection
  }

  @Test
  @DisplayName("FillService test (fail)")
  public void testFillServiceFail() throws DataAccessException {
    userDao.insert(myUser);

    //had to commit changes to database for EventIDService to find the authToken
    db.closeConnection(true);

    FillRequest request = new FillRequest("username", -1);
    FillResult result = new FillService().fill(request);

    assertTrue(result.isSuccessful(), "Accepted invalid input for number of generations.");
    setup(); //to establish a new connection
  }

  @Test
  @DisplayName("LoadService test (pass)")
  public void testLoadServicePass() throws DataAccessException {
    LoadRequest request = new LoadRequest(myUsers, myPersons, myEvents);
    LoadResult result = new LoadService().load(request);

    assertTrue(result.isSuccessful(), "Failed to fill");
    assertEquals(myUser, userDao.find(myUser.getUsername()), "Failed to find user");
    assertEquals(myPerson, personDao.find(myPerson.getPersonID()), "Failed to find person");
    assertEquals(myEvent, eventDao.find(myEvent.getEventID()), "Failed to find event");

    db.closeConnection(true);
    setup(); //to establish a new connection
  }

  @Test
  @DisplayName("LoadService test (fail)")
  public void testLoadServiceFail() throws DataAccessException {
    LoadRequest request = new LoadRequest(myUsers, myPersons, myEvents);
    LoadResult result = new LoadService().load(request);
    //will throw error if not coded correctly
    result = new LoadService().load(request);

    assertTrue(result.isSuccessful(), "Failed to fill");

    request = new LoadRequest(new ArrayList<User>(), new ArrayList<Person>(), new ArrayList<Event>());
    result = new LoadService().load(request);

    assertEquals("Error: missing values", result.getMessage());

  }

  @Test
  @DisplayName("LoginService test (pass)")
  public void testLoginServicePass() throws DataAccessException {
    userDao.insert(myUser);

    //commit changes for authToken to be recognized
    db.closeConnection(true);

    LoginRequest request = new LoginRequest(myUser.getUsername(), myUser.getPassword());
    LoginResult result = new LoginService().login(request);

    assertTrue(result.isSuccessful(), "failed to login user");
    assertEquals(result.getPersonID(), myUser.getPersonID(), "logged in wrong user");

    setup();
  }

  @Test
  @DisplayName("LoginService test (fail)")
  public void testLoginServiceFail() throws DataAccessException {
    userDao.insert(myUser);

    //commit changes for authToken to be recognized
    db.closeConnection(true);

    LoginRequest request = new LoginRequest(myUser2.getUsername(), myUser2.getPassword());
    LoginResult result = new LoginService().login(request);

    assertFalse(result.isSuccessful(), "logged in wrong user");

    request = new LoginRequest("notUsername", "notPassword");
    result = new LoginService().login(request);

    assertNotEquals(result.getPersonID(), myUser.getPersonID(), "logged in nonexistent user");

    setup();
  }

  @Test
  @DisplayName("PersonIDService test (pass)")
  public void testPersonIDServicePass() throws DataAccessException {
    personDao.insert(myPerson);
    authTokenDao.insert(myAuthToken3);

    //had to commit changes to database for PersonIDService to find the authToken
    db.closeConnection(true);
    setup();

    PersonIDRequest request = new PersonIDRequest("personID", "authToken3");
    PersonIDResult result = new PersonIDService().getPerson(request);

    assertNotNull(result.getPersonID(), "Didn't find the Person");
    assertEquals(myEvent.getPersonID(), result.getPersonID(), "Found wrong Person");
  }

  @Test
  @DisplayName("PersonIDService test (fail)")
  public void testPersonIDServiceFail() throws DataAccessException {
    personDao.insert(myPerson);
    authTokenDao.insert(myAuthToken3);

    PersonIDRequest request = new PersonIDRequest("notPersonID", "authToken3");
    PersonIDResult result = new PersonIDService().getPerson(request);

    assertNull(result.getPersonID(), "Found nonexistent Person");
  }

  @Test
  @DisplayName("PersonService test (pass)")
  public void testPersonServicePass() throws DataAccessException {
    personDao.insert(myPerson);
    personDao.insert(myPerson2);
    personDao.insert(myPerson3);
    authTokenDao.insert(myAuthToken3);

    //had to commit changes to database for PersonIDService to find the authToken
    db.closeConnection(true);
    setup();

    PersonRequest request = new PersonRequest("authToken3");
    PersonResult result = new PersonService().getPeople(request);

    for (Person person : result.getData()) {
      assertNotNull(person.getPersonID(), "Didn't find the people");
    }
    assertEquals(3, result.getData().size(), "Returned wrong amount of people");
  }

  @Test
  @DisplayName("PersonService test (fail)")
  public void testPersonServiceFail() throws DataAccessException {
    personDao.insert(myPerson);
    authTokenDao.insert(myAuthToken3);
    PersonRequest request = new PersonRequest("authToken3");
    PersonResult result = new PersonService().getPeople(request);

    assertNull(result.getData(), "Found nonexistent people");
  }

  @Test
  @DisplayName("RegisterService test (pass)")
  public void testRegisterServicePass() throws DataAccessException {
    RegisterRequest request = new RegisterRequest("username", "password", "email",
            "firstName", "lastName", "m");
    RegisterResult result = new RegisterService().register(request);

    assertTrue(result.isSuccessful(), "failed to register user");
    assertNotNull(userDao.find("username"), "failed to insert user into database");
  }

  @Test
  @DisplayName("RegisterService test (fail)")
  public void testRegisterServiceFail() throws DataAccessException {
    RegisterRequest request = new RegisterRequest("username", "password", "email",
            "firstName", "lastName", "m");
    RegisterResult result = new RegisterService().register(request);

    result = new RegisterService().register(request);
    assertEquals("Error: Username already exists.", result.getMessage(), "registered user twice");

  }

}
