package rrs.model.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import rrs.model.entities.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {

	@Transactional @Modifying @Query("UPDATE CONTENTS SET views = views+1 WHERE id =:content_id") 
	public void upviews(Long content_id) throws IllegalStateException;
}
