package imageDB.location;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import imageDB.image.Image;

@Entity
@Table(name = "locations")
public class Location {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "locationID")
	private Integer locationID;
	
	
	@Column(name = "locationName")
	private String locationName;
	
	@OneToMany(mappedBy = "location", fetch = FetchType.LAZY, orphanRemoval = false)
	private List<Image> images = new ArrayList<Image>();

	
	//Location constructors
	
	public Location(String LocationName) {
		this.locationName = LocationName;
	}
	
	//adding default constructor
	public Location() {}
	
	
	//end of construcotrs
	
	public Integer getLocationID() {
		return locationID;
	}

	public void setLocationID(Integer locationID) {
		this.locationID = locationID;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

}
