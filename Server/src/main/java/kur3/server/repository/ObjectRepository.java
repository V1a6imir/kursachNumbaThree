package kur3.server.repository;

import kur3.server.entity.ObjectData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ObjectRepository extends JpaRepository<ObjectData, Long> {
    ObjectData findByNameIgnoreCase(String name);
}

