package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationalHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void setUp() {
        operationHandler = new BalanceOperation();
    }

    @Test
    void validateTransaction_fruitTransactionValidData_Ok() {
        assertDoesNotThrow(() -> operationHandler.validateTransaction(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20)));
    }

    @Test
    void validateTransaction_fruitTransactionIsNull_NotOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> operationHandler.validateTransaction(null));
        assertTrue(exception.getMessage().contains("Transaction cannot be null"));
    }

    @Test
    void validateTransaction_getFruitIsNull_NotOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, null, 5);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> operationHandler.validateTransaction(fruitTransaction));
        assertTrue(exception.getMessage().contains("Fruit cannot be null"));
    }

    @Test
    void validateTransaction_getFruitIsEmpty_NotOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "", 5);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> operationHandler.validateTransaction(fruitTransaction));
        assertTrue(exception.getMessage().contains("Fruit cannot be empty"));
    }

    @Test
    void validateTransaction_quantityIsLowerThanZero_NotOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", -5);
        RuntimeException exception = assertThrows(IllegalArgumentException.class,
                () -> operationHandler.validateTransaction(fruitTransaction));
        assertTrue(exception.getMessage().contains("Quantity cannot be negative"));
    }
}
