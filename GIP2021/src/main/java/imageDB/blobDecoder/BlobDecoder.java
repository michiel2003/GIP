package imageDB.blobDecoder;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.cj.jdbc.Blob;

import imageDB.IconCreator.IconRep;
import imageDB.filePaths.PathRepository;
import imageDB.image.Image;
import imageDB.image.ImageRep;
import imageDB.location.LocationRep;
import imageDB.tags.Tag;
import imageDB.tags.TagRep;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class BlobDecoder {

	//Adding image repository
	@Autowired
	private ImageRep imageRep;
	
	@Autowired
	private TagRep tagRep;
	
	@Autowired
	private PathRepository filerep;
	
	@Autowired
	private IconRep icrep;
	
	@GetMapping("/upload/image")
	public void UploadImage(@RequestParam String blobURI) {

		Image image = null;
		try {
		    URL url = new URL("http://www.yahoo.com/image_to_read.jpg");
		    image = ImageIO.read(url);
		} catch (IOException e) {
		}
	}
	
	
}