package rrs.model.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rrs.model.entities.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
}
