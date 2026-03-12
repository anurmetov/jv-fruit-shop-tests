package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.ReturnOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private static final OperationHandler operationHandler = new ReturnOperation();
    private static FruitTransaction testTransaction;

    @AfterEach
    void afterEach() {
        Storage.clear();
    }

    @Test
    void process_validTransaction_Ok() {
        Storage.put("banana", 50);
        testTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 20);
        operationHandler.process(testTransaction);
        assertEquals(70, Storage.getAll().get("banana"));
    }

    @Test
    void process_validTransactionPlusFunctionResultNull_Ok() {
        Storage.put("banana", 50);
        testTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 0);
        operationHandler.process(testTransaction);
        assertEquals(50, Storage.getAll().get("banana"));
    }

    @Test
    void process_updatedQuantityLowerThanZero_NotOk() {
        Storage.put("banana", 3);
        testTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", -5);
        assertThrows(RuntimeException.class,
                        () -> operationHandler.process(testTransaction));
    }
}
