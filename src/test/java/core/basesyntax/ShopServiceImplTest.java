package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.impl.ShopServiceImpl;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShopServiceImplTest {
    private static ShopService shopService;
    private static final Map<FruitTransaction.Operation, OperationHandler>
            operationHandler = new HashMap<>();

    @BeforeAll
    static void setUp() {
        operationHandler.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandler);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @Test
    void process_validTransactionList_IsOk() {
        assertDoesNotThrow(() -> shopService.process(List.of(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 5))));
    }

    @Test
    void process_transactionListIsNull_NotOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> shopService.process(null));
        assertTrue(exception.getMessage().contains("Transaction list is null"));
    }

    @Test
    void process_transactionListIsEmpty_NotOk() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> shopService.process(List.of()));
        assertTrue(exception.getMessage().contains("Transaction list is empty"));
    }

    @Test
    void process_transactionIsNull_NotOk() {
        List<FruitTransaction> test = new ArrayList<>();
        test.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana" , 5));
        test.add(null);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> shopService.process(test));
        assertTrue(exception.getMessage().contains("Provided transaction in transaction List is null"));
    }

}
