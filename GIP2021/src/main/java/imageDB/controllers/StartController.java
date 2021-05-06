package imageDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imageDB.IconCreator.Icon;
import imageDB.IconCreator.IconCreator;
import imageDB.IconCreator.IconRep;
import imageDB.filePaths.PathRepository;
import imageDB.image.Image;
import imageDB.image.ImageController;
import imageDB.image.ImageRep;
import imageDB.location.LocationRep;
import imageDB.tags.Tag;
import imageDB.tags.TagController;
import imageDB.tags.TagRep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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