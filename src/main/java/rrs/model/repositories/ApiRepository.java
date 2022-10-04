package rrs.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rrs.model.entities.Api;

@Repository
public interface ApiRepository extends JpaRepository<Api, String>{
}
