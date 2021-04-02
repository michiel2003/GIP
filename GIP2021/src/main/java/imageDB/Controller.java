package imageDB;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imageDB.authors.Author;
import imageDB.authors.AuthorRep;
import imageDB.image.Image;
import imageDB.image.ImageRepository;
import imageDB.image.ImageResourceHttpRequestHandler;
import imageDB.tags.Tag;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class Controller {

	//Adding image repository
	@Autowired
	private ImageRepository imageRep;

	//Adding author repository
	@Autowired
	private AuthorRep authrep;

	//Get all images
	@GetMapping(path="/all")
	public Iterable<Image> index() {
		return imageRep.findAll();
	}

	//Get all image URLS
	@GetMapping(path = "/all/URL")
	public Iterable<String> AllUrl() {
		return imageRep.findAllURL();
	}

	//Enable the image to be forwarded on the localhost
	@Resource
    private ImageResourceHttpRequestHandler imageResourceHttpRequestHandler;
    @GetMapping("/download")
    public void download(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam String url)
            throws ServletException, IOException {
        File file = new File(url);
        httpServletRequest.setAttribute(ImageResourceHttpRequestHandler.ATTRIBUTE_FILE, file);
        imageResourceHttpRequestHandler.handleRequest(httpServletRequest, httpServletResponse);
    }

    //Adding tags
    @GetMapping("/add/tags")
    public String addTags(@RequestParam String add,@RequestParam String URL) {
    	Image img = imageRep.getImageByUrl(URL);
    	List<Tag> testlist = new ArrayList<Tag>();
    	testlist.addAll(img.getTags());
    	testlist.add(new Tag(add));
    	for(Tag a: img.getTags()) {
    		System.out.println(a.tagName);
    	}
    	img.setTags(testlist);
    	imageRep.save(img);
    	return "done";
    }

    //Adding authors
    @GetMapping("/add/author")
    public void createAuthor(@RequestParam String AuthName) {
    	Author author = new Author(AuthName);
    	authrep.save(author);
    }

    //Retrieving tags from a specific image
    @GetMapping("/get/tags")
    public Iterable<String> gettags(@RequestParam String URL){
    	List<List<String>> a = imageRep.getTags(URL);
    	List<String> b = new ArrayList<String>();
    	for(List<String> c:a) {
    		b.add(c.get(5));
    	}
    	return b;
    }

    //Delete a specific tag from a specific image
    @GetMapping("/delete/tag")
    public String delete(@RequestParam String URL, @RequestParam int index) {
    	Image img = imageRep.getImageByUrl(URL);
    	List<Tag> tags = new ArrayList<Tag>();
    	tags.addAll(img.getTags());
    	tags.remove(index);
    	img.setTags(tags);
    	imageRep.save(img);
    	return "done";
    }

    //Get all images with a specific tag
    @GetMapping("/search/bytag")
    public List<String> Stag(@RequestParam String s){
    	return imageRep.ImageTagSearch(s);
    }

    @GetMapping("/add/authtoimg")
    public void addAuthorToImage(@RequestParam String URL, @RequestParam String authorName) {

    	Author auth = authrep.findAuthByName(authorName);
    	if(auth == null) {
    		auth = new Author(authorName);
    		authrep.save(auth);
    	}
    	Image img = imageRep.getImageByUrl(URL);
    	img.author = auth;
    	imageRep.save(img);
    }
    
    @GetMapping("/get/author")
    public String getAuthorByURL(@RequestParam String URL) {
    	return authrep.getAuthorOnImageURL(URL);
    }


}