package imageDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import imageDB.IconCreator.IconCreator;
import imageDB.image.ImageController;
import imageDB.tags.TagController;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class StartController {
	
	@Autowired
	IconCreator iconCreator;
	
	@Autowired
	ImageController imageController;
	
	@Autowired
	TagController tagController;

	/**
	 * a method that executes specific commands on startup so that the database gets
	 * cleaned before startup
	 * 
	 * @see insertFromPaths
	 * @see threadICO
	 * @see deleteImageNoLongerInFolder
	 * @see deleteTagNoLongerConnectedToImage
	 */
	
	Executor executor = Executors.newSingleThreadExecutor();
	
	@GetMapping("/start/app")
	public void boot() {
		imageController.deleteImageNoLongerInFolder();
		tagController.deleteTagNoLongerConnectedToImage();
		imageController.insertFromPaths();
		iconCreator.threadICO();
		System.out.println("done");
	}

}