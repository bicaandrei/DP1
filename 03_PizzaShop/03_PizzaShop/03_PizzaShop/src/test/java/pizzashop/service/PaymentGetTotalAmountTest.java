package pizzashop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentGetTotalAmountTest {

    private PizzaService service;
    private TestPaymentRepository testPaymentRepo;

    @BeforeEach
    void setUp() {
        testPaymentRepo = new TestPaymentRepository();
        service = new PizzaService(new DummyMenuRepository(), testPaymentRepo);
    }

    @Test
    void testGetTotalAmountWithNullList() {
        PizzaService nullService = new PizzaService(new DummyMenuRepository(), new PaymentRepository() {
            @Override public void add(Payment payment) {}
            @Override public List<Payment> getAll() { return null; }
            @Override public void writeAll() {}
        });

        double total = nullService.getTotalAmount(PaymentType.Cash);
        assertEquals(0.0, total);
    }

    @Test
    void testGetTotalAmountWithEmptyList() {
        double total = service.getTotalAmount(PaymentType.Cash);
        assertEquals(0.0, total);
    }

    @Test
    void testGetTotalAmountWithMatchingType() {
        service.addPayment(1, PaymentType.Cash, 10.0);
        service.addPayment(2, PaymentType.Cash, 5.0);
        service.addPayment(3, PaymentType.Card, 7.0);

        double total = service.getTotalAmount(PaymentType.Cash);
        assertEquals(15.0, total);
    }

    @Test
    void testGetTotalAmountWithNoMatchingType() {
        service.addPayment(1, PaymentType.Card, 10.0);
        service.addPayment(2, PaymentType.Card, 20.0);

        double total = service.getTotalAmount(PaymentType.Cash);
        assertEquals(0.0, total);
    }

    // Reuse same test repo as in other tests
    static class TestPaymentRepository extends PaymentRepository {
        private final List<Payment> testPayments = new ArrayList<>();

        @Override public void add(Payment payment) {
            testPayments.add(payment);
        }

        @Override public List<Payment> getAll() {
            return testPayments;
        }

        @Override public void writeAll() {}
    }

    static class DummyMenuRepository extends pizzashop.repository.MenuRepository {
        @Override
        public List<pizzashop.model.MenuDataModel> getMenu() {
            return new ArrayList<>();
        }
    }
}
