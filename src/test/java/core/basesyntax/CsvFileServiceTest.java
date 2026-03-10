package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.CsvReaderImpl;
import core.basesyntax.service.impl.CsvWriterImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CsvFileServiceTest {
    private static FileReader fileReader;
    private static FileWriter fileWriter;
    private static final String DEFAULT_OUTPUT_PATH = "src/test/resources/output.csv";

    @BeforeAll
    static void beforeAll() {
        fileReader = new CsvReaderImpl();
        fileWriter = new CsvWriterImpl();
    }

    @AfterEach
    void afterEach() {
        boolean deleted = new File(DEFAULT_OUTPUT_PATH).delete();
        if (deleted) {
            System.out.println("File could not be deleted: " + DEFAULT_OUTPUT_PATH);
        }
    }

    @Test
    void read_ExampleFile_Ok() {
        List<String> expected = List.of(
                "type", "fruit", "quantity", "b", "banana", "20","b","apple", "100", "s",
                "banana", "100", "p", "banana", "13", "r", "apple", "10", "p", "apple", "20",
                "p","banana", "5", "s", "banana", "50");
        assertEquals(expected, fileReader.readFile("src/test/resources/input_data.csv"));
    }

    @Test
    void read_EmptyFile_Ok() {
        List<String> expected = List.of();
        assertEquals(expected, fileReader.readFile("src/test/resources/empty_data.csv"));
    }

    @Test
    void read_NullFile_NotOk() {
        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> fileReader.readFile(null));
        assertEquals("Provided file name is null", exception.getMessage());
    }

    @Test
    void read_NotExistingFile_NotOk() {
        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> fileReader.readFile("null.csv"));
        assertTrue(exception.getMessage().contains("File was not found"));
    }

    @Test
    void write_fromValidString_Ok() {
        assertDoesNotThrow(() ->
                fileWriter.writeTo("Hello, world!", DEFAULT_OUTPUT_PATH));
    }

    @Test
    void write_inputEquals_Ok() throws IOException {
        assertDoesNotThrow(() ->
                fileWriter.writeTo("Hello, world!", DEFAULT_OUTPUT_PATH));

        String content = Files.readString(Path.of(DEFAULT_OUTPUT_PATH));

        assertEquals("Hello, world!", content);
    }

    @Test
    void write_toValidName_Ok() {
        assertDoesNotThrow(() ->
                fileWriter.writeTo("Hello, world!", DEFAULT_OUTPUT_PATH));
    }

    @Test
    void write_fromNullString_NotOk() {
        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> fileWriter.writeTo(null, "example_output.csv"));
        assertTrue(exception.getMessage()
                .contains("String from which should be written from is empty"));
    }

    @Test
    void write_fromEmptyString_NotOk() {
        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> fileWriter.writeTo("", "example_output.csv"));
        assertTrue(exception.getMessage()
                .contains("String from which should be written from is empty"));
    }

    @Test
    void write_toNullFilePath_NotOk() {
        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> fileWriter.writeTo("Hello, world!", null));
        assertTrue(exception.getMessage()
                .contains("The File Path that should be written to is empty"));
    }

    @Test
    void write_toEmptyFilePath_NotOk() {
        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> fileWriter.writeTo("Hello, world!", ""));
        assertTrue(exception.getMessage()
                .contains("The File Path that should be written to is empty"));
    }

    @Test
    void write_toNotExistingFolder_NotOk() {
        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> fileWriter.writeTo("Hello, world!",
                        "src/test/example/example_output.csv"));
        assertTrue(exception.getMessage().contains("Could not create or write to file"));
    }

    @Test
    void write_inputDoesNotEqual_NotOk() throws IOException {
        assertDoesNotThrow(() ->
                fileWriter.writeTo("Hello, world!", DEFAULT_OUTPUT_PATH));

        String content = Files.readString(Path.of(DEFAULT_OUTPUT_PATH));
        assertNotEquals("Hello world!",content);
    }
}
