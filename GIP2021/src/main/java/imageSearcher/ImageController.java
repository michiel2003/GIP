package imageSearcher;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

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
}

