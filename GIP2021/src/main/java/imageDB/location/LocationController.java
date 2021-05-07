package imageDB.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imageDB.image.Image;
import imageDB.image.ImageRep;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class LocationController {

	@Autowired
	private ImageRep imageRep;
	
	@Autowired
	private LocationRep locrep;

	/**
	 * add a location to an image
	 * 
	 * @param URL
	 * @param LocationName
	 */
	@GetMapping("/location/addto/image")
	public void addLocationToImage(@RequestParam String URL, @RequestParam String LocationName) {

		Location location = locrep.findLocationByName(LocationName);
		if (location == null) {
			location = new Location(LocationName);
			locrep.save(location);
		}
		Image img = imageRep.getImageByUrl(URL);
		img.location = location;
		imageRep.save(img);
	}
	
	
	/**
	 * Takes a URL and finds the location connected to the image with set URL
	 * @param URL
	 * @return String with location corresponding to the image
	 */
	@GetMapping("/location/get/from/image")
	private String getLocationFromImage(@RequestParam String URL) {
		System.out.println(URL);
		return imageRep.ImageLocationFinder(URL);
	}
	
	
	
	
}