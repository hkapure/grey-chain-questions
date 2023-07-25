import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LoanStoreTest {
    private LoanStore loanStore;
    private SimpleDateFormat sdf;

    @BeforeEach
    void setUp() {
        loanStore = new LoanStore();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Test
    void testAddLoanWithInvalidPaymentDate() throws ParseException {
        Date paymentDate = sdf.parse("06/07/2023");
        Date dueDate = sdf.parse("05/07/2023");
        Loan loan = new Loan("L1", "C1", "LEN1", 10000, 10000, paymentDate, 1, dueDate, 0.01);
        assertThrows(Exception.class, () -> loanStore.addLoan(loan));
    }

    @Test
    void testAddLoanWithDueDatePassed() throws ParseException {
        Date paymentDate = sdf.parse("01/07/2023");
        Date dueDate = sdf.parse("30/06/2023");
        Loan loan = new Loan("L2", "C1", "LEN1", 20000, 5000, paymentDate, 1, dueDate, 0.01);
        loanStore.addLoan(loan);
        // No exception should be thrown as the due date has passed.
    }

    @Test
    void testAggregateRemainingAmountByLender() throws ParseException {
        Date dueDate1 = sdf.parse("05/07/2023");
        Date dueDate2 = sdf.parse("05/08/2023");

        Loan loan1 = new Loan("L1", "C1", "LEN1", 10000, 10000, dueDate1, 1, dueDate1, 0.01);
        Loan loan2 = new Loan("L2", "C1", "LEN1", 20000, 5000, dueDate2, 1, dueDate2, 0.01);

        try {
            loanStore.addLoan(loan1);
            loanStore.addLoan(loan2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Double> lenderToRemainingAmountMap = loanStore.aggregateRemainingAmountByLender();

        assertEquals(2, lenderToRemainingAmountMap.size());
        assertEquals(15000.0, lenderToRemainingAmountMap.get("LEN1"));
        assertEquals(0.0, lenderToRemainingAmountMap.get("LEN2"));
    }

    @Test
    void testAggregateInterestByInterestRate() throws ParseException {
        Date dueDate1 = sdf.parse("05/07/2023");
        Date dueDate2 = sdf.parse("05/08/2023");

        Loan loan1 = new Loan("L1", "C1", "LEN1", 10000, 10000, dueDate1, 1, dueDate1, 0.01);
        Loan loan2 = new Loan("L2", "C1", "LEN1", 20000, 5000, dueDate2, 1, dueDate2, 0.01);

        try {
            loanStore.addLoan(loan1);
            loanStore.addLoan(loan2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<Integer, Double> interestToTotalInterestMap = loanStore.aggregateInterestByInterestRate();

        assertEquals(1, interestToTotalInterestMap.size());
        assertEquals(50.0, interestToTotalInterestMap.get(1));
    }

    @Test
    void testAggregateRemainingAmountByCustomerId() throws ParseException {
        Date dueDate1 = sdf.parse("05/07/2023");
        Date dueDate2 = sdf.parse("05/08/2023");

        Loan loan1 = new Loan("L1", "C1", "LEN1", 10000, 10000, dueDate1, 1, dueDate1, 0.01);
        Loan loan2 = new Loan("L2", "C1", "LEN1", 20000, 5000, dueDate2, 1, dueDate2, 0.01);

        try {
            loanStore.addLoan(loan1);
            loanStore.addLoan(loan2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Double> customerToRemainingAmountMap = loanStore.aggregateRemainingAmountByCustomerId();

        assertEquals(1, customerToRemainingAmountMap.size());
        assertEquals(15000.0, customerToRemainingAmountMap.get("C1"));
        assertNull(customerToRemainingAmountMap.get("C2")); // No loans for C2
    }
}
