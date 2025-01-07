import com.stripe.Stripe;
import com.stripe.model.PaymentMethod;
import com.stripe.exception.StripeException;

public String getPaymentMethodIdForUser(String customerId) throws StripeException {
    Stripe.apiKey = "your_secret_key"; // 秘密鍵を設定

    // カスタマーに紐づく支払い方法を取得
    PaymentMethod paymentMethod = PaymentMethod.list(
            Map.of("customer", customerId, "type", "card")
    ).getData().get(0); // 仮に最初の支払い方法を取得

    return paymentMethod.getId();
}
