package Handlers;

import DataAccess.DataAccessException;
import Requests.PersonIDRequest;
import Results.PersonIDResult;
import Services.PersonIDService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class PersonIDRequestHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    try {
      if (exchange.getRequestMethod().equals("GET")) {

        Headers myHeaders = exchange.getRequestHeaders();
        String token = myHeaders.getFirst("Authorization");
        Gson g = new Gson();

        String pathData = exchange.getRequestURI().getPath();
        String dataArray[] = pathData.split("/"); //will divide the username and number of generations
        PersonIDRequest request;

        request = new PersonIDRequest(dataArray[2], token);
        PersonIDResult result = new PersonIDService().getPerson(request);

        if (result.isSuccessful()) {
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
