package rrs.model.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import rrs.model.entities.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

	@Query("SELECT o FROM CONTENTS o WHERE o.active = :active")
	public List<Content> findAllActive(Boolean active);

	@Query("SELECT o FROM CONTENTS o WHERE account_id = :account_id")
	public List<Content> findByAccountId(String account_id);	
	
	@Query(value = "SELECT o.* FROM CONTENTS o INNER JOIN CONTENT_TYPES t "
			+ "ON o.id = t.content_id WHERE t.category_id = :category_id", nativeQuery = true)
	public List<Content> findByCategory(String category_id);
	
	@Modifying @Transactional
	@Query(value = "UPDATE LIKES SET content_id=NULL WHERE content_id = :content_id", nativeQuery = true)
	public int setLike(Long content_id);
	
	@Query(value = "SELECT * FROM LIKES", nativeQuery = true)
	public List<Object[]> findAllLikes();
	
	@Query(value = "SELECT * FROM LIKES WHERE content_id IS NULL", nativeQuery = true)
	public List<Object[]> findLikesByContentId();
	
	@Query(value = "SELECT * FROM LIKES WHERE content_id = :content_id", nativeQuery = true)
	public List<Object[]> findLikesByContentId(Long content_id);
	
	@Modifying @Transactional
	@Query(value = "DELETE FROM LIKES WHERE content_id IS NULL", nativeQuery = true)
	public int deleteLikesByContentId();
	
	@Modifying @Transactional
	@Query(value = "DELETE FROM LIKES WHERE content_id = :content_id", nativeQuery = true)
	public int deleteLikesByContentId(Long content_id);
}
