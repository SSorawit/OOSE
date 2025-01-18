public class Manager extends Person{
    private String accountId;
    private String password;

    public Manager(String idCard, String name, String surname, String gender, String accId, String accPassword){
        super(idCard, name, surname, gender);
        this.accountId = accId;
        this.password = accPassword;
    }

    public boolean login(String inputAccountId,String inputPassword){
        return this.accountId.equals(inputAccountId) && this.password.equals(inputPassword);
    }
}
