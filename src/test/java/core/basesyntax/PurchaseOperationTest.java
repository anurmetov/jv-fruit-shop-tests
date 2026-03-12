package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private static final OperationHandler operationHandler = new PurchaseOperation();
    private static FruitTransaction testTransaction;

    @AfterEach
     void afterEach() {
        Storage.clear();
    }

    @Test
    void process_validTransaction_Ok() {
        Storage.put("banana", 50);
        testTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20);
        assertDoesNotThrow(() -> operationHandler.process(testTransaction));
        assertEquals(30, Storage.getAll().get("banana"));
    }

    @Test
    void process_validTransactionMinusFunctionResultNull_Ok() {
        Storage.put("banana", 50);
        testTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 50);
        operationHandler.process(testTransaction);
        assertEquals(0, Storage.getAll().get("banana"));
    }

    @Test
    void process_updatedQuantityLowerThanZero_NotOk() {
        Storage.put("banana", 3);
        testTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 5);
        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> operationHandler.process(testTransaction));
        assertTrue(exception.getMessage().contains("Not enough fruit in storage to sell"));
    }
}
