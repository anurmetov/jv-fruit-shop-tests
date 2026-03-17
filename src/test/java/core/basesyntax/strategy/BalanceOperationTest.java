package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private static final OperationHandler operationHandler = new BalanceOperation();
    private static FruitTransaction testTransaction;

    @AfterEach
    void afterEach() {
        Storage.clear();
    }

    @Test
    void process_validTransaction_Ok() {
        testTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20);
        assertDoesNotThrow(() -> operationHandler.process(testTransaction));
        assertEquals(20, Storage.getAll().get("banana"));
    }

    @Test
    void process_validTransactionFunctionResultNull_Ok() {
        testTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 0);
        operationHandler.process(testTransaction);
        assertEquals(0, Storage.getAll().get("banana"));
    }

    @Test
    void process_QuantityLowerThanZero_NotOk() {
        testTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", -5);
        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> operationHandler.process(testTransaction));
        assertTrue(exception.getMessage().contains("Quantity cannot be negative"));
    }

    @Test
    void process_fruitAlreadyExists_Ok() {
        Storage.put("banana", 50);
        testTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 100);
        operationHandler.process(testTransaction);
        assertEquals(100, Storage.getAll().get("banana"));

    }
}
