import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class LoanStore {
    private List<Loan> loans;

    public LoanStore() {
        loans = new ArrayList<>();
    }

    public void addLoan(Loan loan) throws Exception {
        if (!loan.isPaymentDateValid()) {
            throw new Exception("Payment date cannot be greater than the due date.");
        }
        loans.add(loan);
        if (loan.isDueDatePassed()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("ALERT: Loan with ID " + loan.getLoanId() + " has crossed the due date. Due date: " + sdf.format(loan.getDueDate()));
        }
    }

    public double getTotalRemainingAmountByLender(String lenderId) {
        double totalRemainingAmount = 0;
        for (Loan loan : loans) {
            if (loan.getLenderId().equals(lenderId)) {
                totalRemainingAmount += loan.getRemainingAmount();
            }
        }
        return totalRemainingAmount;
    }

    public double getTotalInterestByInterestRate(int interestRate) {
        double totalInterest = 0;
        for (Loan loan : loans) {
            if (loan.getInterestPerDay() == interestRate) {
                totalInterest += loan.calculateInterest();
            }
        }
        return totalInterest;
    }

    public double getTotalRemainingAmountByCustomerId(String customerId) {
        double totalRemainingAmount = 0;
        for (Loan loan : loans) {
            if (loan.getCustomerId().equals(customerId)) {
                totalRemainingAmount += loan.getRemainingAmount();
            }
        }
        return totalRemainingAmount;
    }

public Map<String, Double> aggregateRemainingAmountByLender() {
        Map<String, Double> lenderToRemainingAmountMap = new HashMap<>();
        for (Loan loan : loans) {
            String lenderId = loan.getLenderId();
            double remainingAmount = loan.getRemainingAmount();
            lenderToRemainingAmountMap.put(lenderId, lenderToRemainingAmountMap.getOrDefault(lenderId, 0.0) + remainingAmount);
        }
        return lenderToRemainingAmountMap;
    }

    public Map<Integer, Double> aggregateInterestByInterestRate() {
        Map<Integer, Double> interestToTotalInterestMap = new HashMap<>();
        for (Loan loan : loans) {
            int interestRate = loan.getInterestPerDay();
            double interest = loan.calculateInterest();
            interestToTotalInterestMap.put(interestRate, interestToTotalInterestMap.getOrDefault(interestRate, 0.0) + interest);
        }
        return interestToTotalInterestMap;
    }

    public Map<String, Double> aggregateRemainingAmountByCustomerId() {
        Map<String, Double> customerToRemainingAmountMap = new HashMap<>();
        for (Loan loan : loans) {
            String customerId = loan.getCustomerId();
            double remainingAmount = loan.getRemainingAmount();
            customerToRemainingAmountMap.put(customerId, customerToRemainingAmountMap.getOrDefault(customerId, 0.0) + remainingAmount);
        }
        return customerToRemainingAmountMap;
    }

}
