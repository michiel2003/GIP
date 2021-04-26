package imageDB.location;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRep extends CrudRepository<Location, Integer>{
	
	@Query(value = "select * from locations where location_name like %?1%", nativeQuery = true)
	Location findLocationByName (@Param("LocationName") String LocationName);


}
