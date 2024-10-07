package Model;

import javafx.beans.property.*;

public class Runner {
    private final IntegerProperty runnerId;
    private final StringProperty name;
    private final StringProperty last_name;
    private final IntegerProperty MarathonId;
    private final StringProperty MarathonName;
    private final IntegerProperty age;
    private final StringProperty gender;
    private final StringProperty email;
    private final StringProperty phoneNumber;

    public Runner(int runnerId, String name, String last_name, int marathonId, String marathonName, int age, String gender, String email, String phoneNumber) {
        this.runnerId = new SimpleIntegerProperty(runnerId);
        this.last_name = new SimpleStringProperty(last_name);
        this.MarathonId = new SimpleIntegerProperty(marathonId);
        this.name = new SimpleStringProperty(name);
        this.MarathonName = new SimpleStringProperty(marathonName);
        this.age = new SimpleIntegerProperty(age);
        this.gender = new SimpleStringProperty(gender);
        this.email = new SimpleStringProperty(email);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    public Runner(IntegerProperty runnerId, String name, String last_name, IntegerProperty marathonId, String marathonName, IntegerProperty  age, String gender, String email, String phoneNumber) {
        this.runnerId = runnerId;
        this.last_name = new SimpleStringProperty(last_name);
        this.MarathonId = marathonId;
        this.name = new SimpleStringProperty(name);
        this.MarathonName = new SimpleStringProperty(marathonName);
        this.age = age;
        this.gender = new SimpleStringProperty(gender);
        this.email = new SimpleStringProperty(email);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    public String getLast_name() {
        return last_name.get();
    }

    public StringProperty last_nameProperty() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name.set(last_name);
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



    public IntegerProperty getAge() {
        return age;
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public void setAge(int age) {
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


    public int getMarathonId() {
        return MarathonId.get();
    }

    public IntegerProperty marathonIdProperty() {
        return MarathonId;
    }

    public void setMarathonId(int marathonId) {
        this.MarathonId.set(marathonId);
    }

    public String getMarathonName() {
        return MarathonName.get();
    }

    public StringProperty marathonNameProperty() {
        return MarathonName;
    }

    public void setMarathonName(String marathonName) {
        this.MarathonName.set(marathonName);
    }
}
