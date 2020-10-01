package solution;

import se.itello.example.payments.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PaymentHandler {
  public PaymentReceiver paymentReceiver;
  String defaultCurrency = "SEK";

  public PaymentHandler(PaymentReceiver paymentReceiver) {
    this.paymentReceiver = paymentReceiver;
  }

  /**
   * Reads a payment bundle and calls the methods of the PaymentReceiver interface with the relevant data
   * @param filePath absolute path of an inbetalningTänst or Betalningservice file.
   */

  public void executePayments(String filePath) {
    PaymentBundle bundle = new PaymentBundle();

    if (filePath.endsWith("_inbetalningstjansten.txt")) {
      readSetInbetalning(filePath, bundle);
    } else if (filePath.endsWith("_betalningsservice.txt")) {
      readSetBetalningService(filePath, bundle);
    } // more conditional statements can be added here if needed for other file types

    paymentReceiver.startPaymentBundle(bundle.getAccountNumber(), bundle.getPaymentDate(), bundle.getCurrency());
    List<SinglePayment> singlePayments = bundle.getPaymentList();

    for (SinglePayment p : singlePayments) {
      paymentReceiver.payment(p.getAmount(), p.getReference());
    }
    paymentReceiver.endPaymentBundle();
  }

  /**
   * Sets the account number, date and individual payments of the bundle object
   * @param filePath absolute file path
   * @param  bundle the payment bundle object
   */
  public void readSetInbetalning(String filePath, PaymentBundle bundle) {
    try {
      List<String> lineList = textFileToList(filePath);
      String openingLine = lineList.get(0);
      String accountNumber = openingLine.substring(14, 24).trim();
      bundle.setAccountNumber(accountNumber);

      bundle.setCurrency(defaultCurrency);

      lineList.remove(0);
      lineList.remove(lineList.size() - 1);
      for (String line : lineList) {
        BigDecimal amount = new BigDecimal(line.substring(2, 20) + "." + line.substring(20, 22));

        SinglePayment sp = new SinglePayment(amount, line.substring(40));
        bundle.addSinglePayment(sp);
      }
    } catch (FileNotFoundException e) {
      e.fillInStackTrace();
    }
  }

  /**
   * Sets the account number and the individual payments of the bundle object
   * @param filePath absolute file path
   * @param  bundle the payment bundle object
   */
  public void readSetBetalningService(String filePath, PaymentBundle bundle) {
    try {
      List<String> lineList = textFileToList(filePath);
      String openingLine = lineList.get(0);

      String accountNumber = openingLine.substring(1, 16).trim();
      bundle.setAccountNumber(accountNumber);

      String dateString = openingLine.substring(40, 48);

      DateFormat format = new SimpleDateFormat("yyyyMMdd");
      Date date = format.parse(dateString);
      bundle.setPaymentDate(date);

      String currency = openingLine.substring(48, 51);
      bundle.setCurrency(currency);

      lineList.remove(0);

      for (String line : lineList) {
        String stringAmount = line.substring(1, 15).trim().replace(',', '.');
        BigDecimal amount = new BigDecimal(stringAmount);
        String ref = line.substring(15, 50).trim();

        SinglePayment payment = new SinglePayment(amount, ref);
        bundle.addSinglePayment(payment);
      }
    } catch (ParseException | FileNotFoundException e) {
      e.fillInStackTrace();
    }
  }
  /**
   *
   * @param filePath absolute file path.
   * @return a list whose elements are the lines of the text file with the path "filepath"
   */
  public List<String> textFileToList(String filePath) throws FileNotFoundException {
    List<String> list = new ArrayList<>();
    File betalningFile = new File(filePath);
    Scanner myReader = new Scanner(betalningFile);
    while (myReader.hasNextLine()) {
      list.add(myReader.nextLine());
    }
    myReader.close();
    return list;
  }
}
