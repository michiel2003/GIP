package imageDB.tags;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRep extends CrudRepository<Tag, Integer>{

	@Query(value = "select * from tags where tagname like ?1", nativeQuery = true)
	Tag findTagByName(@Param("tagName")String tagName);
	
	@Query(value = "select tagname from tags", nativeQuery = true)
	List<String> getAllTags();
	
}
