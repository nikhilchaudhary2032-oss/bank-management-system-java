import java.io.*;
import java.util.*;

class Account implements Serializable {
    int accNo;
    String name;
    double balance;

    Account(int accNo, String name, double balance) {
        this.accNo = accNo;
        this.name = name;
        this.balance = balance;
    }
}

public class BankSystem {

    static Map<Integer, Account> accounts = new HashMap<>();
    static final String FILE = "accounts.dat";

    static void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE))) {
            accounts = (Map<Integer, Account>) ois.readObject();
        } catch (Exception e) {
            accounts = new HashMap<>();
        }
    }

    static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(accounts);
        } catch (Exception e) {
            System.out.println("Error saving data");
        }
    }

    static void createAccount() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Initial Balance: ");
        double bal = sc.nextDouble();

        accounts.put(accNo, new Account(accNo, name, bal));
        saveData();
        System.out.println("✅ Account Created");
    }

    static void deposit() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        Account acc = accounts.get(accNo);
        if (acc != null) {
            System.out.print("Enter Amount: ");
            acc.balance += sc.nextDouble();
            saveData();
            System.out.println("✅ Money Deposited");
        } else {
            System.out.println("❌ Account not found");
        }
    }

    static void withdraw() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        Account acc = accounts.get(accNo);
        if (acc != null) {
            System.out.print("Enter Amount: ");
            double amt = sc.nextDouble();

            if (amt <= acc.balance) {
                acc.balance -= amt;
                saveData();
                System.out.println("✅ Withdraw Successful");
            } else {
                System.out.println("❌ Insufficient Balance");
            }
        } else {
            System.out.println("❌ Account not found");
        }
    }

    static void checkBalance() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        Account acc = accounts.get(accNo);
        if (acc != null) {
            System.out.println("💰 Balance: ₹" + acc.balance);
        } else {
            System.out.println("❌ Account not found");
        }
    }

    public static void main(String[] args) {
        loadData();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Bank Management System ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1 -> createAccount();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 4 -> checkBalance();
                case 5 -> {
                    System.out.println("Thank You 👋");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }
}
