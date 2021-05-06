package imageDB.tags;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imageDB.IconCreator.IconRep;
import imageDB.filePaths.PathRepository;
import imageDB.image.Image;
import imageDB.image.ImageRep;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class TagController {

	// Adding image repository
	@Autowired
	private ImageRep imageRep;

	@Autowired
	private TagRep tagRep;

	/**
	 * add a new tag to a given image
	 * 
	 * @param String the name of the new tag
	 * @param String the url of the image to add the tag to
	 * @return String "done" if completed
	 */
	@GetMapping("/add/tags")
	public String addTags(@RequestParam String add, @RequestParam String URL) {
		Tag tag = tagRep.findTagByName(add);
		if (tag == null) {
			tag = new Tag(add);
		}
		Image img = imageRep.getImageByUrl(URL);
		List<Tag> testlist = new ArrayList<Tag>();
		testlist.addAll(img.getTags());
		testlist.add(tag);
		for (Tag a : img.getTags()) {
			System.out.println(a.tagName);
		}
		img.setTags(testlist);
		imageRep.save(img);
		return "done";
	}
	
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
	 * Get the tag names from a specific image
	 * 
	 * @param The URL from which
	 * @return Iterable tag names
	 */
	@GetMapping("/get/tags")
	public Iterable<String> gettags(@RequestParam String URL) {
		List<List<String>> a = imageRep.getTags(URL);
		List<String> b = new ArrayList<String>();
		for (List<String> c : a) {
			b.add(c.get(5));
		}
		return b;
	}
	
	/**
	 * Used to search image by tag
	 * 
	 * @param s = search term
	 * @return List with all images corresponding
	 */
	@GetMapping("/get/bytag")
	public List<String> sTag(@RequestParam String s) {
		return imageRep.ImageTagSearch(s);
	}
	
	
	
	
	
	
	
}