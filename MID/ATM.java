import java.util.ArrayList;
import java.util.Scanner;

public class ATM implements ATMAction {
    private ArrayList<BankAccount> accounts;
    private ArrayList<Manager> managers;
    private double rate;

    public static void main(String[] args) {
        boolean isManagerCreated = false;
        Scanner scanner = new Scanner(System.in);
        ATM atm = new ATM();

        // สร้าง Manager
        System.out.print("Create the manager[Y/N]: ");
        String create = scanner.nextLine();
        if (create.equalsIgnoreCase("y")) {
            isManagerCreated = true;
            System.out.print("Enter Manager Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Manager Surname: ");
            String surname = scanner.nextLine();
            System.out.print("Enter Manager ID Card: ");
            String managerIdCard = scanner.nextLine();
            System.out.print("Enter Manager Gender [Male/Female]: ");
            String managerGender = scanner.nextLine();
            System.out.print("Enter Manager Account ID: ");
            String managerAccountId = scanner.nextLine();
            System.out.print("Enter Manager Account Password: ");
            String managerPassword = scanner.nextLine();

            Manager manager = new Manager(managerIdCard, name, surname, managerGender, managerAccountId,
                    managerPassword);
            atm.addManagerAccount(manager);
            System.out.println("Manager account create successfully!");

            // ล๊อกอิน Manager
            System.out.println("Manager Login to use ATM");
            System.out.print("Manager ID: ");
            String loginId = scanner.nextLine();
            System.out.print("Account Password: ");
            String loginPass = scanner.nextLine();
            if (manager.login(loginId, loginPass)) {
                System.out.println("Login successful!");
                // กำหนดอัตราแลกเปลี่ยน BTC
                System.out.print("Please Enter Exchangrate For BTC : ");
                int BTC = scanner.nextInt();
                scanner.nextLine();
                atm.exchangrateBTC(BTC);
                // สร้างบัญชีธนาคาร
                System.out.print("Step 1. Enter the number of accounts to create: ");
                int accountCount = scanner.nextInt();
                scanner.nextLine();
                for (int i = 1; i <= accountCount; i++) {
                    System.out.println("Create account #" + i);
                    System.out.print("Name: ");
                    String accname = scanner.nextLine();
                    System.out.print("Surname: ");
                    String surnameAcc = scanner.nextLine();
                    System.out.print("ID Card: ");
                    String idCard = scanner.nextLine();
                    System.out.print("Gender [Male/Female]: ");
                    String gender = scanner.nextLine();
                    System.out.print("Account ID: ");
                    String accountId = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    System.out.print("Initial Balance: ");
                    int balance = scanner.nextInt();
                    scanner.nextLine();

                    BankAccount account = new BankAccount(idCard, accname, surnameAcc, gender, accountId, password,
                            balance);
                    atm.addAccount(account);
                    System.out.println("Account added successfuly!");
                }
                // ให้ user login เพิ่มทำธุรกรรม
                boolean running = true;
                while (running) {
                    System.out.println("please login to an account to use ATM services.");
                    System.out.print("Enter Account ID: ");
                    String accountId = scanner.nextLine();
                    System.out.print("Enter Account Password: ");
                    String accountPassword = scanner.nextLine();

                    BankAccount loggedIdAccount = atm.findAccountById(accountId);
                    if (loggedIdAccount != null && loggedIdAccount.login(accountId, accountPassword)) {
                        System.out.println("Login successful! Welcome, " + loggedIdAccount.getAccountName());
                        boolean loggedId = true;

                        // ATM menu
                        while (loggedId) {
                            System.out.println("\nATM Menu:");
                            System.out.println("1. Check Balance");
                            System.out.println("2. Deposit Money");
                            System.out.println("3. Withdraw Money");
                            System.out.println("4. Tranfer Money");
                            System.out.println("5. Exit");
        

                            System.out.print("Choose an option: ");
                            int choice = scanner.nextInt();
                            scanner.nextLine();

                            switch (choice) {
                                case 1:
                                    System.out.println("Checking balance...");
                                    atm.checkable(loggedIdAccount);
                                    break;
                                case 2:
                                    System.out.println("Choose deposit type:");
                                    System.out.println("1. Deposit in THB");
                                    System.out.println("2. Deposit in BTC");
                                    System.out.print("Enter your choice: ");
                                    int depositType = scanner.nextInt();
                                    scanner.nextLine();

                                    if (depositType == 1) { // ฝากเป็น บาท
                                        System.out.print("Enter amount to deposit (THB): ");
                                        double depositAmount = scanner.nextDouble();
                                        scanner.nextLine();
                                        atm.depositable(loggedIdAccount, depositAmount, false);
                                    } else if (depositType == 2) { // ฝากเป็น BTC
                                        System.out.print("Enter amount to deposit (BTC): ");
                                        double depositBTC = scanner.nextDouble();
                                        scanner.nextLine();
                                        atm.depositable(loggedIdAccount, depositBTC, true);
                                    } else {
                                        System.out.println("Invalid choice.");
                                    }
                                    break;
                                case 3:
                                    System.out.println("Choose withdrawal type:");
                                    System.out.println("1. Withdraw in THB");
                                    System.out.println("2. Withdraw in BTC");
                                    System.out.print("Enter your choice: ");
                                    int withdrawType = scanner.nextInt();
                                    scanner.nextLine();

                                    if (withdrawType == 1) { // ถอนเป็น บาท
                                        System.out.print("Enter amount to withdraw (THB): ");
                                        double withdrawAmount = scanner.nextDouble();
                                        scanner.nextLine();
                                        atm.withdrawable(loggedIdAccount, withdrawAmount, false);
                                    } else if (withdrawType == 2) { // ถอนเป็น BTC
                                        System.out.print("Enter amount to withdraw (BTC): ");
                                        double withdrawBTC = scanner.nextDouble();
                                        scanner.nextLine();
                                        atm.withdrawable(loggedIdAccount, withdrawBTC, true);
                                    } else {
                                        System.out.println("Invalid choice.");
                                    }
                                    break;
                                case 4:
                                    System.out.print("Enter recipient Account ID: ");
                                    String recipientId = scanner.nextLine();
                                    BankAccount recipientAccount = atm.findAccountById(recipientId);

                                    if (recipientAccount != null) {
                                        System.out.println("Choose transfer type:");
                                        System.out.println("1. Transfer in THB");
                                        System.out.println("2. Transfer in BTC");
                                        System.out.print("Enter your choice: ");
                                        int transferType = scanner.nextInt();
                                        scanner.nextLine();

                                        if (transferType == 1) { // โอนเป็น บาท
                                            System.out.print("Enter amount to transfer (THB): ");
                                            double transferAmount = scanner.nextDouble();
                                            scanner.nextLine();
                                            atm.transferable(loggedIdAccount, recipientAccount, transferAmount, false);
                                        } else if (transferType == 2) { // โอนเป็น BTC
                                            System.out.print("Enter amount to transfer (BTC): ");
                                            double transferBTC = scanner.nextDouble();
                                            scanner.nextLine();
                                            atm.transferable(loggedIdAccount, recipientAccount, transferBTC, true);
                                        } else {
                                            System.out.println("Invalid choice.");
                                        }
                                    } else {
                                        System.out.println("Recipient account not found. Transfer failed.");
                                    }
                                    break;
                                case 5:
                                    loggedId = false;
                                    System.out.println("Thank you for using the ATM. Goodbye!");
                                    break;
                                default:
                                    System.out.println("Invalid option. please try again.");
                            }
                        }
                    }
                }
            } else {
                System.out.println("Login failed. Exiting system");
            }
        } else {
            System.out.println("Manager not created. Exiting System");
        }
        scanner.close();
    }

