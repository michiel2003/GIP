package imageDB.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import imageDB.image.Image;
import imageDB.image.ImageRep;
import imageDB.tags.Tag;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class DeleteController {

	//Adding image repository
	@Autowired
	private ImageRep imageRep;
	
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
    
    
}