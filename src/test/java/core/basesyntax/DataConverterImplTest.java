package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Converter;
import core.basesyntax.service.impl.DataConverterImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataConverterImplTest {
    private static Converter converter;
    private static List<FruitTransaction> exampleOutput;
    private static List<String> exampleInput;


    @BeforeAll
    static void setUp() {
        converter = new DataConverterImpl();
        exampleOutput = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50));
    }

    @BeforeEach
    void beforeEach() {
        exampleInput = List.of("type", "fruit", "quantity",
                "b", "banana", "20", "b", "apple", "100",
                "s", "banana", "100", "p", "banana", "13",
                "r", "apple", "10", "p", "apple", "20",
                "p", "banana", "5", "s", "banana", "50");
    }

    @Test
    void convert_validInput_Ok() {
        assertDoesNotThrow(() -> converter.convertToTransaction(exampleInput));
    }

    @Test
    void convert_equalsOutput_Ok() {
        assertEquals(exampleOutput, converter.convertToTransaction(exampleInput));
    }

    @Test
    void convert_emptyList_Ok() {
        exampleInput = List.of();
        List<FruitTransaction> expected = List.of();
        assertEquals(expected, converter.convertToTransaction(exampleInput));
    }

    @Test
    void convert_quantityIsNotNumber_NotOk() {
        exampleInput = List.of("type", "fruit", "quantity",
                "b", "banana", "ERROR", "b", "apple", "100",
                "s", "banana", "100", "p", "banana", "13",
                "r", "apple", "10", "p", "apple", "20",
                "p", "banana", "5", "s", "banana", "50");
        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> converter.convertToTransaction(exampleInput));
        assertTrue(exception.getMessage().contains("Input file contains invalid quantity value"));
    }

    @Test
    void convert_nullInput_notOk() {
        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> converter.convertToTransaction(null));
        assertTrue(exception.getMessage().contains("Input list cannot be null"));
    }

    @Test
    void convert_unknownOperationCode_notOk() {
        exampleInput = List.of("type", "fruit", "quantity", "x", "banana", "20");
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(exampleInput));
    }

    @Test
    void convert_incompleteData_notOk() {
        exampleInput = List.of("type", "fruit", "quantity", "b", "banana");
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(exampleInput));
        assertTrue(exception.getMessage().contains("Input list is incomplete"));
    }
}
