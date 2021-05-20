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
import java.util.Arrays;
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
			toAdd.add(auth.getAuthorName());
			toAdd.add(auth.getPhone());
			toAdd.add(auth.getEmail());
			toAdd.add(auth.getLastName());
			toAdd.add(auth.getAuthorId() + "");
			authorList.add(toAdd);
		}
		return authorList;
	}

	/**
	 * searches the author belonging to a specific image url
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
	public String addAuthorToImage(@RequestParam String URL, @RequestParam String authorName) {

		Author auth = authorRepository.findAuthByName(authorName);
		if (auth == null) {
			System.out.println("ddd");
			return authorName;
		}
		Image img = imageRep.getImageByUrl(URL);
		img.author = auth;
		imageRep.save(img);
		return "OK";
	}

	/**
	 * used to save a new author
	 * @param authorName
	 * @param lastName
	 * @param phone
	 * @param email
	 * @param id
	 */
	@GetMapping("author/advanced/save")
	public void advancedAuthorSave(@RequestParam String authorName, @RequestParam String lastName,
			@RequestParam String phone, @RequestParam String email, @RequestParam Integer id) {
		Author auth = authorRepository.getAuthorById(id);
		String[] str = {authorName, lastName, phone, email};
		for(int i = 0; i < str.length; i++) {
			if(str[i].equalsIgnoreCase("undefined")) {
				str[i] = null;
			}
		}
		System.out.println(auth == null);
		if (auth == null) {
			System.out.println("if");
			auth = new Author(str[0], str[1], str[2], str[3]);
			authorRepository.save(auth);
			return;
		}
		System.out.println("else");
		auth.setAuthorName(str[0]);
		auth.setLastName(str[1]);
		auth.setPhone(str[2]);
		auth.setEmail(str[3]);
		authorRepository.save(auth);
		return;
	}
	
	/**
	 * takes an id of an author and deletes it.
	 * The given author will only be deleted if:
	 * it can be found, if it can not be found a <code>java.lang.NullPointerException</code> will be thrown
	 * @param id
	 * @throws java.lang.NullPointerException
	 * @return String
	 */
	@GetMapping("author/delete")
	public String deleteAuthor(@RequestParam Integer id) {
		try {
			Author auth = authorRepository.getAuthorById(id);
			if(auth.getImages().size() != 0) {
				return "ERRstillUsed";
			}
			authorRepository.delete(auth);
			return "OK";
		} catch (Exception e) {
			e.printStackTrace();
			return Arrays.toString(e.getStackTrace());
		}
	}
	
	/**
	 * removes empty authors
	 */
	@GetMapping("author/fix")
	public void fixAuth(){
		List<Author> authList = new ArrayList<Author>();
		authList = authorRepository.allAuthors();
		for(Author auth: authList) {
			if(auth.getAuthorName().isBlank() && auth.getEmail() == null && auth.getLastName() == null && auth.getPhone() == null) {
				System.out.println("yes");
				authorRepository.delete(auth);
			}
		}
	}

}