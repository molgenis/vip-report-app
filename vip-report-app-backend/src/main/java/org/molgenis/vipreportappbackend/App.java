package org.molgenis.vipreportappbackend;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class App {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @EventListener({ApplicationReadyEvent.class})
  void applicationReadyEvent() {
    System.out.println("Application started ... launching browser now");
    // browse("http://localhost:8080/");
  }

  public static void browse(String url) {
    Runtime runtime = Runtime.getRuntime();
    try {
      runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
