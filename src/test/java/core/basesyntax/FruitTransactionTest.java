package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    private static FruitTransaction fruitTransaction;

    @Test
    void assign_validParameters_Ok() {
        assertNotNull(fruitTransaction
                = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 20));
    }

    @Test
    void get_fromOperationCodeValidInput_Ok() {
        List<String> validCodes = List.of("b", "s", "p", "r");
        for (String code : validCodes) {
            assertDoesNotThrow(() -> FruitTransaction.Operation.fromCode(code));
        }
    }

    @Test
    void get_fromOperationCodeNotValidCode_NotOk() {
        RuntimeException exception
                = assertThrows(RuntimeException.class,
                    () -> FruitTransaction.Operation.fromCode("x"));
        assertTrue(exception.getMessage().contains("Unknown operation"));
    }


}
