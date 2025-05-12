package pizzashop.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class Step2IntegrationTest {
    private PizzaService pizzaService;

    @BeforeEach
    void setUp() {
        MenuRepository menuRepo = mock(MenuRepository.class);
        PaymentRepository payRepo = new TestPaymentRepository();

        pizzaService = new PizzaService(menuRepo, payRepo);
    }

    @Test
    void testAddPaymentAndTotalAmount() {
        pizzaService.addPayment(1, PaymentType.Cash, 10.0);
        pizzaService.addPayment(2, PaymentType.Cash, 15.0);
        pizzaService.addPayment(3, PaymentType.Card, 25.0);

        assertEquals(25.0, pizzaService.getTotalAmount(PaymentType.Cash), 0.01);
        assertEquals(25.0, pizzaService.getTotalAmount(PaymentType.Card), 0.01);
    }

    @Test
    void testAddPaymentAndGetPayments() {
        pizzaService.addPayment(1, PaymentType.Cash, 10.0);
        pizzaService.addPayment(2, PaymentType.Cash, 15.0);
        pizzaService.addPayment(3, PaymentType.Card, 25.0);

        List<Payment> payments = pizzaService.getPayments();

        assertEquals(3, payments.size());

        assertEquals(10.0, payments.get(0).getAmount(), 0.01);
        assertEquals(PaymentType.Cash, payments.get(0).getType());


        assertEquals(15.0, payments.get(1).getAmount(), 0.01);
        assertEquals(PaymentType.Cash, payments.get(1).getType());

        assertEquals(25.0, payments.get(2).getAmount(), 0.01);
        assertEquals(PaymentType.Card, payments.get(2).getType());
    }

}


class TestPaymentRepository extends PaymentRepository {
    private final List<Payment> payments = new ArrayList<>();

    public TestPaymentRepository() {
    }

    @Override
    public List<Payment> getAll() {
        return payments;
    }

    @Override
    public void add(Payment payment) {
        payments.add(payment);
    }

    @Override
    public void writeAll() {
        // Evităm scrierea pe disc
    }
}