    public ATM() {
        this.accounts = new ArrayList<>();
        this.managers = new ArrayList<>();
        this.rate = 0;
    }

    public BankAccount findAccountById(String accountId) {
        for (BankAccount account : accounts) {
            if (account.getAccountId().equals(accountId)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public void addManagerAccount(Manager account) {
        this.managers.add(account);
    }

    @Override
    public void addAccount(BankAccount account) {
        this.accounts.add(account);
    }

    // ตรวจสอบเงินคงเหลือ
    @Override
    public boolean checkable(BankAccount loggedInAccount) {
        if (loggedInAccount == null) {
            System.out.println("No account is currently logged in.");
            return false;
        }
        System.out.println("Account: " + loggedInAccount.getAccountName() + ", Balance : "
                + loggedInAccount.getBalance() + " Bath");
        if (rate > 0) {
            System.out.println("Account: " + loggedInAccount.getAccountName() + ", BTC : "
                    + (double) loggedInAccount.getBalance() / rate);
        } else {
            System.out.println("Exchange rate not set. Unable to display BTC value.");
        }
        return true;
    }

    // การถอนเงิน
    @Override
    public boolean withdrawable(BankAccount loggedInAccount, double amount, boolean isBTC) {
        if (loggedInAccount == null) {
            System.out.println("No account is currently logged in.");
            return false;
        }

        if (isBTC) { // ถอนเป็น BTC
            if (rate <= 0) {
                System.out.println("Exchange rate not set. Unable to withdraw BTC.");
                return false;
            }
            double convertedAmount = amount * rate; // แปลง BTC เป็นบาท
            if (loggedInAccount.getBalance() >= convertedAmount) {
                System.out.println("Withdrawing " + amount + " BTC (" + convertedAmount + " THB)...");
                loggedInAccount.withdraw(convertedAmount);
                return true;
            } else {
                System.out.println("Withdrawal failed. Insufficient funds.");
                return false;
            }
        } else { // ถอนเป็น บาท
            if (amount > 0 && loggedInAccount.getBalance() >= amount) {
                System.out.println("Withdrawing " + amount + " THB...");
                loggedInAccount.withdraw(amount);
                return true;
            }
            System.out.println("Withdrawal failed. Insufficient funds or invalid amount.");
            return false;
        }
    }

    // การฝากเงิน
    @Override
    public boolean depositable(BankAccount loggedInAccount, double amount, boolean isBTC) {
        if (loggedInAccount == null) {
            System.out.println("No account is currently logged in.");
            return false;
        }

        if (isBTC) { // ฝากเป็น BTC
            if (rate <= 0) {
                System.out.println("Exchange rate not set. Unable to deposit BTC.");
                return false;
            }
            double convertedAmount = amount * rate; // แปลง BTC เป็นบาท
            System.out.println("Depositing " + amount + " BTC (" + convertedAmount + " THB)...");
            loggedInAccount.deposit(convertedAmount);
        } else { // ฝากเป็น บาท
            if (amount <= 0) {
                System.out.println("Deposit failed. Invalid amount.");
                return false;
            }
            System.out.println("Depositing " + amount + " THB...");
            loggedInAccount.deposit(amount);
        }
        return true;
    }

    // การโอนเงิน
    @Override
    public boolean transferable(BankAccount loggedInAccount, BankAccount recipient, double amount, boolean isBTC) {
        if (loggedInAccount == null || recipient == null) {
            System.out.println("Sender or recipient account not found.");
            return false;
        }

        if (isBTC) { // โอนเป็น BTC
            if (rate <= 0) {
                System.out.println("Exchange rate not set. Unable to transfer BTC.");
                return false;
            }
            double convertedAmount = amount * rate; // แปลง BTC เป็น บาท
            if (loggedInAccount.getBalance() >= convertedAmount) {
                System.out.println("Transferring " + amount + " BTC (" + convertedAmount + " THB) to "
                        + recipient.getAccountName() + "...");
                loggedInAccount.withdraw(convertedAmount);
                recipient.deposit(convertedAmount);
                return true;
            } else {
                System.out.println("Transfer failed. Insufficient funds.");
                return false;
            }
        } else { // โอนเป็น บาท
            if (amount > 0 && loggedInAccount.getBalance() >= amount) {
                System.out.println("Transferring " + amount + " THB to " + recipient.getAccountName() + "...");
                loggedInAccount.withdraw(amount);
                recipient.deposit(amount);
                return true;
            }
            System.out.println("Transfer failed. Insufficient funds or invalid amount.");
            return false;
        }
    }

    @Override
    public double exchangrateBTC(double exchangrate) {
        this.rate = exchangrate;
        return (this.rate);
    }
}
