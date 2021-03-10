package Services;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Results.ClearResult;


public class ClearService {

  /**
   * Deletes ALL data from the database, including user accounts, auth tokens, and generated person and event data.
   * @return ClearResult
   */
  public ClearResult clear() throws DataAccessException {
    Database db = new Database();
    db.openConnection();
    ClearResult result;
    try {
      db.clearTables();
      db.closeConnection(true);
      result = new ClearResult("Clear succeeded.");
      result.setSuccess(true);
      return result;
    }
    catch (DataAccessException e) {
      result = new ClearResult("error");
      db.closeConnection(false);
      return result;
    }
  }
}
