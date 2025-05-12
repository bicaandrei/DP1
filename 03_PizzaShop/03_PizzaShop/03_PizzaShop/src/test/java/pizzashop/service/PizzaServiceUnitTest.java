package pizzashop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PizzaServiceUnitTest {

    private PaymentRepository mockPaymentRepo;
    private MenuRepository mockMenuRepo;
    private PizzaService pizzaService;

    @BeforeEach
    void setUp() {
        mockPaymentRepo = mock(PaymentRepository.class);
        mockMenuRepo = mock(MenuRepository.class);
        pizzaService = new PizzaService(mockMenuRepo, mockPaymentRepo);
    }

    @Test
    void testAddPaymentSuccess() {
        pizzaService.addPayment(2, PaymentType.Card, 60.0);
        verify(mockPaymentRepo, times(1)).add(any(Payment.class));
    }

    @Test
    void testGetTotalAmount() {
        List<Payment> mockPayments = Arrays.asList(
                new Payment(1, PaymentType.Cash, 10.0),
                new Payment(2, PaymentType.Cash, 20.0)
        );
        when(mockPaymentRepo.getAll()).thenReturn(mockPayments);
        double total = pizzaService.getTotalAmount(PaymentType.Cash);
        assertEquals(30.0, total);
    }
}
