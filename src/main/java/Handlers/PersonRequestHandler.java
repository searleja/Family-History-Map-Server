package Handlers;

import DataAccess.DataAccessException;
import Requests.PersonRequest;
import Results.PersonResult;
import Services.PersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class PersonRequestHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    try {
      if (exchange.getRequestMethod().equals("GET")) {

        Headers myHeaders = exchange.getRequestHeaders();
        String token = myHeaders.getFirst("Authorization");
        Gson g = new Gson();

        PersonRequest request = new PersonRequest(token);
        PersonResult result = new PersonService().getPeople(request);

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
