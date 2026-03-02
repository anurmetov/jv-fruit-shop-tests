package core.basesyntax;

import core.basesyntax.service.FileReader;
import core.basesyntax.service.FileWriter;
import core.basesyntax.service.impl.CsvReaderImpl;
import core.basesyntax.service.impl.CsvWriterImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CsvFileServiceTest {
    private static FileReader fileReader;
    private static FileWriter fileWriter;

    @BeforeAll
    static void beforeAll() {
        fileReader = new CsvReaderImpl();
        fileWriter = new CsvWriterImpl();
    }

    @Test
    void read_ExampleFile_Ok() {
        List<String> expected = List.of(
                "type", "fruit", "quantity", "b", "banana", "20","b" , "apple", "100", "s",
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

}
