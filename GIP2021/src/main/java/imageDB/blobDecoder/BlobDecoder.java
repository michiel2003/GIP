package imageDB.blobDecoder;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.cj.jdbc.Blob;

import imageDB.IconCreator.IconCreator;
import imageDB.IconCreator.IconRep;
import imageDB.controllers.InsertionController;
import imageDB.filePaths.PathRepository;
import imageDB.image.Image;
import imageDB.image.ImageRep;
import imageDB.location.LocationRep;
import imageDB.tags.Tag;
import imageDB.tags.TagRep;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.sql.SQLException;
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
	
	@Autowired
	private InsertionController insert;
	
	@Autowired
	private IconCreator creator;
	
	@RequestMapping(value = "/upload/image", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String uploadImage(@RequestParam(value = "blob") MultipartFile blob) {
		try {
			byte[] bytes = blob.getBytes();
			File file = new File(new File("").getAbsolutePath().replaceAll("\\\\",  "/") + "/SampleImages/" + blob.getOriginalFilename());
			String path = new File("").getAbsolutePath().replaceAll("\\\\",  "/") + "/SampleImages/" + blob.getOriginalFilename();
			System.out.println(path);
			if(file.exists()) {
				return "Already exists";
			}
			FileOutputStream stream = new FileOutputStream(path);
			stream.write(bytes);
			stream.close();
			insert.insertFromPaths();
			creator.threadICO();
			return "OK";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAIL";
		}				
	}
	
	
}