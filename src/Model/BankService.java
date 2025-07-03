package Model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BankService {

    private static final double DEFAULT_INTEREST_RATE_PER_TURN = 0.10 / 12; // ~0.0083

    public static boolean issueLoan(Player player, double amount, int currentTurn) {
        if (player.getActiveLoan() != null) return false;
        Loan loan = new Loan(amount, DEFAULT_INTEREST_RATE_PER_TURN, currentTurn);
        player.setMoney((int) (player.getMoney() + amount));
        player.setActiveLoan(loan);
        return true;
    }

    public static boolean repayLoan(Player player) {
        Loan loan = player.getActiveLoan();
        if (loan == null) return false;

        double amountDue = loan.getOutstandingAmount();
        if (player.getMoney() >= amountDue) {
            player.setMoney((int) (player.getMoney() - amountDue));
            player.setActiveLoan(null);
            return true;
        }
        return false;
    }

    public static void updateLoanEachTurn(Player player) {
        Loan loan = player.getActiveLoan();
        if (loan != null) {
            loan.incrementTurn();
            double debt = loan.getOutstandingAmount();
            double totalFunds = player.getMoney() + player.getTotalAssets(); // Make sure you have this method

            if (debt > totalFunds) {
                foreclose(player);
            }
        }
    }

    public static void foreclose(Player player) {
        List<PropertyTile> properties = player.getOwnedProperties(); // ensure this method exists
        properties.sort(Comparator.comparingInt(PropertyTile::getPrice).reversed());

        double debt = player.getActiveLoan().getOutstandingAmount();

        for (PropertyTile property : properties) {
            player.sellProperty(property);  // make sure this method exists
            debt -= property.getPrice();
            if (debt <= 0) break;
        }

        if (debt > 0) {
            player.setBankrupt(true);  // again, this method must exist
        } else {
            player.setActiveLoan(null);
        }
    }
}
