package imageDB.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imageDB.IconCreator.IconRep;
import imageDB.authors.Author;
import imageDB.authors.AuthorRep;
import imageDB.image.Image;
import imageDB.image.ImageRep;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ImageController {

	// Adding image repository
	@Autowired
	private ImageRep imageRep;

	// Adding author repository
	@Autowired
	private AuthorRep authrep;

	@Autowired
	public IconRep icrep;

	// image get mappings

	@GetMapping(path = "/get/All")
	public Iterable<Image> index() {
		return imageRep.findAll();
	}

}
