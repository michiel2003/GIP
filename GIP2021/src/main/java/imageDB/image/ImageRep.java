package imageDB.image;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import imageDB.IconCreator.Icon;

@Repository
public interface ImageRep extends CrudRepository<Image, Integer> {

	@Query(value = "select * from Image", nativeQuery = true)
	Iterable<Image> findAll();

	@Query(value = "select Image.imageURL from Image", nativeQuery = true)
	List<String> findAllURL();

	@Query(value = "select * from Image where Image.imageURL like %?1% limit 1", nativeQuery = true)
	Image getImageByUrl(@Param("URL") String URL);

	@Query(value = "select imageid, tagid, image.id as \"imgid\", image.imageurl, tags.id, tags.tagname from imagetag join image on imagetag.imageid = image.id join tags on tags.id = imagetag.tagid where image.imageurl like %?1%", nativeQuery = true)
	List<List<String>> getTags(@Param("URL") String URL);

	// search query's
	@Query(value = "select distinct image.imageurl from imagetag join tags on imagetag.tagid = tags.id join image on imagetag.imageid = image.id where tags.tagname like %?1%", nativeQuery = true)
	List<String> ImageTagSearch(@Param("SearchTerm") String SearchTerm);

	@Query(value = "select distinct image.id, image.imageurl, image.authors_author_id, image.locations_location_id from imagetag join tags on imagetag.tagid = tags.id join image on imagetag.imageid = image.id where tags.tagname like ?1", nativeQuery = true)
	List<Image> ImageTagSearchFullImage(@Param("SearchTerm") String SearchTerm);

	@Query(value = "select distinct image.imageurl from authors join image on authors.author_id = image.authors_author_id where authors.author_name like %?1%", nativeQuery = true)
	List<String> ImageAuthorSearch(@Param("SearchTerm") String SearchTerm);
	
	@Query(value = "select icons.iconurl from image join icons ON image.icons_icon_id = icons.id where image.imageurl like ?1", nativeQuery = true)
	String ImageIconFinder(@Param("url") String url);
	
	
}
