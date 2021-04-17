package imageDB.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imageDB.filePaths.PathRepository;
import imageDB.image.Image;
import imageDB.image.ImageRep;
import imageDB.location.LocationRep;
import imageDB.tags.Tag;
import imageDB.tags.TagRep;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class DeleteController {

	//Adding image repository
	@Autowired
	private ImageRep imageRep;
	
	@Autowired
	private TagRep tagRep;
	
	@Autowired
	private PathRepository filerep;
	
	//Delete a specific tag from a specific image
    @GetMapping("/delete/tag")
    public String deleteTag(@RequestParam String URL, @RequestParam int index) {
    	Image img = imageRep.getImageByUrl(URL);
    	List<Tag> tags = new ArrayList<Tag>();
    	tags.addAll(img.getTags());
    	
    	List<Image> imageList = imageRep.ImageTagSearchFullImage(tags.get(index).tagName);
    	System.out.println(imageList.size());
    	
    	if(imageList.size() <= 1) {
    		tagRep.delete(tags.get(index));
    	}
    	
    	
    	tags.remove(index);
    	img.setTags(tags);
    	imageRep.save(img);
    	return "done";
    }
    
    @GetMapping("/delete/Image/NoLongerInFolder")
    public void deleteImage() {
    	List<String> allURLfromDB =new ArrayList<String>();
    	allURLfromDB.addAll(imageRep.findAllURL());
    	List<String> allURLfromFiles = new ArrayList<String>();
    	allURLfromFiles.addAll(filerep.allFilePaths());
    	
    	List<String> ProcessedFromDB = new ArrayList<String>();
    	
    	List<String> ProcessedFromFiles = new ArrayList<String>();
    	
    	for(String a: allURLfromDB) {
    		int sub = a.lastIndexOf("/")+1;
    		a = a.substring(sub);
    		ProcessedFromDB.add(a);
    	}
    	for(String b : allURLfromFiles) {
    		File file = new File(b);
    		String[] imageURLS = file.list();
    		for(String c: imageURLS) {
    			ProcessedFromFiles.add(c);
    		}
    	}
    	
    	List<String> compared = new ArrayList<String>();
    	compared.addAll(compareToReturnNotFound(ProcessedFromDB, ProcessedFromFiles));
    	if(compared.size() == 0) {
    		return;
    	}
    	for(String a: compared) {
    		Image imageToDelete = imageRep.getImageByUrl(a);
    		imageRep.delete(imageToDelete);
    	}
    }
    
    public List<String> compareToReturnNotFound(List<String>compFrom, List<String>compTo) {
    	List<String> nonFound = new ArrayList<String>();
    	for(String compareFrom: compFrom) {
    		int occ = 0;
    		for(String compareTo: compTo) {
    			if(compareFrom.equalsIgnoreCase(compareTo)) {
    				occ++;
    			}
    		}
    		if(occ == 0) {
    			nonFound.add(compareFrom);
    		}
    	}
    	return nonFound;
    }
    
    public static void main(String[] args) {
		DeleteController exec = new DeleteController();
		exec.deleteImage();
	}
    
    
}