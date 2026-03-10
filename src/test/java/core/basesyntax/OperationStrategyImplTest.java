package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlers;


    @BeforeAll
    static void setUp() {
        operationHandlers = new HashMap<>();
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void getHandler_validOperation_Ok() {
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        assertEquals(operationHandlers.get(FruitTransaction.Operation.BALANCE),
                operationStrategy.getHandler(FruitTransaction.Operation.BALANCE));
    }


    @Test
    void getHandler_operationIsNull_NotOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> operationStrategy.getHandler(null));
        assertTrue(exception.getMessage().contains("Operation is null"));
    }

    @AfterEach
    void afterEach() {
        operationHandlers.clear();
    }

}
