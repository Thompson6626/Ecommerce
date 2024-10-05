package com.ecommerce.ecommerce.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


// TODO: 05/10/2024
@Service
public class StripeService {

    @Value("${STRIPE_SECRET_KEY}")
    private String API_KEY;

    public StripeService(){
        Stripe.apiKey = API_KEY;
    }

    public PaymentIntent createPaymentIntent(long amount,String currency) throws StripeException {
        if (currency == null){
            currency = "usd";
        }
        var params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency(currency)
                .build();
        var intent = PaymentIntent.create(params);
        intent.getClientSecret();

        return intent;
    }
}
