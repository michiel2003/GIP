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
import imageDB.tags.Tag;
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

	// Adding tags
	@GetMapping("/add/tags")
	public String addTags(@RequestParam String add, @RequestParam String URL) {
		Image img = imageRep.getImageByUrl(URL);
		List<Tag> testlist = new ArrayList<Tag>();
		testlist.addAll(img.getTags());
		testlist.add(new Tag(add));
		for (Tag a : img.getTags()) {
			System.out.println(a.tagName);
		}
		img.setTags(testlist);
		imageRep.save(img);
		return "done";
	}

	// Adding authors
	@GetMapping("/add/author")
	public void createAuthor(@RequestParam String AuthName) {
		Author author = new Author(AuthName);
		authrep.save(author);
	}

	@GetMapping("/add/authtoimg")
	public void addAuthorToImage(@RequestParam String URL, @RequestParam String authorName) {

		Author auth = authrep.findAuthByName(authorName);
		if (auth == null) {
			auth = new Author(authorName);
			authrep.save(auth);
		}
		Image img = imageRep.getImageByUrl(URL);
		img.author = auth;
		imageRep.save(img);
	}

}
