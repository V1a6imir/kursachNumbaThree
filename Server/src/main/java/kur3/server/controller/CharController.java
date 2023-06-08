package kur3.server.controller;

import kur3.server.entity.ObjectCharacteristics;
import kur3.server.service.CharacteristicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CharController {

    private final CharacteristicsService charService;

    @Autowired
    public CharController(CharacteristicsService charService) {
        this.charService = charService;
    }

    @PostMapping("/add-char")
    public void addChar(@RequestBody ObjectCharacteristics objectCharacteristics) {
        charService.addObjectCharacteristics(objectCharacteristics);
    }

    @DeleteMapping("/{id}")
    public void deleteChar(@PathVariable Long id) {
        charService.removeObjectCharacteristics(id);
    }

    @GetMapping("/data")
    public List<ObjectCharacteristics> getCharData() {
        return charService.getAllObjectCharacteristics();
    }
}
