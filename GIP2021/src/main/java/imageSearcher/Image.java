package imageSearcher;

import javax.persistence.Column
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "image")		
public class Image {
	
	@Id
	@Column(name = "id")
	public Integer id;

	@Column(name= "imageURL")
	public String imageURL;

}
