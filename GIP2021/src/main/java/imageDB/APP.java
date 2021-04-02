package imageDB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"imageDB.authors", "imageDB.image", "imageDB.tags", "imageDB"})
public class APP {
	
	public static void main(String[] args) {
		SpringApplication.run(APP.class, args);
	}
	
}
