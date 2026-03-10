package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private final OperationStrategy operationStrategy;

    public ShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        if (transactions == null) {
            throw new RuntimeException("Transaction list is null");
        }
        if (transactions.isEmpty()) {
            throw new RuntimeException("Transaction list is empty");
        }
        for (FruitTransaction transaction : transactions) {
            if (transaction == null) {
                throw new RuntimeException("Provided transaction in transaction List is null");
            }
            OperationHandler handler = operationStrategy.getHandler(transaction.getOperation());
            if (handler == null) {
                throw new RuntimeException("No handler is found for the transaction's operation.");
            }
            handler.process(transaction);
        }
    }
}
