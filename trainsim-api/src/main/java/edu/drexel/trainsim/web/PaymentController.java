package edu.drexel.trainsim.web;

import com.google.inject.Inject;
import edu.drexel.trainsim.payment.models.PaymentRequest;
import edu.drexel.trainsim.payment.models.PaymentResponse;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class PaymentController implements Controller
{

    @Inject
    public PaymentController() {
    }

    public void bindRoutes(Javalin app) {
        app.post("/api/stripe/payment", this::makePayment);
    }

    private void makePayment(Context ctx) {
        try {
            PaymentRequest request = ctx.bodyAsClass(PaymentRequest.class);
            if (!request.getCreditCardInfo().validate()) {
                ctx.json(new PaymentResponse(PaymentResponse.Status.CREDIT_CARD_VALIDATION_FAILED));
            } else {
                ctx.json(new PaymentResponse(PaymentResponse.Status.PAYMENT_SUCCEED));
            }
        } catch (Exception e) {
            ctx.status(400);
        }
    }
}
