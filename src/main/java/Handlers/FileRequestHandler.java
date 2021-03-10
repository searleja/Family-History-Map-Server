package Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileRequestHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange exchange) throws IOException {

    try {
      //filePath to file code is from lectures, stackoverflow to figure out how to make it work
      //(Pretty sure path stuff wasn't in lecture notes)
      String urlPath=exchange.getRequestURI().toString();
      Path filePath = FileSystems.getDefault().getPath("web" + urlPath);
      File file = new File(filePath.toString());

      //rest is from lecture notes
      if (file.isFile()) { //if it was already a valid input
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        sendFilePath(exchange, filePath);
      } else if (Files.isDirectory(filePath)) { //default is to the test page
        filePath = FileSystems.getDefault().getPath("web" + urlPath + "index.html");
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        sendFilePath(exchange, filePath);

      } else { //not found
        filePath = FileSystems.getDefault().getPath("web/HTML/404.html");
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
        sendFilePath(exchange, filePath);

      }
    }
    catch (IOException e) { //won't let me do DataAccessException
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
      exchange.getResponseBody().close();
      e.printStackTrace();
    }
  }

  public void sendFilePath(HttpExchange exchange, Path filePath) throws IOException {
    Files.copy(filePath, exchange.getResponseBody());
    exchange.getResponseBody().close();
  }


  //use these in other handlers - from lecture notes

  /*
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
  */

}
