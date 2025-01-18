public class BankAccount extends Person {
    private String accountId;
    private String password;
    private double balance;

    public BankAccount(String idCard, String name, String surname, String gender, String accId, String accPassword, int accBalance){
        super(idCard, name, surname, gender);
        this.accountId = accId;
        this.password = accPassword;
        this.balance = accBalance;
    }

    public double getBalance(){
        return this.balance;
    }

    public String getAccountName(){
        return this.getName();
    }

    public String getAccountId(){
        return this.accountId;
    }

    public boolean login(String inputAccountId,String inputPassword){
        return this.accountId.equals(inputAccountId) && this.password.equals(inputPassword);
    }
    
    public void deposit(double amount){
        if(amount > 0){
            this.balance += amount;
            System.out.println("Deposit successful. New balance: "+ this.balance);
        }
        else{
            System.out.println("Deposit failed. Invalid amount.");
        }
    }

    public void withdraw(double amount){
        if (amount > 0 && amount <= this.balance) {
            this.balance -= amount;
            System.out.println("Withdrawal successful. Remaining balance: " + this.balance);
        }
        else{
            System.out.println("Withdarwal failed. Insufficient funds.");
        }
    }
}
