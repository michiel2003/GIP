package imageDB.IconCreator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class IconController {

	@Autowired
	private IconRep icrep;

	/**
	 * Used to get all the url's in the databse for the icons
	 * 
	 * @return Iterable with all the urls
	 */
	@GetMapping(path = "/get/URL")
	public Iterable<String> AllUrl() {
		return icrep.getAllIcons();
	}
	
	/**
	 * get all icon URLS from database
	 * 
	 * @return List of all the icon URLS
	 */
	@GetMapping("/icons/getIconURLS")
	public List<String> getIconURLS() {
		return icrep.getAllIcons();
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
	
	
	
}