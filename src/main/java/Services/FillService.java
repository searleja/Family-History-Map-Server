package Services;

import DataAccess.*;
import JsonHelpers.AllLocations;
import JsonHelpers.Location;
import JsonHelpers.Names;
import Models.Event;
import Models.Person;
import Models.User;
import Requests.FillRequest;
import Results.FillResult;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.util.ArrayList;

import static java.util.UUID.randomUUID;

public class FillService {

  private final int currentYear = 2021;
  private int generations;
  private ArrayList<String> fNames;
  private ArrayList<Location> locations;
  private ArrayList<String> mNames;
  private ArrayList<String> sNames;
  int peopleAdded = 0;
  int eventsAdded = 0;

  //had to create classes for this, found in JsonHelpers package
  private void importData() {
    Gson g = new Gson();
    try {
      fNames = g.fromJson(new FileReader("json/fnames.json"),Names.class).getData();
      locations = g.fromJson(new FileReader("json/locations.json"), AllLocations.class).getData();
      mNames = g.fromJson(new FileReader("json/mnames.json"),Names.class).getData();
      sNames = g.fromJson(new FileReader("json/snames.json"),Names.class).getData();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }


  /**
   * Populates the server's database with generated data for the specified user name. The required "username" parameter
   * must be a user already registered with the server. If there is any data in the database already associated with
   * the given user name, it is deleted. The optional generations parameter lets the caller specify the number of
   * generations of ancestors to be generated, and must be a non-negative integer (the default is 4, which results in
   * 31 new persons each with associated events).
   * @param request
   * @return FillResult
   */
  public FillResult fill(FillRequest request) throws DataAccessException {
    generations = request.getNumGenerations();
    if (generations <= 0) generations = 4;
    importData();
    FillResult result;
    Database db = new Database();
    Connection conn = db.openConnection();

    EventDao eventDao = new EventDao(conn);
    PersonDao personDao = new PersonDao(conn);
    UserDao userDao = new UserDao(conn);

    User myUser = userDao.find(request.getUsername()); //the current user
    Person currentPerson = new Person(myUser.getPersonID(), myUser.getUsername(), myUser.getFirstName(),
            myUser.getLastName(), myUser.getGender(), null, null, null);

    //clear any previous data
    personDao.clear(currentPerson.getUsername());
    eventDao.clear(currentPerson.getUsername());

    if (!myUser.getUsername().equals(request.getUsername())) {
      result = new FillResult("Error: invalid username");
      db.closeConnection(false);
      return result;
    }

    try {
      generateBirth(eventDao, currentPerson, generations);
      generateLife(personDao, eventDao, currentPerson, generations);
    }
    catch (DataAccessException e) {
      result = new FillResult("Error: Internal server error");
      e.printStackTrace();
      db.closeConnection(false);
      return result;
    }

    result = new FillResult("Successfully added " + peopleAdded + " persons and " + eventsAdded + " events to the database.");
    result.setSuccess(true);
    db.closeConnection(true);

    return result;
  }

  /**
   * Recursive method to go through generations and generate events for each person (spouses assigned as well)
   * @param personDao
   * @param eventDao
   * @param child
   * @param generationCounter
   * @throws DataAccessException
   */
  private void generateLife(PersonDao personDao, EventDao eventDao, Person child, int generationCounter) throws DataAccessException {
    //generating parents if not the last generation
    if (generationCounter > 0) {
      Person father = generateFather(child.getUsername());
      Person mother = generateMother(child.getUsername());

      father.setSpouseID(mother.getPersonID());
      mother.setSpouseID(father.getPersonID());

      generationCounter--;

      //set marriagePlace here to make it equal for both parents
      Location marriagePlace = locations.get((int)(Math.random() * (locations.size())));

      //events for father
      int birthYear = generateBirth(eventDao, father, generationCounter);
      int marriageYear =  generateMarriage(birthYear, 0, eventDao, father, marriagePlace);
      generateDeath(birthYear, eventDao, father);

      //events for mother
      generateBirth(eventDao, mother, generationCounter);
      generateMarriage(birthYear, marriageYear, eventDao, mother, marriagePlace);
      generateDeath(birthYear, eventDao, mother);


      generateLife(personDao, eventDao, father, generationCounter);
      generateLife(personDao, eventDao, mother, generationCounter);

      child.setFatherID(father.getPersonID());
      child.setMotherID(mother.getPersonID());
    }
    personDao.insert(child);
    peopleAdded++;
  }

  //generate father and mother

  private Person generateFather(String username) {
    String firstName = mNames.get((int)(Math.random() * (mNames.size())));
    String lastName = sNames.get((int)(Math.random() * (sNames.size())));

    String personID = randomUUID().toString().substring(0,8);

    return new Person(personID, username, firstName, lastName, "m", null, null, null);
  }

  private Person generateMother(String username) {
    String firstName = fNames.get((int)(Math.random() * (fNames.size())));
    String lastName = sNames.get((int)(Math.random() * (sNames.size())));

    String personID = randomUUID().toString().substring(0,8);

    return new Person(personID, username, firstName, lastName, "m", null, null, null);
  }


  //events to be generated

  private int generateBirth(EventDao eventDao, Person currentPerson, int generationCounter) throws DataAccessException {
    //birth
    int birthYear = (int)(Math.random() * (30-28 + 1) + 28); //age ranged from 28-35 when having a child.
    birthYear = currentYear - (birthYear * (4 - generationCounter+1));

    String eventID = randomUUID().toString().substring(0,8);

    Location birthPlace = locations.get((int)(Math.random() * (locations.size())));
    Event birth = new Event(eventID, currentPerson.getUsername(), currentPerson.getPersonID(), birthPlace.getLatitude(),
            birthPlace.getLongitude(), birthPlace.getCountry(), birthPlace.getCity(), "Birth",
            birthYear);
    eventDao.insert(birth);
    eventsAdded++;
    return birthYear;
  }

  private int generateMarriage(int birthYear, int marriageYear, EventDao eventDao, Person currentPerson, Location marriagePlace) throws DataAccessException {
    //marriage
    //year doesn't necessarily match with the spouse - need to fix if the tests are that picky
    if (marriageYear == 0) { //if generating for the father
      marriageYear=birthYear + (int) ((Math.random() * (27 - 24 + 1)) + 24);
    }

    String eventID = randomUUID().toString().substring(0,8);

    Event marriage = new Event(eventID, currentPerson.getUsername(), currentPerson.getPersonID(), marriagePlace.getLatitude(),
            marriagePlace.getLongitude(), marriagePlace.getCountry(), marriagePlace.getCity(), "Marriage",
            marriageYear);
    eventDao.insert(marriage);
    eventsAdded++;
    return marriageYear;
  }

  private void generateDeath(int birthYear, EventDao eventDao, Person currentPerson) throws DataAccessException {
    //death
    //will die in current year if the randomly generated year is in the future.
    int deathYear = birthYear + ((int)(Math.random() * (80-50+1))+50);
    if (deathYear > currentYear) { deathYear = currentYear; }

    String eventID = randomUUID().toString().substring(0,8);

    Location deathPlace=locations.get((int) (Math.random() * (locations.size())));
    Event death=new Event(eventID, currentPerson.getUsername(), currentPerson.getPersonID(), deathPlace.getLatitude(),
            deathPlace.getLongitude(), deathPlace.getCountry(), deathPlace.getCity(), "Death",
            deathYear);
    eventDao.insert(death);
    eventsAdded++;
  }

  //Would be nice to have one method for all of it but I needed to have common marriage values.
  /*private void generateEvents(EventDao eventDao, Person currentPerson, int generationCounter, Location marriagePlace) throws DataAccessException {

    int birthYear = generateBirth(eventDao, currentPerson, generationCounter);

    int marriageyear = generateMarriage(birthYear, eventDao, currentPerson, marriagePlace);

    //death
    //will die in current year if the randomly generated year is in the future.
    int deathYear = birthYear + ((int)(Math.random() * (80-50+1))+50);
    if (deathYear > currentYear) { deathYear = currentYear; }

    eventID = randomUUID().toString();
    eventID = eventID.substring(0,8);
    Location deathPlace=locations.get((int) (Math.random() * (locations.size())));
    Event death=new Event(eventID, currentPerson.getUsername(), currentPerson.getPersonID(), deathPlace.getLatitude(),
            deathPlace.getLongitude(), deathPlace.getCountry(), deathPlace.getCity(), "Death",
            deathYear);
    eventDao.insert(death);
    eventsAdded++;


  }

   */
}
