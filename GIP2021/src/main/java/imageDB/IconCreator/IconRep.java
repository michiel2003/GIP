package imageDB.IconCreator;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IconRep extends CrudRepository<Icon, Integer>{
	
	@Query(value = "select iconurl from icons", nativeQuery = true)
	List<String> getAllIcons();
	
	@Query(value = "select * from icons where iconurl like ?1 limit 1", nativeQuery = true)
	Icon getExactIcon(@Param("URL") String url);

}
