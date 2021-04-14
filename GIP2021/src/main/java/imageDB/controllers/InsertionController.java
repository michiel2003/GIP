package imageDB.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import imageDB.filePaths.PathRepository;
import imageDB.image.Image;
import imageDB.image.ImageRep;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class InsertionController {

	String[] filepaths;

	List<String> paths = new ArrayList<String>();

	@Autowired
	private ImageRep imagerep;

	@Autowired
	private PathRepository pathrep;

	private List<String> getPaths() {
		return pathrep.allFilePaths();
	}

	@GetMapping("/insert/fromPaths")
	public void insertFromPaths() {
		List<String> paths = new ArrayList<String>();
		paths.addAll(getPaths());
		for (String path : paths) {
			File file = new File(path);
			String subPaths[] = file.list();
			String absolutePath = file.getPath().replaceAll("\\\\", "/");
			for (String subPath : subPaths) {
				String exactPath = absolutePath + "/" + subPath;
				CheckIfImage(exactPath);
				if (CheckIfImage(exactPath) == true) {
					if (imagerep.getImageByUrl(exactPath) == null) {
						insertImageIntoDB(exactPath);
					}
				}
			}
		}
	}

	private boolean CheckIfImage(String filepath) {
		File f = new File(filepath);
		String mimetype = new MimetypesFileTypeMap().getContentType(f);
		String type = mimetype.split("/")[0];
		if (type.equals("image")) {
			return true;
		} else {
			return false;
		}
	}

	public void insertImageIntoDB(String FPath) {
		Image newImage = new Image();
		newImage.setImageURL(FPath);
		imagerep.save(newImage);
	}

	public static void main(String[] args) {
		InsertionController exec = new InsertionController();
		exec.insertFromPaths();
	}

}
