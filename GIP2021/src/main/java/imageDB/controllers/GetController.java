package imageDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imageDB.IconCreator.IconRep;
import imageDB.authors.Author;
import imageDB.authors.AuthorRep;
import imageDB.image.Image;
import imageDB.image.ImageRep;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class GetController {

	// Adding image repository
	@Autowired
	private ImageRep imageRep;

	// Adding author repository
	@Autowired
	private AuthorRep authrep;

	@Autowired
	public IconRep icrep;

	// Retrieving tags from a specific image
	/**
	 * Get the tag names from a specific image
	 * 
	 * @param The URL from which
	 * @return Iterable tag names
	 */
	@GetMapping("/get/tags")
	public Iterable<String> gettags(@RequestParam String URL) {
		List<List<String>> a = imageRep.getTags(URL);
		List<String> b = new ArrayList<String>();
		for (List<String> c : a) {
			b.add(c.get(5));
		}
		return b;
	}

	/**
	 * Used to get all the url's in the databse for the icons
	 * 
	 * @return Iterable with all the urls
	 */
	@GetMapping(path = "/get/URL")
	public Iterable<String> AllUrl() {
		return icrep.getAllIcons();
	}

	/**
	 * Used to search image by tag
	 * 
	 * @param s = search term
	 * @return List with all images corresponding
	 */
	@GetMapping("/get/bytag")
	public List<String> sTag(@RequestParam String s) {
		return imageRep.ImageTagSearch(s);
	}

	/**
	 * searches the author beloning to a specific image url
	 * 
	 * @param Image URL to search for author
	 * @return String with author name
	 */
	@GetMapping("/get/authorByImageURL")
	public String getAuthorByURL(@RequestParam String URL) {
		return authrep.getAuthorOnImageURL(URL);
	}

	/**
	 * Searches to all images form a specific author
	 * 
	 * @param Author to search for images
	 * @return List with all images returned
	 */
	@GetMapping("/get/byAuthor")
	public List<String> sAuthor(@RequestParam String s) {
		return imageRep.ImageAuthorSearch(s);
	}

	/**
	 * gets all authors and their info
	 * 
	 * @return List within a List containing all the author info
	 */
	@GetMapping("/all/Authors")
	public List<List<String>> allAuthor() {
		List<List<String>> authorList = new ArrayList<List<String>>();
		List<Author> authL = new ArrayList<Author>();
		authL.addAll(authrep.allAuthors());
		for (Author auth : authL) {
			List<String> toAdd = new ArrayList<String>();
			toAdd.add(auth.authorName);
			toAdd.add(auth.phone);
			toAdd.add(auth.email);
			toAdd.add(auth.lastName);
			authorList.add(toAdd);
		}
		return authorList;
	}

	/**
	 * get all icon URLS from database
	 * 
	 * @return List of all the icon URLS
	 */
	@GetMapping("/icons/getIconURLS")
	public List<String> getIconURLS() {
		return icrep.getAllIcons();
	}

	/**
	 * get the full scale image instead of the icon
	 * 
	 * @param URL the url of the image
	 * @return String the indepth image
	 */
	@GetMapping("/get/indepth")
	public String getIndepthFullScaleImage(@RequestParam String url) {
		return icrep.IndepthImageFinder(url);
	}

}
