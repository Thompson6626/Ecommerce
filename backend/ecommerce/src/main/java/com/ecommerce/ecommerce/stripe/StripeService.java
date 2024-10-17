package com.ecommerce.ecommerce.stripe;

import com.ecommerce.ecommerce.order.Order;
import com.ecommerce.ecommerce.utils.Utils;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem;
import com.stripe.param.checkout.SessionCreateParams.Mode;
import com.stripe.param.checkout.SessionCreateParams.PaymentMethodType;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData.ProductData;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.List;

@Service
public class StripeService {

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    public PaymentResponse createPaymentIntent(
        Order order
        ) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        // 
        var params = SessionCreateParams.builder()
                        .addPaymentMethodType(PaymentMethodType.CARD)
                        .setMode(Mode.PAYMENT)
                        .setSuccessUrl("urlToSuccess")
                        .setCancelUrl("urlToFail")
                        .addAllLineItem(generateLineItems(order))
                        .build();
        
        var session = Session.create(params);

        return new PaymentResponse(session.getUrl());  
    }

    private List<LineItem> generateLineItems(Order order) {
        return order.getOrderItems().stream()
            .map(oi -> 
                 LineItem.builder()
                .setQuantity(oi.getQuantity().longValue())  
                .setPriceData(
                    PriceData.builder()
                        .setCurrency("usd")
                        .setUnitAmount(Utils.dollarToPennies(oi.getProduct().getPrice()))
                        .setProductData(
                            ProductData.builder()
                                .setName(oi.getProduct().getName())
                                .build()
                        )
                        .build()
                )
                .build()
            )
            .collect(Collectors.toList());
    }

}
