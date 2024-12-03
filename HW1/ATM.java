import java.util.Scanner;
import java.util.ArrayList;

public class ATM {
    private ArrayList<bankAccount> accounts;
    public static void main(String[] args) {
        ATM atm = new ATM();
        Scanner scanner = new Scanner(System.in);

       // เพิ่มบัญชี
       System.out.print("Step 1. Enter amount of all accounts = ");
       int accountCount = scanner.nextInt();
       scanner.nextLine(); // Consume leftover newline
       
       //ตรวจสอบว่าจำนวน Account เกินหรือไม่
       while(accountCount < 1 || accountCount > 5){
            System.out.println("Error : The number of account requests exceeds the limit."); 
            System.out.println("Please enter again.");    
            // เพิ่มบัญชี
            System.out.print("Step 1. Enter amount of all accounts = ");
            accountCount = scanner.nextInt();
            scanner.nextLine(); // Consume leftover newline  
       }

       System.out.println("Step 2. Enter details of accounts.");
       for (int i = 1; i <= accountCount; i++) {
           System.out.println("No. " + i);

           String accountId;
           while (true) {
               System.out.print("Account ID (13 characters): ");
               accountId = scanner.nextLine();
               if (accountId.length() == 13) {
                   break;
               }
               System.out.println("Error: Account ID must be 13 characters. Please try again.");
           }

           String accountName;
           while (true) {
               System.out.print("Account Name (max 50 characters): ");
               accountName = scanner.nextLine();
               if (accountName.length() <= 50) {
                   break;
               }
               System.out.println("Error: Account Name must not exceed 50 characters. Please try again.");
           }

           String password;
           while (true) {
               System.out.print("Password (4 characters): ");
               password = scanner.nextLine();
               if (password.length() == 4) {
                   break;
               }
               System.out.println("Error: Password must be 4 characters. Please try again.");
           }

           int balance;
           while (true) {
               System.out.print("Balance (max 1,000,000): ");
               balance = scanner.nextInt();
               scanner.nextLine(); // Consume leftover newline
               if (balance <= 1000000) {
                   break;
               }
               System.out.println("Error: Balance must not exceed 1,000,000. Please try again.");
           }

           atm.addAccount(new bankAccount(accountName, accountId, password, balance));
       }

       // เริ่มต้นระบบ ATM
       atm.start();
    }
    public ATM() {
        this.accounts = new ArrayList<>();
    }

    public void addAccount(bankAccount account) {
        this.accounts.add(account);
    }

    // ฟังก์ชันลดเงิน (ถอนเงิน)
    public void withdraw(bankAccount account, int amount) {
        if (account.checkBalance(amount)) {
            System.out.println("Withdrawal successful. Remaining balance: " + account.getBalance());
        } else {
            System.out.println("Insufficient funds or invalid amount.");
        }
    }

    // เริ่มต้นระบบ ATM
    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nATM ComputerThanyaburi Bank");
        System.out.print("Account ID: ");
        String loginId = scanner.nextLine();

        System.out.print("Account Password: ");
        String loginPass = scanner.nextLine();

        // ตรวจสอบข้อมูลบัญชี
        boolean isAuthenticated = false;
        bankAccount loggedInAccount = null;

        for (bankAccount account : this.accounts) {
            if (account.verifyId(loginId) && account.verifyPassword(loginPass)) {
                System.out.println("Login Success");
                isAuthenticated = true;
                loggedInAccount = account;
                break;
            }
        }

        if (isAuthenticated) {
            boolean exit = false;
            while (!exit) {
                System.out.println("\nATM ComputerThanyaburi Bank");
                System.out.println("Account ID: "+loginId);
                System.out.println("1. Check Balance");
                System.out.println("2. Withdraw Money");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Your balance is: " + loggedInAccount.getBalance());
                        break;

                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        int amount = scanner.nextInt();
                        withdraw(loggedInAccount, amount); // เรียกใช้ฟังก์ชันถอนเงิน
                        break;

                    case 3:
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        exit = true;
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } else {
            System.out.println("Login Failed");
        }
        scanner.close();
    }
}