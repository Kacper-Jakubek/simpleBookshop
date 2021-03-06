package pl.sdacademy.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BookstoreApp {

  public static void main(String[] args) {
    SpringApplication.run(BookstoreApp.class, args);
  }
}
