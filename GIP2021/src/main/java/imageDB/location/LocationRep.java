package imageDB.location;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRep extends CrudRepository<Location, Integer>{
	
	

}
