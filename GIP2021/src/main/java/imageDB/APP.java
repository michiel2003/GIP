package imageDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import imageDB.IconCreator.IconCreator;

@SpringBootApplication(scanBasePackages = {"imageDB.authors", "imageDB.image", "imageDB.tags", "imageDB", "imageDB.controllers", "imageDB.IconCreator"})
public class APP {
	
	public static void main(String[] args) {
		SpringApplication.run(APP.class, args);
	}
	
}
