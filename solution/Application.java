package solution;

import se.itello.example.payments.*;
import java.math.BigDecimal;
import java.util.Date;

public class Application {

  public static void main(String[] args) {

    PaymentReceiver receiver = new PaymentReceiver() {
      @Override
      public void startPaymentBundle(String accountNumber, Date paymentDate, String currency) {
        System.out.println("From account: " + accountNumber + ". Payment done on: " + paymentDate + ". the currency: " + currency);
      }
      @Override
      public void payment(BigDecimal amount, String reference) {
        System.out.println("payment amount: " + amount  + ". Reference: " + reference);
      }
      @Override
      public void endPaymentBundle() {
        System.out.println("Done processing payments");

      }
    };
    PaymentHandler handler = new PaymentHandler(receiver);
    handler.executePayments("/Users/yaldarhapak/Programmeringsuppgift/Inbetalningar/Exempelfil_betalningsservice.txt");
  }
}
