package kur3.server.controller;

import kur3.server.entity.ObjectData;
import kur3.server.service.ParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parse-data")
public class ParseDataController {

    private final ParseService parseService;

    @Autowired
    public ParseDataController(ParseService parseService) {
        this.parseService = parseService;
    }

    @GetMapping
    public List<ObjectData> parseData() {
        return parseService.parseAndGetData();
    }
}

