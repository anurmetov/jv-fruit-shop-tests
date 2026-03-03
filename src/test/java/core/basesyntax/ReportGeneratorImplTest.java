package core.basesyntax;

import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;
    private static final String EXPECTED_TITLE = "fruit,quantity" + System.lineSeparator();
    private static Map<String, Integer> exampleInput;

    @BeforeAll
    static void setUp(){
        reportGenerator = new ReportGeneratorImpl();
        exampleInput = new LinkedHashMap<>();
    }

    @AfterEach
    void afterEach() {
        exampleInput.clear();
    }

    @Test
    void generate_inputMapIsNull_NotOk() {
       RuntimeException exception = assertThrows(RuntimeException.class,
               () -> reportGenerator.getReport(null));
       assertTrue(exception.getMessage().contains("The fruit storage is null"));
    }

    @Test
    void generate_inputMapIsEmpty_Ok() {
        assertEquals(EXPECTED_TITLE, reportGenerator.getReport(new HashMap<>()));
    }

    @Test
    void generate_inputWithOneFruit_Ok() {
        exampleInput.put("apple", 5);
        String expected = EXPECTED_TITLE + "apple,5";
        assertEquals(expected, reportGenerator.getReport(exampleInput));
    }


    @Test
    void generate_inputWithTwoFruits_Ok() {
        exampleInput.put("apple", 5);
        exampleInput.put("banana", 15);
        String expected = EXPECTED_TITLE + "apple,5" + System.lineSeparator() + "banana,15";
        assertEquals(expected, reportGenerator.getReport(exampleInput));
    }

    @Test
    void generate_OutputNotNaturalOrder_NotOk() {
        exampleInput.put("apple", 5);
        exampleInput.put("banana", 15);
        String expectedFalse = EXPECTED_TITLE + "banana,15" + System.lineSeparator() + "apple,5";
        assertNotEquals(expectedFalse, reportGenerator.getReport(exampleInput));
    }

}
