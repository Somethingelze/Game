package service;

import model.Data;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class QuestionService {

    public Data readFromFile() throws IOException, URISyntaxException {
        Path path = Paths.get(getClass().getClassLoader()
                .getResource("data.json").toURI());

        String data = Files.readString(path);

        ObjectMapper om = new ObjectMapper();
        return om.readValue(data, Data.class);
    }
}
