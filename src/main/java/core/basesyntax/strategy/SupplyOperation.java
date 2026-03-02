package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class SupplyOperation implements OperationHandler {
    @Override
    public void process(FruitTransaction fruitTransaction) {
        canBeProcessed(fruitTransaction);
        int currentQuantity = Storage.getAll().getOrDefault(fruitTransaction.getFruit(), 0);
        int shouldQuantity = currentQuantity + fruitTransaction.getQuantity();
        Storage.put(fruitTransaction.getFruit(), shouldQuantity);
    }
}
