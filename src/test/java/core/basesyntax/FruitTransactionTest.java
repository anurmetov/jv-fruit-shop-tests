package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FruitTransactionTest {
    private static FruitTransaction fruitTransaction;

    @Test
    void assign_validParameters_Ok() {
        assertNotNull(fruitTransaction
                = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20));
    }

    @Test
    void assign_nullOperation_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fruitTransaction = new FruitTransaction(null, "apple", 20));
        assertTrue(exception.getMessage().contains("Operation is null"));
    }

    @Test
    void assign_nullFruit_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fruitTransaction
                        = new FruitTransaction(FruitTransaction.Operation.BALANCE, null, 20));
        assertTrue(exception.getMessage().contains("Fruit name is null"));
    }

    @Test
    void assign_emptyFruitName_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fruitTransaction
                        = new FruitTransaction(FruitTransaction.Operation.BALANCE, "", 20));
        assertTrue(exception.getMessage().contains("Fruit is empty"));
    }

    @Test
    void assign_negativeQuantity_notOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fruitTransaction
                        = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", -1));
        assertTrue(exception.getMessage().contains("Quantity can not be lower than zero"));
    }

    @Test
    void get_fromOperationCodeValidInput_Ok() {
        List<String> validCodes = List.of("b", "s", "p", "r");
        for (String code : validCodes) {
            assertDoesNotThrow(() -> FruitTransaction.Operation.fromCode(code));
        }
    }

    @Test
    void get_fromOperationCodeNotValidCode_NotOk() {
        RuntimeException exception
                = assertThrows(RuntimeException.class, () -> FruitTransaction.Operation.fromCode("x"));
        assertTrue(exception.getMessage().contains("Unknown operation"));
    }

    @Test
    void get_getFruitEquals_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 5);
        String expected = "banana";
        assertEquals(expected, fruitTransaction.getFruit());
    }

    @Test
    void get_getQuantityEquals_Ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 5);
        int expected = 5;
        assertEquals(expected, fruitTransaction.getQuantity());
    }

    @Test
    void get_getOperationEqualsOk() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 5);
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        assertEquals(expected, fruitTransaction.getOperation());
    }

}
