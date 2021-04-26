package imageDB.controllers;



import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import imageDB.image.ImageResourceHttpRequestHandler;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class DownloadController {
	
	//Enable the image to be forwarded on the localhost
		@Resource
	    private ImageResourceHttpRequestHandler imageResourceHttpRequestHandler;
	    @GetMapping("/download")
	    public void download(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @RequestParam String url)
	            throws ServletException, IOException {
	        File file = new File(url);
	        httpServletRequest.setAttribute(ImageResourceHttpRequestHandler.ATTRIBUTE_FILE, file);
	        imageResourceHttpRequestHandler.handleRequest(httpServletRequest, httpServletResponse);
	    }

}
