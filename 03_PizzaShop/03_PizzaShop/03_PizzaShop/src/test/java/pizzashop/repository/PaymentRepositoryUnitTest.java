package pizzashop.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryUnitTest {

    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
    }

    @Test
    void testAddAndGetAll() {
        Payment payment = new Payment(5, PaymentType.Cash, 100.0);
        paymentRepository.add(payment);
        List<Payment> allPayments = paymentRepository.getAll();
        assertTrue(allPayments.contains(payment));
    }

    @Test
    void testGetAllNotNull() {
        assertNotNull(paymentRepository.getAll());
    }
}
