package imageDB.blobDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import imageDB.IconCreator.IconCreator;
import imageDB.image.ImageController;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class BlobDecoder {

	@Autowired
	private ImageController imageController;

	@Autowired
	private IconCreator creator;

	/**
	 * Used for uploading images Method takes a String and makes sure it's entered
	 * into the database
	 * 
	 * @see ImageController
	 * @see IconCreator
	 * @throws IOException
	 * @param blob MultipartFile
	 * @return response code String
	 */
	@RequestMapping(value = "/upload/image", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String uploadImage(@RequestParam(value = "blob") MultipartFile blob) {
		try {
			byte[] bytes = blob.getBytes();
			File file = new File(new File("").getAbsolutePath().replaceAll("\\\\", "/") + "/SampleImages/"
					+ blob.getOriginalFilename());
			String path = new File("").getAbsolutePath().replaceAll("\\\\", "/") + "/SampleImages/"
					+ blob.getOriginalFilename();
			System.out.println(path);
			if (file.exists()) {
				return "Already exists";
			}
			FileOutputStream stream = new FileOutputStream(path);
			stream.write(bytes);
			stream.close();
			imageController.insertFromPaths();
			creator.threadICO();
			return "OK";
		} catch (IOException e) {
			e.printStackTrace();
			return "FAIL";
		}
	}

}