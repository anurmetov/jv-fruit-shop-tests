package core.basesyntax.model;

import java.util.Objects;

public class FruitTransaction {
    private static final String BALANCE_CODE = "b";
    private static final String SUPPLY_CODE = "s";
    private static final String PURCHASE_CODE = "p";
    private static final String RETURN_CODE = "r";

    private final Operation operation;
    private final String fruit;
    private final int quantity;

    public FruitTransaction(Operation operation, String fruit, int quantity) {
        if (operation == null) {
            throw new RuntimeException("Operation is null");
        }

        if (fruit == null) {
            throw new RuntimeException("Fruit name is null");
        }

        if (fruit.isEmpty()) {
            throw new RuntimeException("Fruit is empty");
        }

        if (quantity < 0) {
            throw new RuntimeException("Quantity can not be lower than zero: " + quantity);
        }

        this.operation = operation;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    public enum Operation {
        BALANCE(BALANCE_CODE),
        SUPPLY(SUPPLY_CODE),
        PURCHASE(PURCHASE_CODE),
        RETURN(RETURN_CODE);

        private final String code;

        Operation(String code) {
            this.code = code;
        }

        public static Operation fromCode(String code) {
            for (Operation op : Operation.values()) {
                if (op.code.equals(code)) {
                    return op;
                }
            }
            throw new IllegalArgumentException("Unknown operation: " + code);
        }

    }

    public String getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public Operation getOperation() {
        return operation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FruitTransaction)) return false;
        FruitTransaction that = (FruitTransaction) o;
        return quantity == that.quantity &&
                operation == that.operation &&
                Objects.equals(fruit, that.fruit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, fruit, quantity);
    }

    @Override
    public String toString() {
        return "\n" + "FruitTransaction{" +
                "operation=" + operation +
                ", fruit='" + fruit + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
