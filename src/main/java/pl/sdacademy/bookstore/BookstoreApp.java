package pl.sdacademy.bookstore;

import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.sdacademy.bookstore.Validators.EmailValidator;

@SpringBootApplication
public class BookstoreApp {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApp.class, args);

	}
}