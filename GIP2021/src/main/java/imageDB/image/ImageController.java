package imageDB.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imageDB.IconCreator.IconController;
import imageDB.IconCreator.IconRep;
import imageDB.filePaths.PathRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.activation.MimetypesFileTypeMap;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ImageController {

	// Adding image repository
	@Autowired
	private ImageRep imageRep;

	@Autowired
	public IconRep icrep;

	@Autowired
	public PathRepository pathrep;

	@Autowired
	private IconController iconController;

	// image get mappings

	/**
	 * get the full scale image instead of the icon
	 * 
	 * @param URL the url of the image
	 * @return String the indepth image
	 */
	@GetMapping("/get/indepth")
	public String getIndepthFullScaleImage(@RequestParam String url) {
		return icrep.IndepthImageFinder(url);
	}

	private List<String> getPaths() {
		return pathrep.allFilePaths();
	}

	/**
	 * insert all images from the paths given in the databse
	 * 
	 * @return void
	 */
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
					if (imageRep.getImageByUrl(exactPath) == null) {
						insertImageIntoDB(exactPath);
					}
				}
			}
		}

	}

	/**
	 * checks if a file is an image
	 * 
	 * @param filepath to check
	 * @return true if the file is an image <br>
	 *         false if the file is not an image
	 */
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

	/**
	 * inserting the given path into the database
	 * 
	 * @param FPath of the new image
	 */
	public void insertImageIntoDB(String FPath) {
		Image newImage = new Image();
		newImage.setImageURL(FPath);
		imageRep.save(newImage);
	}

	/**
	 * deletes all images which are no longer available in the folder from the
	 * database
	 * 
	 * @see compareForImageDeletion
	 * 
	 * @see getAllFromFiles
	 * @see getAllFromDB
	 */
	@GetMapping("/delete/Image/NoLongerInFolder")
	public void deleteImageNoLongerInFolder() {
		List<String> allURLfromDB = new ArrayList<String>();
		allURLfromDB.addAll(imageRep.findAllURL());
		List<String> allURLfromFiles = new ArrayList<String>();
		allURLfromFiles.addAll(pathrep.allFilePaths());

		List<String> ProcessedFromDB = new ArrayList<String>();
		ProcessedFromDB.addAll(getAllFromDB(allURLfromDB, ProcessedFromDB));
		List<String> ProcessedFromFiles = new ArrayList<String>();
		ProcessedFromFiles.addAll(getAllFromFiles(allURLfromFiles, ProcessedFromFiles));

		List<String> compared = new ArrayList<String>();
		compared.addAll(compareToReturnNotFound(ProcessedFromDB, ProcessedFromFiles));
		if (compared.size() == 0) {
			return;
		}
		for (String a : compared) {
			try{
			compareForImageDeletion(a);
			}
			catch(Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}
	

	/**
	 * used to delete the image with give url
	 * 
	 * @param a where a is the url of the image to compare
	 * @see deleteImageNoLongerInFolder
	 * @throws java.lang.NullPointerException
	 */
	private void compareForImageDeletion(String a) {
		Image imageToDelete = imageRep.getImageByUrl(a);
		imageDB.IconCreator.Icon icon = new imageDB.IconCreator.Icon(imageRep.ImageIconFinder(imageToDelete.imageURL));
		imageDB.IconCreator.Icon ExactIcon = icrep.getExactIcon(icon.iconURL);
		File f = new File(icon.iconURL);
		f.delete();
		icrep.delete(ExactIcon);
		imageRep.delete(imageToDelete);
	}

	/**
	 * gets all the urls form the file system
	 * 
	 * @param allURLfromFiles
	 * @param ProcessedFromFiles
	 * @return a list all the URLS found in the file explorer
	 * @see deleteImageNoLongerInFolder
	 */
	private List<String> getAllFromFiles(List<String> allURLfromFiles, List<String> ProcessedFromFiles) {
		for (String b : allURLfromFiles) {
			File file = new File(b);
			String[] imageURLS = file.list();
			for (String c : imageURLS) {
				ProcessedFromFiles.add(c);
			}
		}
		return allURLfromFiles;
	}

	/**
	 * gets all the urls from the databse
	 * 
	 * @param allURLfromDB
	 * @param ProcessedFromDB
	 * @return List with all the urls from the databse
	 */
	private List<String> getAllFromDB(List<String> allURLfromDB, List<String> ProcessedFromDB) {
		for (String a : allURLfromDB) {
			int sub = a.lastIndexOf("/") + 1;
			a = a.substring(sub);
			ProcessedFromDB.add(a);
		}
		return ProcessedFromDB;
	}

	/**
	 * used to compare two list which each other and find the difference between the
	 * two
	 * 
	 * @param compFrom used for the 1st list
	 * @param compTo   used for the 2nd list
	 * @return A list with the items which occur in list 1 but not in list 2
	 */
	public List<String> compareToReturnNotFound(List<String> compFrom, List<String> compTo) {
		List<String> nonFound = new ArrayList<String>();
		for (String compareFrom : compFrom) {
			int occ = 0;
			for (String compareTo : compTo) {
				if (compareFrom.equalsIgnoreCase(compareTo)) {
					occ++;
				}
			}
			if (occ == 0) {
				nonFound.add(compareFrom);
			}
		}
		return nonFound;
	}

	Executor executor = Executors.newSingleThreadExecutor();

	/**
	 * Thread to delete the image
	 * 
	 * @param url of the image to delete
	 */
	@GetMapping("delete/image")
	public void delIMG(@RequestParam String url) {
		executor.execute(() -> deleteImage(url));
	}

	/**
	 * used to delete an image with a specific url
	 * 
	 * @param url of the image to delete
	 */
	public void deleteImage(String url) {
		System.out.println(url + " img");
		File file = new File(url);
		file.delete();
		deleteImageNoLongerInFolder();
		iconController.iconDelete();
	}

}
