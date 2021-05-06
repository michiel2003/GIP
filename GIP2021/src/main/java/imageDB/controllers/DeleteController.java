package imageDB.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imageDB.IconCreator.Icon;
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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class DeleteController {

	// Adding image repository
	@Autowired
	private ImageRep imageRep;

	@Autowired
	private TagRep tagRep;

	@Autowired
	private PathRepository filerep;

	@Autowired
	private IconRep icrep;

	// Delete a specific tag from a specific image
	/**
	 * delete a specific tag
	 * 
	 * @param URL   the URL of the image where the tag belongs to
	 * @param index the index of where the tag is placed
	 * @return
	 */
	@GetMapping("/delete/tag")
	public String deleteTag(@RequestParam String URL, @RequestParam int index) {
		Image img = imageRep.getImageByUrl(URL);
		List<Tag> tags = new ArrayList<Tag>();
		tags.addAll(img.getTags());

		List<Image> imageList = imageRep.ImageTagSearchFullImage(tags.get(index).tagName);
		System.out.println(imageList.size());

		if (imageList.size() <= 1) {
			tagRep.delete(tags.get(index));
		}

		tags.remove(index);
		img.setTags(tags);
		imageRep.save(img);
		return "done";
	}

	/**
	 * deletes all images which are no longer available in the folder from the
	 * database
	 * 
	 * @see compareForImageDeletion
	 * @see getAllFromFiles
	 * @see getAllFromDB
	 */
	@GetMapping("/delete/Image/NoLongerInFolder")
	public void deleteImageNoLongerInFolder() {
		List<String> allURLfromDB = new ArrayList<String>();
		allURLfromDB.addAll(imageRep.findAllURL());
		List<String> allURLfromFiles = new ArrayList<String>();
		allURLfromFiles.addAll(filerep.allFilePaths());

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
			compareForImageDeletion(a);
		}
	}

	/**
	 * used to delete the image with give url
	 * 
	 * @param a where a is the url of the image to compare
	 * @see deleteImageNoLongerInFolder
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
	 * deletes tags which are no longer used by any image
	 */
	@GetMapping("/delete/tag/noLongerConnectedToImage")
	public void deleteTagNoLongerConnectedToImage() {
		List<String> allTagsFromDBList = new ArrayList<String>();
		allTagsFromDBList.addAll(tagRep.getAllTags());

		List<String> allTagsFromImages = new ArrayList<String>();
		for (Image img : imageRep.findAll()) {
			List<Tag> tagsFromImage = new ArrayList<Tag>();
			tagsFromImage.addAll(img.getTags());
			for (Tag tag : tagsFromImage) {
				allTagsFromImages.add(tag.tagName);
			}
		}

		List<String> notFound = new ArrayList<String>();

		notFound.addAll(compareToReturnNotFound(allTagsFromDBList, allTagsFromImages));
		for (String tagname : notFound) {
			Tag notFoundTag = tagRep.findTagByName(tagname);
			tagRep.delete(notFoundTag);
		}

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

	/**
	 * delete icons which are no longer in the file explorer to avoid file not found
	 * error
	 */
	@GetMapping("delete/icon/noLongerInExplorer")
	public void iconDelete() {
		File file = new File("DATA\\ICONS");
		String pathExplorer = file.getAbsolutePath().toString();
		List<String> iconsPathDB = new ArrayList<String>();
		iconsPathDB.addAll(icrep.getAllIcons());
		List<String> iconsPathExplorer = new ArrayList<String>();
		for (String path : new File(pathExplorer).list()) {
			iconsPathExplorer.add(file.getAbsolutePath().toString().replaceAll("\\\\", "/") + "/" + path);
		}

		List<String> notFound = new ArrayList<String>();

		notFound.addAll(compareToReturnNotFound(iconsPathDB, iconsPathExplorer));
		for (String url : notFound) {
			imageDB.IconCreator.Icon notFoundIcon = icrep.getExactIcon(url);
			icrep.delete(notFoundIcon);
		}
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
		iconDelete();
	}

}