package imageSearcher;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "image")		
public class Image {
	
	@Id
	@Column(name = "id")
	public Integer id;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	@Column(name= "imageURL")
	public String imageURL;

	@ManyToMany(cascade = {
	        CascadeType.PERSIST,
	        CascadeType.MERGE
	    })
	    @JoinTable(name = "image_tag",
	        joinColumns = @JoinColumn(name = "image_id"),
	        inverseJoinColumns = @JoinColumn(name = "tag_id")
	    )
	    private List<Tag> tags = new ArrayList<>();
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image)) return false;
        return id != null && id.equals(((Image) o).getId());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
