package CommonDataTypes;

import java.io.Serializable;
import java.util.Objects;

public class PersonalData implements Serializable {
    private String name;
    private String surname;
    private String accountNumber;
    private String telephoneNumber;
    private float rating;
    private boolean isClient;
    private boolean wantToSignUp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonalData that = (PersonalData) o;
        return Float.compare(that.rating, rating) == 0 &&
                isClient == that.isClient &&
                wantToSignUp == that.wantToSignUp &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(accountNumber, that.accountNumber) &&
                Objects.equals(telephoneNumber, that.telephoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, accountNumber, telephoneNumber, rating, isClient, wantToSignUp);
    }

    public boolean isWantToSignUp() {
        return wantToSignUp;
    }

    public void setWantToSignUp(boolean wantToSignUp) {
        this.wantToSignUp = wantToSignUp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setClient(boolean client) {
        isClient = client;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public float getRating() {
        return rating;
    }

    public boolean isClient() {
        return isClient;
    }

    public PersonalData()
    {

    }

    public PersonalData(String name, String surname, String accountNumber, String telephoneNumber, boolean isClent, float rating )
    {
        this.name=name;
        this.surname=surname;
        this.accountNumber=accountNumber;
        this.telephoneNumber=telephoneNumber;
        this.rating=rating;
        this.isClient=isClent;
        this.wantToSignUp=false;
    }
}
