package edu.gatech.userservice.persistence.repositories;

import edu.gatech.userservice.persistence.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {

}
