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
	
	//Get all images
		@GetMapping(path="/get/All")
		public Iterable<Image> index() {
			return imageRep.findAll();
		}

	// Retrieving tags from a specific image
	@GetMapping("/get/tags")
	public Iterable<String> gettags(@RequestParam String URL) {
		List<List<String>> a = imageRep.getTags(URL);
		List<String> b = new ArrayList<String>();
		for (List<String> c : a) {
			b.add(c.get(5));
		}
		return b;
	}

	// Get all image URLS
	@GetMapping(path = "/get/URL")
	public Iterable<String> AllUrl() {
		return icrep.getAllIcons();
	}

	// Get all images with a specific tag
	@GetMapping("/get/bytag")
	public List<String> sTag(@RequestParam String s) {
		return imageRep.ImageTagSearch(s);
	}

	@GetMapping("/get/authorByImageURL")
	public String getAuthorByURL(@RequestParam String URL) {
		return authrep.getAuthorOnImageURL(URL);
	}

	@GetMapping("/get/byAuthor")
	public List<String> sAuthor(@RequestParam String s) {
		return imageRep.ImageAuthorSearch(s);
	}
	
	@GetMapping("/all/Authors")
	public List<List<String>> allAuthor(){
		List<List<String>> authorList = new ArrayList<List<String>>();  
		List<Author> authL = new ArrayList<Author>();
		authL.addAll(authrep.allAuthors());
		for(Author auth: authL) {
			List<String> toAdd = new ArrayList<String>();
			toAdd.add(auth.authorName);
			toAdd.add(auth.phone);
			toAdd.add(auth.email);
			toAdd.add(auth.lastName);
			authorList.add(toAdd);
		}
		return authorList;
	}
	
	@GetMapping("/icons/getIconURLS")
	public List<String> getIconURLS(){
		return icrep.getAllIcons();
	}

}
