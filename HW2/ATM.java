import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ATM implements ATMAction{
    private ArrayList<BankAccount> accounts;
    private ArrayList<Manager> managers;

    public static void main(String[] args){
        boolean isManagerCreated = false;
        Scanner scanner = new Scanner(System.in);
        ATM atm = new ATM();

        // สร้าง Manager
        System.out.println("Create the manager[Y/N]: ");
        String create = scanner.nextLine();
        if(create.equalsIgnoreCase("y")){
            isManagerCreated = true;
            System.out.println("Enter Manager Name: ");
            String name = scanner.nextLine();
            System.out.println("Enter Manager Surname: ");
            String surname = scanner.nextLine();
            System.out.println("Enter Manager ID Card: ");
            String managerIdCard = scanner.nextLine();
            System.out.println("Enter Manager Gender [Male/Female]: ");
            String managerGender = scanner.nextLine();
            System.out.println("Enter Manager Account ID: ");
            String managerAccountId = scanner.nextLine();
            System.out.println("Enter Manager Account Password: ");
            String managerPassword = scanner.nextLine();

            Manager manager = new Manager(managerIdCard, name, surname, managerGender, managerAccountId, managerPassword);
            atm.addManagerAccount(manager);
            System.out.println("Manager account create successfully!");

            // ล๊อกอิน Manager
            System.out.println("Manager Login to use ATM");
            System.out.println("Manager ID: ");
            String loginId = scanner.nextLine();
            System.out.println("Account Password: ");
            String loginPass = scanner.nextLine();
            if(manager.login(loginId, loginPass)){
                System.out.println("Login successful!");
                //สร้างบัญชีธนาคาร
                System.out.println("Step 1. Enter the number of accounts to create: ");
                int accountCount = scanner.nextInt();
                scanner.nextLine();
                for(int i=1;i <= accountCount; i++){
                    System.out.println("Create account #" +i);
                    System.out.println("Name: ");
                    String accname = scanner.nextLine();
                    System.out.println("Surname: ");
                    String surnameAcc = scanner.nextLine();
                    System.out.println("ID Card: ");
                    String idCard = scanner.nextLine();
                    System.out.println("Gender [Male/Female]: ");
                    String gender = scanner.nextLine();
                    System.out.println("Account ID: ");
                    String accountId = scanner.nextLine();
                    System.out.println("Password: ");
                    String password = scanner.nextLine();
                    System.out.println("Initial Balance: ");
                    int balance = scanner.nextInt();
                    scanner.nextLine();

                    BankAccount account = new BankAccount(idCard, accname, surnameAcc, gender, accountId, password, balance);
                    atm.addAccount(account);
                    System.out.println("Account added successfuly!");
                }
                // ให้ user login เพิ่มทำธุรกรรม
                boolean running = true;
                while(running) {
                    System.out.println("please login to an account to use ATM services.");
                    System.out.println("Enter Account ID: ");
                    String accountId = scanner.nextLine();
                    System.out.println("Enter Account Password: ");
                    String accountPassword = scanner.nextLine();
                    
                    BankAccount loggedIdAccount = atm.findAccountById(accountId);
                    if (loggedIdAccount != null && loggedIdAccount.login(accountId, accountPassword)){
                        System.out.println("Login successful! Welcome, " + loggedIdAccount.getAccountName());
                        boolean loggedId = true;

                        // ATM menu
                        while(loggedId) {
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
                                    System.out.println("Enter amount to deposit: ");
                                    int depositAmount = scanner.nextInt();
                                    scanner.nextLine();
                                    System.out.println("Depositing money...");
                                    atm.depositable(loggedIdAccount, depositAmount);
                                    break;
                                case 3:
                                    System.out.println("Enter amount to withdraw: ");
                                    int withdrawAmount = scanner.nextInt();
                                    scanner.nextLine();
                                    System.out.println("Withdrawing money...");
                                    atm.withdrawable(loggedIdAccount, withdrawAmount);
                                    break;
                                case 4:
                                    System.out.println("Enter recipient Account ID: ");
                                    String recipientId = scanner.nextLine();
                                    System.out.println("Enter amount to transfer: ");
                                    int transferAmount = scanner.nextInt();
                                    scanner.nextLine();

                                    BankAccount recipieAccount = atm.findAccountById(recipientId);
                                    if (recipieAccount != null){
                                        System.out.println("Transferring money...");
                                        atm.transferable(loggedIdAccount, recipieAccount, transferAmount);
                                    }else{
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
            } else{
                System.out.println("Login failed. Exiting system");
            }
        } else{
            System.out.println("Manager not created. Exiting System");
        }
        scanner.close();
    }
    public ATM(){
        this.accounts = new ArrayList<>();
        this.managers = new ArrayList<>();
    }

    public BankAccount findAccountById(String accountId){
        for(BankAccount account : accounts){
            if(account.getAccountId().equals(accountId)){
                return account;
            }
        }
        return null;
    }

    @Override
    public void addManagerAccount(Manager account){
        this.managers.add(account);
    }

    @Override
    public void addAccount(BankAccount account){
        this.accounts.add(account);
    }

    // ตรวจสอบเงินคงเหลือ
    @Override
    public boolean checkable(BankAccount loggedInAccount){
        if(loggedInAccount == null){
            System.out.println("No account is currently logged in.");
            return false;
        }
        System.out.println("Account: " + loggedInAccount.getAccountName() + ", Balance" + loggedInAccount.getBalance());
        return true;
    }

    //การถอนเงิน
    @Override
    public boolean withdrawable(BankAccount loggedInAccount, int amount){
        if(loggedInAccount == null){
            System.out.println("No account is currently logged in.");
            return false;
        }
        if (amount > 0 && loggedInAccount.getBalance() >= amount) {
            loggedInAccount.withdraw(amount);
            return true;
        }
        System.out.println("Withdrawal failed. Insufficient funds or invalid amount.");
        return false;
    }

    //การฝากเงิน
    @Override
    public boolean depositable(BankAccount loggedInAccount, int amount){
        if(loggedInAccount == null){
            System.out.println("No account is currently logged in.");
            return false;
        }
        if(amount > 0){
            loggedInAccount.deposit(amount);
            return true;
        }
        System.out.println("Deposit failed. Invalid amount.");
        return false;
    }

    //การโอนเงิน
    @Override
    public boolean transferable(BankAccount loggedInAccount, BankAccount recipient, int amount){
        if(loggedInAccount == null){
            System.out.println("No account is currently logged in.");
            return false;
        }
        if(recipient == null){
            System.out.println("Recipient account not found.");
            return false;
        }
        if(amount > 0 && loggedInAccount.getBalance() >= amount){
            loggedInAccount.withdraw(amount);
            recipient.deposit(amount);
            System.out.println("Transfer successful. Transferred "+ amount + " to " + recipient.getAccountName());
            return true;
        }
        System.out.println("Transfer failed. Insufficient funds or invalid amount.");
        return false;
    }
}
