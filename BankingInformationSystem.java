import java.util.*;
import java.io.*;

class BankAccount implements Serializable {
    String name;
    int accountNumber;
    String dob;
    String password;
    double balance;

    BankAccount(String name, int accountNumber, String dob, String password, double balance) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.dob = dob;
        this.password = password;
        this.balance = balance;
    }

    void showDetails() {
        System.out.println("\n===== ACCOUNT DETAILS =====");
        System.out.println("Bank: BHARAT BANK OF INDIA");
        System.out.println("Name: " + name);
        System.out.println("Acc No: " + accountNumber);
        System.out.println("DOB: " + dob);
        System.out.println("Balance: " + balance);
    }
}

public class BankingInformationSystem {

    static HashMap<Integer, BankAccount> accounts = new HashMap<>();
    static final String FILE_NAME = "bankdata.dat";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadData();

        System.out.println("====== WELCOME TO BHARAT BANK OF INDIA ======");

        while (true) {
            System.out.println("\n1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    createAccount(sc);
                    break;
                case 2:
                    login(sc);
                    break;
                case 3:
                    saveData();
                    System.out.println("\n🙏 Thank you for visiting BHARAT BANK OF INDIA!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // 🔹 Create Account
    static void createAccount(Scanner sc) {
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter DOB (DD/MM/YY): ");
        String dob = sc.nextLine();

        System.out.print("Create Password: ");
        String password = sc.nextLine();

        System.out.print("Initial Balance: ");
        double balance = sc.nextDouble();

        accounts.put(accNo, new BankAccount(name, accNo, dob, password, balance));
        System.out.println("✅ Account Created Successfully!");
    }

    // 🔹 Login
    static void login(Scanner sc) {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter DOB: ");
        String dob = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        BankAccount acc = accounts.get(accNo);

        if (acc != null && acc.dob.equals(dob) && acc.password.equals(password)) {
            System.out.println("\n✅ Login Successful! Welcome " + acc.name + " 🎉");
            homeMenu(sc, acc);
        } else {
            System.out.println("\n❌ Invalid Information!");
        }
    }

    // 🔹 Home Menu
    static void homeMenu(Scanner sc, BankAccount acc) {
        int choice;
        do {
            System.out.println("\n===== HOME MENU =====");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. View Details");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    deposit(sc, acc);
                    break;
                case 2:
                    withdraw(sc, acc);
                    break;
                case 3:
                    System.out.println("💰 Current Balance: " + acc.balance);
                    break;
                case 4:
                    acc.showDetails();
                    break;
                case 5:
                    logoutMessage(acc);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);
    }

    // 🔹 Deposit Function
    static void deposit(Scanner sc, BankAccount acc) {
        System.out.print("Enter amount: ");
        double amt = sc.nextDouble();
        acc.balance += amt;
        System.out.println("✅ Deposited: " + amt);
    }

    // 🔹 Withdraw Function
    static void withdraw(Scanner sc, BankAccount acc) {
        System.out.print("Enter amount: ");
        double amt = sc.nextDouble();
        if (amt <= acc.balance) {
            acc.balance -= amt;
            System.out.println("✅ Withdrawn: " + amt);
        } else {
            System.out.println("❌ Insufficient Balance!");
        }
    }

    // 🔹 Logout Message
    static void logoutMessage(BankAccount acc) {
        System.out.println("\n👋 Thank you, " + acc.name + "!");
        System.out.println("🙏 Visit Again - BHARAT BANK OF INDIA");
    }

    // 🔹 Save Data
    static void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(accounts);
        } catch (Exception e) {
            System.out.println("Error saving data!");
        }
    }

    // 🔹 Load Data
    static void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            accounts = (HashMap<Integer, BankAccount>) ois.readObject();
        } catch (Exception e) {
            accounts = new HashMap<>();
        }
    }
}