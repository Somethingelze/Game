package service;

import model.Data;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuestionService {

    public Data readFromFile() throws IOException, URISyntaxException {
        Path path = Paths.get(getClass().getClassLoader()
                  .getResource("data.json").toURI());

        Stream<String> lines = Files.lines(path);
        String data = lines.collect(Collectors.joining("\n"));
        lines.close();

        ObjectMapper om = new ObjectMapper();
        return om.readValue(data, Data.class);
    }
}
