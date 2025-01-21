public interface ATMAction {

    boolean checkable(BankAccount loggedInAccount);

    boolean withdrawable(BankAccount loggedInAccount, double amount, boolean isBTC);

    boolean depositable(BankAccount loggedInAccount, double amount, boolean isBTC);

    boolean transferable(BankAccount loggedInAccount, BankAccount recipient, double amount, boolean isBTC);

    double exchangrateBTC(double exchangrate);

    void addAccount(BankAccount account);

    void addManagerAccount(Manager account);
}