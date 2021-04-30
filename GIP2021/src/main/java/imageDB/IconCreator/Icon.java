package imageDB.IconCreator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import imageDB.image.Image;

@Entity
@Table(name = "icons")	
public class Icon {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;
	
	@Column(name= "iconURL")
	public String iconURL;
	
	@OneToOne(mappedBy = "icon", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	public Image image;	
	
	public Icon(String url) {
		this.iconURL = url;
	}
	
	public Icon() {}
}
