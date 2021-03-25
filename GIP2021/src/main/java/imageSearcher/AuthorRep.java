package imageSearcher;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRep extends CrudRepository<Author, Integer>{
	
	@Query(value = "select * from authors where author_name like %?1% limit 1", nativeQuery = true)
	Author findAuthByName(@Param("name")String name);

}
