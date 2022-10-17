package rrs.model.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import rrs.model.entities.Like;

public interface LikeRepository extends JpaRepository<Like, Like> {

	@Query(value = "SELECT * FROM LIKES WHERE content_id IS NULL", nativeQuery = true)
	public List<Like> findLikesByContentId();
	
	@Query(value = "SELECT * FROM LIKES WHERE content_id = :content_id", nativeQuery = true)
	public List<Like> findLikesByContentId(Long content_id);
		
	@Modifying @Transactional
	@Query(value = "DELETE FROM LIKES WHERE content_id IS NULL", nativeQuery = true)
	public int deleteLikesByContentId();
	
	@Modifying @Transactional
	@Query(value = "DELETE FROM LIKES WHERE content_id = :content_id", nativeQuery = true)
	public int deleteLikesByContentId(Long content_id);
}
