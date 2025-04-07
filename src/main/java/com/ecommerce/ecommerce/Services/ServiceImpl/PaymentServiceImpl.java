package com.ecommerce.ecommerce.Services.ServiceImpl;

import com.ecommerce.ecommerce.Models.Order;
import com.ecommerce.ecommerce.Models.Payment;
import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Repositories.OrderRepository;
import com.ecommerce.ecommerce.Repositories.PaymentRepository;
import com.ecommerce.ecommerce.Repositories.UserRepository;
import com.ecommerce.ecommerce.Services.PaymentService;
import com.ecommerce.ecommerce.Utils.TransactionUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final TransactionUtil transactionUtil;
    private final UserRepository userRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository, TransactionUtil transactionUtil, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.transactionUtil = transactionUtil;
        this.userRepository = userRepository;
    }

    @Override
    public String purchaseOrder(Integer orderId, Payment dto) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order id: " + orderId + " not found."));
        if(dto.getPaymentMethod() == null || dto.getPaymentMethod().isEmpty()){
            throw new RuntimeException("Invalid payment methods.");
        }
        Payment payment = Payment.builder()
                .order(order)
                .paymentMethod(dto.getPaymentMethod())
                .status(Payment.paymentStatus.COMPLETE)
                .transactionId(transactionUtil.GenerateTransactionID())
                .build();
        paymentRepository.save(payment);
        return "Payment for order " + orderId + " was successful. Transaction ID: " + payment.getTransactionId();
    }

    @Override
    public Payment getOwnPayment(Integer paymentId, Integer userId) {
        if(!userRepository.existsById(userId)){
            throw new RuntimeException("User with id: " + userId + " not found");
        }
        if(!paymentRepository.existsById(paymentId)){
            throw new RuntimeException("Payment with id: " + paymentId + " not found");
        }
        return paymentRepository.findUserPayment(userId);
    }

    @Override
    public Page<Payment> listPayment(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }
}
