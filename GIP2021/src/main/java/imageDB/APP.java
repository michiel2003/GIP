package imageDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"imageDB.authors", "imageDB.image", "imageDB.tags", "imageDB", "imageDB.controllers", "imageDB.IconCreator"})
public class APP {
	
	/**
	 * run this class to start the application
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(APP.class, args);
	}
	
}
