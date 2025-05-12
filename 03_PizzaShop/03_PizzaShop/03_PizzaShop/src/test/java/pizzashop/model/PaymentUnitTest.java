package pizzashop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaymentUnitTest {

    @Test
    void testConstructorAndGetters() {
        Payment payment = new Payment(3, PaymentType.Card, 50.0);
        assertEquals(3, payment.getTableNumber());
        assertEquals(PaymentType.Card, payment.getType());
        assertEquals(50.0, payment.getAmount());
    }

    @Test
    void testToString() {
        Payment payment = new Payment(1, PaymentType.Cash, 30.0);
        assertEquals("1,Cash,30.0", payment.toString());
    }
}
