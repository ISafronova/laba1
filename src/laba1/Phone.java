package laba1;

import java.io.Serializable;


public class Phone implements Serializable {
    private String surname, city;
    private int number, code;
    int id;
    public Phone(int id) {
        this.id = id;
    }
    public Phone(int id,String surname, String city, int code, int number) {
        this.id = id;
        this.surname = surname;
        this.city = city;
        this.code = code;
        this.number = number;
    }
    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getCity() {
        return city;
    }

    public int getCode() {
        return code;
    }

    public int getNumber() {
        return number;
    }
    public void setSurname(String surname1) {
        this.surname = surname1;
    }

    public void setCity(String city1) {
        this.city = city1;
    }

    public void setCode(int code1) {
        this.code = code1;
    }

    public void setNumber(int number1) {
        this.number = number1;
    }

    public String toString() {
        return getId() + " - " + getSurname() + " - " + getCity() + " - " + getCode() + " - " + getNumber();
    }
}
