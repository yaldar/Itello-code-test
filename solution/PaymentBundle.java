package solution;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentBundle {

  private String accountNumber;
  private Date paymentDate;
  private String currency;

  private List<SinglePayment> paymentList;

  public PaymentBundle() {
    this.paymentList = new ArrayList<>();
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public Date getPaymentDate() {
    return paymentDate;
  }

  public void setPaymentDate(Date paymentDate) {
    this.paymentDate = paymentDate;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public List<SinglePayment> getPaymentList() {
    return paymentList;
  }

  public void addSinglePayment(SinglePayment sp) {
    this.paymentList.add(sp);
  }
}
