package imageDB.IconCreator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import imageDB.image.Image;
import imageDB.image.ImageRep;
import net.coobird.thumbnailator.Thumbnails;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class IconCreator {

	int width = 250;
	int height = 250;

	@Autowired
	ImageRep imgrep;

	@Autowired
	IconRep icrep;


	private String createIco() {
		List<String> imageURLS = new ArrayList<String>();
		imageURLS.addAll(imgrep.findAllURL());
		for(String url: imageURLS) {
			boolean save = true;
			try {
				Image image = imgrep.getImageByUrl(url);
				for(String urlico: icrep.getAllIcons()) {
					if(urlico.equalsIgnoreCase(Path.of(".").toRealPath().toString().replaceAll("\\\\", "/") + "/DATA/ICONS" + url.substring(url.lastIndexOf('/')))) {
						save = false;
					}
				}
				if(save == true) {
					Icon icon = resizeImageToIco(url, image);
					icrep.save(icon);
					image.icon = icon;
					imgrep.save(image);
				}
				
			} catch (Exception e) {
				return "fail";
			}
		}
		return "done";
	}

	private Icon resizeImageToIco(String url, Image image) throws IOException {
		Icon icon = new Icon();
		icon.iconURL = Path.of(".").toRealPath().toString().replaceAll("\\\\", "/") + "/DATA/ICONS" + url.substring(url.lastIndexOf('/'));
		image.icon = icon;
		System.out.println(Path.of(".").toRealPath().toString().replaceAll("\\\\", "/"));
		Thumbnails.of(new File(url))
		.size(width, height).toFile(new File(Path.of(".").toRealPath() + "/DATA/ICONS" + url.substring(url.lastIndexOf('/'))));
		System.out.println(Path.of(".").toRealPath().toString().replaceAll("\\\\", "/") + "/DATA/ICONS" + url.substring(url.lastIndexOf('/')));
		icon.iconURL = Path.of(".").toRealPath().toString().replaceAll("\\\\", "/") + "/DATA/ICONS" + url.substring(url.lastIndexOf('/'));
		return icon;
	}
	
	Executor executor = Executors.newSingleThreadExecutor();
	
	@GetMapping("/thread/ico")
	public void threadICO() {
		executor.execute(() -> createIco());
	}
}
