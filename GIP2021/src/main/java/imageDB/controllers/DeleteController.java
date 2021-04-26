package imageDB.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imageDB.IconCreator.IconRep;
import imageDB.filePaths.PathRepository;
import imageDB.image.Image;
import imageDB.image.ImageRep;
import imageDB.location.LocationRep;
import imageDB.tags.Tag;
import imageDB.tags.TagRep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Icon;

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
	
	@Autowired
	private IconRep icrep;
	
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
    		imageDB.IconCreator.Icon icon= new imageDB.IconCreator.Icon(imageRep.ImageIconFinder(imageToDelete.imageURL));
    		System.out.println(icon.iconURL);
    		imageDB.IconCreator.Icon ExactIcon = icrep.getExactIcon(icon.iconURL);
    		System.out.println(icrep.getExactIcon(imageRep.ImageIconFinder(imageToDelete.imageURL)));
    		System.out.println(ExactIcon.iconURL);
    		File f = new File(icon.iconURL);
    		f.delete();
    		icrep.delete(ExactIcon);
    		imageRep.delete(imageToDelete);
    		System.out.println(imageToDelete.imageURL);
    	}
    }
    
    @GetMapping("/delete/tag/noLongerConnectedToImage")
    public void deleteTagNoLongerConnectedToImage(){
    	List<String> allTagsFromDBList = new ArrayList<String>();
    	allTagsFromDBList.addAll(tagRep.getAllTags());
    	
    	List<String> allTagsFromImages = new ArrayList<String>();
    	for(Image img:imageRep.findAll()) {
    		List<Tag> tagsFromImage = new ArrayList<Tag>();
    		tagsFromImage.addAll(img.getTags());
    		for(Tag tag:tagsFromImage) {
    			allTagsFromImages.add(tag.tagName);
    		}
    	}
    	
    	List<String> notFound = new ArrayList<String>();
    	
    	notFound.addAll(compareToReturnNotFound(allTagsFromDBList, allTagsFromImages));
    	for(String tagname: notFound) {
    		Tag notFoundTag = tagRep.findTagByName(tagname);
    		tagRep.delete(notFoundTag);
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
    
    @GetMapping("delete/icon/noLongerInExplorer")
    public void iconDelete() {
    	File file = new File("DATA\\ICONS");
    	String pathExplorer = file.getAbsolutePath().toString();
    	List<String> iconsPathDB = new ArrayList<String>();
    	iconsPathDB.addAll(icrep.getAllIcons());
    	List<String> iconsPathExplorer = new ArrayList<String>();
    	for(String path: new File(pathExplorer).list()) {
    		iconsPathExplorer.add(file.getAbsolutePath().toString().replaceAll("\\\\", "/")+ "/" + path);
    	}
    	
    	List<String> notFound = new ArrayList<String>();
    	
    	notFound.addAll(compareToReturnNotFound(iconsPathDB, iconsPathExplorer));
    	for(String url: notFound) {
    		imageDB.IconCreator.Icon notFoundIcon = icrep.getExactIcon(url);
    		icrep.delete(notFoundIcon);
    	}
    }
    
    
    
    public static void main(String[] args) {
    	try {
			System.out.println(Path.of(".").toRealPath().relativize(Path.of("U:\\\\GIP michiel 2021\\\\GIP\\\\GIP2021\\\\DATA\\\\ICONS").toRealPath()));
			System.out.println(Path.of(".").toRealPath());
			
			File file = new File("DATA\\ICONS");
			String[] files = file.list();
			System.out.println("**" + file.getPath());
			System.out.println(file.getAbsolutePath());
			for(String a: files) {
				System.out.println(a);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    
}