package imageSearcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tags")
public class Tag{
	
	@Id
	@GeneratedValue
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
