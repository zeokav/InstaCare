package edu.gatech.communicationservice.persistence.repositories;

import edu.gatech.communicationservice.persistence.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {

}
