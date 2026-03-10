package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OperationalHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void setUp() {
        operationHandler = new BalanceOperation();
    }

    @Test
    void validateTransaction_fruitTransactionValidData_Ok() {
        assertDoesNotThrow(() -> operationHandler.canBeProcessed(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20)));
    }

    @Test
    void validateTransaction_fruitTransactionIsNull_NotOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> operationHandler.canBeProcessed(null));
        assertTrue(exception.getMessage().contains("Transaction cannot be null"));
    }

    @Test
    void validateTransaction_getFruitIsNull_NotOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, null, 5);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> operationHandler.canBeProcessed(fruitTransaction));
        assertTrue(exception.getMessage().contains("Fruit cannot be null"));
    }

    @Test
    void validateTransaction_getFruitIsEmpty_NotOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "", 5);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> operationHandler.canBeProcessed(fruitTransaction));
        assertTrue(exception.getMessage().contains("Fruit cannot be empty"));
    }

    @Test
    void validateTransaction_quantityIsLowerThanZero_NotOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", -5);
        RuntimeException exception = assertThrows(IllegalArgumentException.class,
                () -> operationHandler.canBeProcessed(fruitTransaction));
        assertTrue(exception.getMessage().contains("Quantity cannot be negative"));
    }
}
