package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void process(FruitTransaction fruitTransaction) {
        canBeProcessed(fruitTransaction);
        int currentQuantity = Storage.getAll().getOrDefault(fruitTransaction.getFruit(), 0);
        int shouldQuantity = currentQuantity - fruitTransaction.getQuantity();

        if (shouldQuantity < 0) {
            throw new RuntimeException("Purchase operation failed. "
                    + "Not enough fruit in storage to sell.");
        }

        Storage.put(fruitTransaction.getFruit(), shouldQuantity);
    }
}
