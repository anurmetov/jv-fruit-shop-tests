package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Converter;
import core.basesyntax.service.impl.DataConverterImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataConverterImplTest {
    private static Converter converter;
    private static List<FruitTransaction> exampleOutput;
    private static List<String> exampleInput;


    @BeforeAll
    static void setUp() {
        converter = new DataConverterImpl();
        exampleInput = List.of("type", "fruit", "quantity",
                "b", "banana", "20", "b", "apple", "100",
                "s", "banana", "100", "p", "banana", "13",
                "r", "apple", "10", "p", "apple", "20",
                "p", "banana", "5", "s", "banana", "50");
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

    @Test
    void convert_equalsOutput_Ok() {
        assertEquals(exampleOutput, converter.convertToTransaction(exampleInput));
    }

    @Test
    void convert_validInput_Ok() {
        assertDoesNotThrow(() -> converter.convertToTransaction(exampleInput));
    }

    @Test
    void convert_nullInput_notOk() {
        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> converter.convertToTransaction(null));
        assertEquals("Input list cannot be null", exception.getMessage());
    }
}
