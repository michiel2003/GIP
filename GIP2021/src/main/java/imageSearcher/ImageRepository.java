package imageSearcher;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Integer>{
	
	@Query(value = "select * from Image", nativeQuery = true)
	Iterable<Image> findAll();
	
	@Query(value = "select Image.imageURL from Image", nativeQuery = true)
	Iterable<String> findAllURL();

}
