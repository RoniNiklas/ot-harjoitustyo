package harjoitustyo;

import harjoitustyo.presentation.Ui;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author Roni
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
public class Main {
    public static void main(String[] args) {
        //SpringApplication.run(Main.class, args);
        Ui ui = new Ui();
        ui.main(args);
    }
}
