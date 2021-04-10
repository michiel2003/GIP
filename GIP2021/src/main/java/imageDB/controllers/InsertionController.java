package imageDB.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imageDB.image.Image;
import imageDB.image.ImageRep;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class InsertionController {

	String[] filepaths;
	
	List<String> knownExtensions = Arrays.asList(".tiff","tif", ".gif", ".png", ".eps", ".raw", ".bmp", ".jpg", ".jpeg", ".cr2", ".nef", ".orf", ".sr2");
	List<String> paths = new ArrayList<String>();
	
	@Autowired
	private ImageRep imagerep;
	
	@GetMapping("/insert/folder")
	public String insertFolder(@RequestParam String FPath, @RequestParam boolean validate) {
		List<String> validation = new ArrayList<String>();
		List<String> invalids = new ArrayList<String>();
		if(validate == false) {
			insertFolder(FPath);
			insertion();
			return "done";
		}
		File file = new File(FPath);
		filepaths = file.list();
		for(String a: filepaths) {
			Integer indexOfDot = a.indexOf('.');
			String str = a.substring(indexOfDot);
			if(checkValidExtension(str) == true) {
				System.out.println("valid");
				validation.add("valid");
			}
			if(checkValidExtension(str) == false) {
				System.out.println("Invalid");
				invalids.add(a);
				validation.add("invalid");
			}
			paths.add(file.getPath() + a);
		}
		if(invalids.size()  != 0) {
			return "error";
		}
		insertion();
		return "done";
	}
	
	
	public void insertFolder(@RequestParam String FPath) {
		File file = new File(FPath);
		filepaths = file.list();
		for(String a: filepaths) {
			paths.add(file.getPath() + a);
		}
	}
	
	public boolean checkValidExtension(String str) {
		for(String b: knownExtensions) {
			if(str.equalsIgnoreCase(b)) {
				return(true);
			}
		}
		return(false);
	}
	
	public static void main(String[] args) {
		InsertionController exec = new InsertionController();
		exec.insertFolder("D:\\GIP\\GIP\\GIP2021\\SampleImages", false);
		List<String> paths = exec.paths;
		exec.insertion();
		for(String b: paths) {
			System.out.println(b);
		}
	}
	
	public void insertion() {
		for(String a: paths) {
			String replaced = a.replaceAll("\\\\", "/");
			Image image = new Image();
			image.setImageURL(replaced);
			imagerep.save(image);
		}
	}
	
}
