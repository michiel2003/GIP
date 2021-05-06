package imageDB.authors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imageDB.IconCreator.IconRep;
import imageDB.filePaths.PathRepository;
import imageDB.image.Image;
import imageDB.image.ImageRep;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AuthorController {

	// Adding image repository
	@Autowired
	private ImageRep imageRep;

	@Autowired
	public IconRep icrep;
	
	@Autowired
	public PathRepository pathrep;
	
	@Autowired
	private AuthorRep authorRepository;
	
	/**
	 * gets all authors and their info
	 * 
	 * @return List within a List containing all the author info
	 */
	@GetMapping("/authors/all")
	public List<List<String>> allAuthor() {
		List<List<String>> authorList = new ArrayList<List<String>>();
		List<Author> authL = new ArrayList<Author>();
		authL.addAll(authorRepository.allAuthors());
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
	 * searches the author beloning to a specific image url
	 * 
	 * @param Image URL to search for author
	 * @return String with author name
	 */
	@GetMapping("/author/search/image")
	public String getAuthorByURL(@RequestParam String URL) {
		return authorRepository.getAuthorOnImageURL(URL);
	}
	
	/**
	 * create a new author
	 * 
	 * @param AuthName the name of the new author
	 * @return returns the newly created author
	 */
	@GetMapping("/author/create")
	public Author createAuthor(@RequestParam String AuthName) {
		Author author = new Author(AuthName);
		authorRepository.save(author);
		return author;
	}
	
	/**
	 * add an author to an image
	 * 
	 * @param URL        of the image to add the author to
	 * @param authorName the name of the author to add
	 */
	@GetMapping("/author/add/image")
	public void addAuthorToImage(@RequestParam String URL, @RequestParam String authorName) {

		Author auth = authorRepository.findAuthByName(authorName);
		if (auth == null) {
			auth = createAuthor(authorName);
		}
		Image img = imageRep.getImageByUrl(URL);
		img.author = auth;
		imageRep.save(img);
	}
	
	
	
	
	
}