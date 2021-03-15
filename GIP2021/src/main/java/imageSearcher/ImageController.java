package imageSearcher;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ImageController {
	
	@Autowired
	private ImageRepository imageRep;
	
	@GetMapping(path="/all")
	public Iterable<Image> index() {
		return imageRep.findAll();
	}
	
	@GetMapping(path = "/all/URL")
	public Iterable<String> AllUrl() {
		return imageRep.findAllURL();
	}
	
	@GetMapping(path ="/search/URL")
	public String searchURL(@RequestParam String Url){
		return imageRep.findByUrl(Url);
	}

}
