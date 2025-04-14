package pizzashop.model;

import com.sun.javafx.tk.Toolkit;
import jdk.jshell.spi.ExecutionControl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    private PizzaService service;
    private TestPaymentRepository testPaymentRepo;

    @BeforeEach
    void setUp() {
        testPaymentRepo = new TestPaymentRepository();
        service = new PizzaService(new DummyMenuRepository(), testPaymentRepo);
    }

    @Test
    void testAddPaymentWithCash() {
        int table = 2;
        double amount = 30.0;
        PaymentType type = PaymentType.Cash;

        service.addPayment(table, type, amount);

        List<Payment> payments = testPaymentRepo.getAll();
        assertEquals(1, payments.size());

        Payment added = payments.get(0);
        assertEquals(table, added.getTableNumber());
        assertEquals(type, added.getType());
        assertEquals(amount, added.getAmount());
    }

    @Test
    void testAddPaymentWithInvalidType() {
        int table = 3;
        double amount = 40.0;

        assertThrows(NullPointerException.class, () -> {
            service.addPayment(table, null, amount);
        });
    }

    @Test
    void testValidPaymentWithValidAmounts() {

        int table = 1;
        double amount = 1.0;
        PaymentType type = PaymentType.Cash;

        service.addPayment(table, type, amount);

        List<Payment> payments = testPaymentRepo.getAll();
        assertEquals(1, payments.size());
        Payment p = payments.get(0);
        assertEquals(table, p.getTableNumber());
        assertEquals(amount, p.getAmount());
        assertEquals(type, p.getType());
    }

    @Test
    void testInvalidPaymentWithInvalidAmount() {
        int table = 1;
        double amount = 0.0;
        PaymentType type = PaymentType.Cash;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.addPayment(table, type, amount);
        });

        assertEquals("Amount must be greater than 0", exception.getMessage());
    }

    static class TestPaymentRepository extends PaymentRepository {
        private final List<Payment> testPayments = new ArrayList<>();

        @Override
        public void add(Payment payment) {
            testPayments.add(payment);
        }

        @Override
        public List<Payment> getAll() {
            return testPayments;
        }

        @Override
        public void writeAll(){
        }
    }

    static class DummyMenuRepository extends MenuRepository {
        @Override
        public List<pizzashop.model.MenuDataModel> getMenu() {
            return new ArrayList<>();
        }
    }
}