package imageSearcher;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
	
	
	@Resource
    private ImageResourceHttpRequestHandler imageResourceHttpRequestHandler;
    @GetMapping("/download")
    public void download(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam String url)
            throws ServletException, IOException {
        File file = new File(url);
        httpServletRequest.setAttribute(ImageResourceHttpRequestHandler.ATTRIBUTE_FILE, file);
        imageResourceHttpRequestHandler.handleRequest(httpServletRequest, httpServletResponse);
    }
    
    @GetMapping("/add/tags")
    public String test(@RequestParam String add,@RequestParam String URL) {
    	Image img = imageRep.addTags(URL);
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
    
    @GetMapping("/get/tags")
    public Iterable<String> gettags(@RequestParam String URL){
    	List<List<String>> a = imageRep.getTags(URL);
    	List<String> b = new ArrayList<String>();
    	for(List<String> c:a) {
    		b.add(c.get(5));
    	}
    	return b;
    }
}

