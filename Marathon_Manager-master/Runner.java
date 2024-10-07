package Model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Runner {
    private final IntegerProperty runnerId;
    private final StringProperty name;
    private final StringProperty age;
    private final StringProperty gender;
    private final StringProperty email;
    private final StringProperty phoneNumber;

    public Runner(int runnerId, String name, String age, String gender, String email, String phoneNumber) {
        this.runnerId = new SimpleIntegerProperty(runnerId);
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleStringProperty(age);
        this.gender = new SimpleStringProperty(gender);
        this.email = new SimpleStringProperty(email);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    public Runner(IntegerProperty runnerId, String name, String  age, String gender, String email, String phoneNumber) {
        this.runnerId = runnerId;
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleStringProperty(age);
        this.gender = new SimpleStringProperty(gender);
        this.email = new SimpleStringProperty(email);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    public int getRunnerId() {
        return runnerId.get();
    }

    public IntegerProperty runnerIdProperty() {
        return runnerId;
    }

    public void setRunnerId(int runnerId) {
        this.runnerId.set(runnerId);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }



    public String getAge() {
        return name.get();
    }

    public StringProperty ageProperty() {
        return name;
    }

    public void setAge(String age) {
        this.age.set(age);
    }



    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }
}
