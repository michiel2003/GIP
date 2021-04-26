package imageDB.filePaths;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PathRepository extends CrudRepository<FilePath, Integer>{

	@Query(value = "select path from filepath", nativeQuery = true)
	List<String> allFilePaths();
	
}
