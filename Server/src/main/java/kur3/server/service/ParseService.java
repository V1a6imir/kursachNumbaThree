package kur3.server.service;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import kur3.server.entity.ObjectData;
import kur3.server.repository.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ParseService {

    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    public ParseService(ObjectRepository objectRepository) {
        this.objectRepository = objectRepository;
    }

    private String extractText(String text) {
        Pattern pattern = Pattern.compile("«([^»\"]*(?:»|\\bОтветы\\b|$))");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    public List<ObjectData> parseAndGetData() {
        List<ObjectData> data = new ArrayList<>();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Links.txt"))) {

            var document = Jsoup.connect("https://fundsolovki.ru/%d0%b7%d0%b0%d0%ba%d1%83%d0%bf%d0%ba%d0%b8/%d0%bf%d0%b5%d1%80%d0%b5%d1%87%d0%b5%d0%bd%d1%8c-%d0%b7%d0%b0%d0%ba%d1%83%d0%bf%d0%be%d0%ba/")
                    .get();
            var links = document.select("li:contains(на право заключения договора на проведение ремонтно-реставрационных работ на объекте культурного наследия)");
            for (Element link : links) {
                String tag = link.text();
                System.out.println(tag);

                // Извлечение текста внутри кавычек
                String extractedText = extractText(tag);
                ObjectData objectData = objectRepository.findByNameIgnoreCase(extractedText);

                // Проверка на наличие значения в базе данных
                if (objectData == null) {
                    writer.write(extractedText + "\n");


                    // Добавление в таблицу MySQL
                    objectData = new ObjectData(null, extractedText);
                    objectRepository.save(objectData);

                    objectData = objectRepository.findByNameIgnoreCase(extractedText);
                }

                // Добавление извлеченного текста в список данных
                data.add(objectData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}

