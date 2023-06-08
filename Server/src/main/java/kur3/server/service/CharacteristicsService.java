package kur3.server.service;

import kur3.server.entity.ObjectCharacteristics;
import kur3.server.repository.CharacteristicsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CharacteristicsService {
    private CharacteristicsRepository сharacteristicsRepository;

    @Autowired
    public CharacteristicsService(CharacteristicsRepository сharacteristicsRepository) {
        this.сharacteristicsRepository = сharacteristicsRepository;
    }

    public void addObjectCharacteristics(ObjectCharacteristics objectCharacteristics) {
        сharacteristicsRepository.save(objectCharacteristics);
    }

    public void removeObjectCharacteristics(Long objectCharacteristicsId) {
        сharacteristicsRepository.deleteById(objectCharacteristicsId);
    }


    public List<ObjectCharacteristics> getAllObjectCharacteristics() {
        List<ObjectCharacteristics> objectCharacteristics = сharacteristicsRepository.findAll();
        return objectCharacteristics;
    }
}
