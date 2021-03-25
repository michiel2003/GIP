package imageSearcher;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "authors")
public class Author {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "authorId")
	public Integer authorId;
	
	@Column(name = "authorName")
	public String authorName;
	
	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY, orphanRemoval = false)
	public List<Image> images = new ArrayList<Image>();
	
	public Author(String name) {
		this.authorName = name;
	}
	
	public Author() {}

}
