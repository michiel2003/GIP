package imageDB.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import imageDB.image.Image;

@Entity
@Table(name = "tags")
public class Tag{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	public Integer TagId;
	

	@Column(name="tagname")
	public String tagName;
	
	@ManyToMany(mappedBy = "tags")
    private List<Image> images = new ArrayList<>();
 
    public Tag() {}
 
    public Tag(String name) {
        this.tagName = name;
    }
    
    public Tag(Integer id, String tagName) {
    	this.TagId = id;
    	this.tagName = tagName;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(tagName, tag.tagName);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(tagName);
    }
    
    
}
