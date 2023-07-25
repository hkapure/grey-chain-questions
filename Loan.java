import java.util.Date;

public class Loan {
    private String loanId;
    private String customerId;
    private String lenderId;
    private double amount;
    private double remainingAmount;
    private Date paymentDate;
    private int interestPerDay;
    private Date dueDate;
    private double penaltyPerDay;

    public Loan(String loanId, String customerId, String lenderId, double amount, double remainingAmount,
                Date paymentDate, int interestPerDay, Date dueDate, double penaltyPerDay) {
        this.loanId = loanId;
        this.customerId = customerId;
        this.lenderId = lenderId;
        this.amount = amount;
        this.remainingAmount = remainingAmount;
        this.paymentDate = paymentDate;
        this.interestPerDay = interestPerDay;
        this.dueDate = dueDate;
        this.penaltyPerDay = penaltyPerDay;
    }


    public boolean isPaymentDateValid() {
        return paymentDate.compareTo(dueDate) <= 0;
    }

    public boolean isDueDatePassed() {
        return new Date().compareTo(dueDate) > 0;
    }

    public double calculateInterest() {
        long days = (new Date().getTime() - dueDate.getTime()) / (1000 * 60 * 60 * 24);
        if (days <= 0) {
            return 0;
        }
        return remainingAmount * interestPerDay * days / 100.0;
    }

    public double calculatePenalty() {
        long days = (new Date().getTime() - dueDate.getTime()) / (1000 * 60 * 60 * 24);
        if (days <= 0) {
            return 0;
        }
        return remainingAmount * penaltyPerDay * days / 100.0;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId='" + loanId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", lenderId='" + lenderId + '\'' +
                ", amount=" + amount +
                ", remainingAmount=" + remainingAmount +
                ", paymentDate=" + paymentDate +
                ", interestPerDay=" + interestPerDay +
                ", dueDate=" + dueDate +
                ", penaltyPerDay=" + penaltyPerDay +
                '}';
    }
}
