package imageDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imageDB.authors.Author;
import imageDB.authors.AuthorRep;
import imageDB.image.Image;
import imageDB.image.ImageRep;
import imageDB.location.Location;
import imageDB.location.LocationRep;
import imageDB.tags.Tag;
import imageDB.tags.TagRep;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AddController {

	// Adding image repository
	@Autowired
	private ImageRep imageRep;

	// Adding author repository
	@Autowired
	private AuthorRep authrep;

	// Adding location repository
	@Autowired
	private LocationRep locrep;

	// Adding tag repository
	@Autowired
	TagRep tagrep;

	// Adding tags
	/**
	 * add a new tag to a given image
	 * 
	 * @param String the name of the new tag
	 * @param String the url of the image to add the tag to
	 * @return String "done" if completed
	 */
	@GetMapping("/add/tags")
	public String addTags(@RequestParam String add, @RequestParam String URL) {
		Tag tag = tagrep.findTagByName(add);
		if (tag == null) {
			tag = new Tag(add);
		}
		Image img = imageRep.getImageByUrl(URL);
		List<Tag> testlist = new ArrayList<Tag>();
		testlist.addAll(img.getTags());
		testlist.add(tag);
		for (Tag a : img.getTags()) {
			System.out.println(a.tagName);
		}
		img.setTags(testlist);
		imageRep.save(img);
		return "done";
	}

	// Adding authors
	/**
	 * create a new author
	 * 
	 * @param AuthName the name of the new author
	 * @return returns the newly created author
	 */
	@GetMapping("/add/author")
	public Author createAuthor(@RequestParam String AuthName) {
		Author author = new Author(AuthName);
		authrep.save(author);
		return author;
	}

	/**
	 * add an author to an image
	 * 
	 * @param URL        of the image to add the author to
	 * @param authorName the name of the author to add
	 */
	@GetMapping("/add/AuthorToImage")
	public void addAuthorToImage(@RequestParam String URL, @RequestParam String authorName) {

		Author auth = authrep.findAuthByName(authorName);
		if (auth == null) {
			auth = createAuthor(authorName);
		}
		Image img = imageRep.getImageByUrl(URL);
		img.author = auth;
		imageRep.save(img);
	}

	/**
	 * add a location to an image
	 * 
	 * @param URL
	 * @param LocationName
	 */
	@GetMapping("/add/locationToImage")
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

}
