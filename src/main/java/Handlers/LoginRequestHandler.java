package Handlers;

import DataAccess.DataAccessException;
import Requests.LoginRequest;
import Requests.RegisterRequest;
import Results.LoginResult;
import Results.RegisterResult;
import Services.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class LoginRequestHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    System.out.println("working");
    try {
      if (exchange.getRequestMethod().equals("POST")) {

        Gson g = new Gson();
        String data = readString(exchange.getRequestBody());
        LoginRequest request = g.fromJson(data, LoginRequest.class);
        LoginResult result = new LoginService().login(request);

        if (result.isSuccessful()) {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
          System.out.println("ok");
        }
        else {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        }
        //write out result
        OutputStream os = exchange.getResponseBody();
        String returnOutput = g.toJson(result);
        writeString(returnOutput, os);
        System.out.println(returnOutput);
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

  private String readString(InputStream is) throws IOException { //from video lecture example
    StringBuilder sb = new StringBuilder();
    InputStreamReader sr = new InputStreamReader(is);
    char[] buf = new char[1024];
    int len;
    while ((len = sr.read(buf)) > 0) {
      sb.append(buf,0,len);
    }
    return sb.toString();
  }
}
