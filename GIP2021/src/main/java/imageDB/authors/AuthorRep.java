package imageDB.authors;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRep extends CrudRepository<Author, Integer>{
	
	@Query(value = "select * from authors where author_name like ?1 limit 1", nativeQuery = true)
	Author findAuthByName(@Param("name")String name);
	
	@Query(value = "select author_name from authors", nativeQuery = true)
	List<String> allAuthorNames();
	
	@Query(value = "select author_name from authors join image on author_id = image.authors_author_id where image.imageurl like %?1% limit 1", nativeQuery = true)
	String getAuthorOnImageURL(@Param("URL") String URL);
	
	@Query(value = "select author_id, author_name, phone, email, last_name from authors", nativeQuery = true)
	List<Author> allAuthors();
	
	//@Query(value = "select phone from authors where authors.author_name like %?1%")

}
