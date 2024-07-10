package service;

import model.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    QuestionService mockQuestionService;

    @Test
    public void testReadFromFile() throws IOException, URISyntaxException {
        QuestionService questionService = new QuestionService();

            Data data = questionService.readFromFile();
            assertNotNull(data);
    }

    @Test
public void test()  {


    }
    }
