package serviceTest;

import model.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import service.QuestionService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @InjectMocks
    private QuestionService questionService;

    @Test
    void testReadFromFileSuccess() throws IOException, URISyntaxException {
        String mockData = "{\"questions\":[],\"answers\":[]}";

        try (MockedStatic<Paths> pathsMockedStatic = mockStatic(Paths.class);
             MockedStatic<Files> filesMockedStatic = mockStatic(Files.class)) {

            Path mockPath = mock(Path.class);
            pathsMockedStatic.when(() -> Paths.get(any(URI.class))).thenReturn(mockPath);

            Stream<String> mockStream = Stream.of(mockData);
            filesMockedStatic.when(() -> Files.lines(mockPath)).thenReturn(mockStream);

            Data data = questionService.readFromFile();
            assertNotNull(data);
            assertTrue(data.getQuestions().isEmpty());
            assertTrue(data.getAnswers().isEmpty());
        }
    }

    @Test
    void testReadFromFileFileNotFound() {
        try (MockedStatic<Paths> pathsMockedStatic = mockStatic(Paths.class)) {
            pathsMockedStatic.when(() -> Paths.get(any(URI.class))).thenThrow(new IOException("File not found"));

            assertThrows(IOException.class, () -> questionService.readFromFile());
        }
    }

    @Test
    void testReadFromFileInvalidData() throws IOException, URISyntaxException {
        String invalidData = "Invalid JSON";

        try (MockedStatic<Paths> pathsMockedStatic = mockStatic(Paths.class);
             MockedStatic<Files> filesMockedStatic = mockStatic(Files.class)) {

            Path mockPath = mock(Path.class);
            pathsMockedStatic.when(() -> Paths.get(any(URI.class))).thenReturn(mockPath);

            Stream<String> mockStream = Stream.of(invalidData);
            filesMockedStatic.when(() -> Files.lines(mockPath)).thenReturn(mockStream);

            assertThrows(IOException.class, () -> questionService.readFromFile());
        }
    }

    @Test
    void testReadFromFileURISyntaxException() {
        try (MockedStatic<Paths> pathsMockedStatic = mockStatic(Paths.class)) {
            pathsMockedStatic.when(() -> Paths.get(any(URI.class))).thenThrow(new URISyntaxException("data.json", "Invalid URI"));

            assertThrows(URISyntaxException.class, () -> questionService.readFromFile());
        }
    }
}
