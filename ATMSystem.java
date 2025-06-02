// Base class representing a Bank Account
class BankAccount {
    protected String accountNumber;
    protected double balance;

    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: " + amount);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void displayBalance() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Balance: " + balance);
    }
}

// ATM class managing transactions
class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public void performTransaction(String type, double amount) {
        switch (type.toLowerCase()) {
            case "withdraw":
                account.withdraw(amount);
                break;
            case "deposit":
                account.deposit(amount);
                break;
            default:
                System.out.println("Invalid transaction type.");
        }
    }

    public void checkBalance() {
        account.displayBalance();
    }
}

// Main class to test ATM functionality
public class ATMSystem {
    public static void main(String[] args) {
        BankAccount account = new BankAccount("123456789", 5000.0);
        ATM atm = new ATM(account);

        System.out.println("Performing Transactions...");
        atm.performTransaction("withdraw", 1000);
        atm.performTransaction("deposit", 2000);

        System.out.println("\nChecking Balance:");
        atm.checkBalance();
    }
}