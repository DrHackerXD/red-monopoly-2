package Model;

public class Loan {

    private final double principal;
    private final double interestRate;
    private final int turnIssued;
    private int turnsPassed;

    public Loan(double principal, double interestRate, int turnIssued) {
        this.principal = principal;
        this.interestRate = interestRate;
        this.turnIssued = turnIssued;
        this.turnsPassed = 0;
    }

    public double getOutstandingAmount() {
        return principal + (principal * interestRate * turnsPassed);
    }

    public void incrementTurn() {
        this.turnsPassed++;
    }

    // Getters and setters
    public double getPrincipal() { return principal; }
    public double getInterestRate() { return interestRate; }
    public int getTurnIssued() { return turnIssued; }
    public int getTurnsPassed() { return turnsPassed; }
    public void setTurnsPassed(int turnsPassed) { this.turnsPassed = turnsPassed; }
}
