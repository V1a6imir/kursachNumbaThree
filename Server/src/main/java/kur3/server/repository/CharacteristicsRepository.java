package kur3.server.repository;


import kur3.server.entity.ObjectCharacteristics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacteristicsRepository extends JpaRepository<ObjectCharacteristics, Long> {
}
