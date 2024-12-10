public class Person {

    private String idCard;
    private String name;
    private String surname;
    private String gender;

    public Person(String idCard, String name, String surname, String gender){
        this.idCard = idCard;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
    }

    String getIdCard(){
        return this.idCard;
    }

    String getName(){
        return this.name+" "+this.surname;
    }

    String getGender(){
        return this.gender;
    }
}