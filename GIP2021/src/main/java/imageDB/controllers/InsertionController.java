package imageDB.controllers;

import java.awt.Desktop;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import imageDB.IconCreator.Icon;
import imageDB.IconCreator.IconCreator;
import imageDB.IconCreator.IconRep;
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

	@Autowired
	private IconRep icrep;

	
}
