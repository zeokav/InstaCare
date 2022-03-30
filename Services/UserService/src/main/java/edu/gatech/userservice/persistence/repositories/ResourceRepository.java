package edu.gatech.userservice.persistence.repositories;

import edu.gatech.userservice.persistence.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

    List<Resource> findResourceByAvailableAndResourceNameContaining(Integer available, String resourceName);
}
