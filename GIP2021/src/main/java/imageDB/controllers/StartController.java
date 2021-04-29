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

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class StartController {
	
	DeleteController del;
	AddController add;
	InsertionController insert;
	IconCreator crea;

	@GetMapping("/start/app")
	public void boot() {
		insert.insertFromPaths();	
		crea.createIco();
		del.deleteImageNoLongerInFolder();
		del.deleteTagNoLongerConnectedToImage();
	}
	
}