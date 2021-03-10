package Handlers;

import DataAccess.DataAccessException;
import Results.ClearResult;
import Services.ClearService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class ClearRequestHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    try {
    if (exchange.getRequestMethod().equals("POST")) {

      Gson g = new Gson();
      ClearResult result = new ClearService().clear();

      if (result.isSuccess()) {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
      }
      else {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
      }

      OutputStream os = exchange.getResponseBody();
      String returnOutput = g.toJson(result);
      writeString(returnOutput, os);
    }
    else {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
    }

      exchange.getResponseBody().close();

    } catch (DataAccessException e) {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
      exchange.getResponseBody().close();
      e.printStackTrace();
    }
  }

  private void writeString(String str, OutputStream os) throws IOException { //from video lecture example
    OutputStreamWriter sw = new OutputStreamWriter(os);
    BufferedWriter bw = new BufferedWriter(sw);
    bw.write(str);
    bw.flush();
  }
}
