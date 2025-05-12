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

class Step3IntegrationTest {
    private PizzaService pizzaService;

    @BeforeEach
    void setUp() {
        MenuRepository menuRepo = mock(MenuRepository.class);
        PaymentRepository payRepo = new TestPaymentRepository();

        pizzaService = new PizzaService(menuRepo, payRepo);
    }

    @Test
    void testAddPaymentIntegration() {
        pizzaService.addPayment(1, PaymentType.Cash, 20.0);

        List<Payment> payments = pizzaService.getPayments();

        assertEquals(1, payments.size());

        Payment payment = payments.get(0);
        assertEquals(20.0, payment.getAmount(), 0.01);
        assertEquals(PaymentType.Cash, payment.getType());
    }


    @Test
    void testGetTotalAmountIntegration() {
        pizzaService.addPayment(1, PaymentType.Cash, 10.0);
        pizzaService.addPayment(2, PaymentType.Cash, 15.0);
        pizzaService.addPayment(3, PaymentType.Card, 25.0);

        List<Payment> payments = pizzaService.getPayments();

        double cashTotal = pizzaService.getTotalAmount(PaymentType.Cash);
        double cardTotal = pizzaService.getTotalAmount(PaymentType.Card);

        assertEquals(25.0, cashTotal, 0.01);
        assertEquals(25.0, cardTotal, 0.01);

        Payment firstPayment = payments.get(0);
        assertEquals(10.0, firstPayment.getAmount(), 0.01);
        assertEquals(PaymentType.Cash, firstPayment.getType());
        assertEquals(1, firstPayment.getTableNumber());

        Payment secondPayment = payments.get(1);
        assertEquals(15.0, secondPayment.getAmount(), 0.01);
        assertEquals(PaymentType.Cash, secondPayment.getType());
        assertEquals(2, secondPayment.getTableNumber());

        Payment thirdPayment = payments.get(2);
        assertEquals(25.0, thirdPayment.getAmount(), 0.01);
        assertEquals(PaymentType.Card, thirdPayment.getType());
        assertEquals(3, thirdPayment.getTableNumber());
    }

}