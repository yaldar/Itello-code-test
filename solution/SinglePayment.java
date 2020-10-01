package solution;

import java.math.BigDecimal;

public class SinglePayment {
  private BigDecimal amount;
  private String reference;

  public SinglePayment(BigDecimal amount, String reference){
    this.amount = amount;
    this.reference = reference;
  }
  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  public String getReference() {
    return reference;
  }
}
