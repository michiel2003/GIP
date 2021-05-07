package imageDB.authors;

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
@Table(name = "authors")
public class Author {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "authorId")
	private Integer authorId;

	@Column(name = "authorName")
	private String authorName;
	
	@Column(name = "lastName")
	private String lastName;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "email")
	private String email;
	
	@OneToMany(mappedBy = "author", fetch = FetchType.LAZY, orphanRemoval = false)
	public List<Image> images = new ArrayList<Image>();
	
	public Author(String name) {
		this.authorName = name;
	}
	
	public Author() {}
	
	public Author(String authorName, String lastName, String phone, String email) {
		this.authorName = authorName;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
	}
	
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}
	
	public Integer getAuthorId() {
		return authorId;
	}
}
