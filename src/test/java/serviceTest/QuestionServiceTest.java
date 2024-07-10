package serviceTest;

import model.Data;
import org.junit.jupiter.api.Test;
import service.QuestionService;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class QuestionServiceTest {
    @Test
    public void testReadFromFile() throws IOException, URISyntaxException {
        QuestionService questionService = new QuestionService();

        Data data = questionService.readFromFile();
        assertNotNull(data);
    }
}
