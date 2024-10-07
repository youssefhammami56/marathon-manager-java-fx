package Model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Participation {
    private final IntegerProperty participation_id;
    private final IntegerProperty marathon_id;

    private final StringProperty MarathonName;
    private final IntegerProperty runner_id;

    private final StringProperty first_name;

    private final StringProperty last_name;
    private final ObjectProperty<LocalDate>  Registration_date;

    private final StringProperty payment_status;

    private final StringProperty email;

    public Participation(int participation_id, int marathon_id, String marathonName, int runner_id, String first_name, String last_name, LocalDate registration_date, String payment_status, String email) {
        this.participation_id = new SimpleIntegerProperty(participation_id);
        this.last_name = new SimpleStringProperty(last_name);
        this.first_name = new SimpleStringProperty(first_name);
        this.MarathonName = new SimpleStringProperty(marathonName);
        this.marathon_id = new SimpleIntegerProperty(marathon_id);
        this.runner_id = new SimpleIntegerProperty(runner_id);
        this.Registration_date = new SimpleObjectProperty<>(registration_date);
        this.payment_status = new SimpleStringProperty(payment_status);
        this.email = new SimpleStringProperty(email);
    }

    public Participation(IntegerProperty participation_id, int marathon_id, String marathonName, int runner_id, StringProperty first_name, StringProperty last_name, LocalDate registration_date, String payment_status, StringProperty email) {
        this.participation_id = participation_id;
        this.marathon_id = new SimpleIntegerProperty(marathon_id);
        this.MarathonName = new SimpleStringProperty(marathonName);
        this.runner_id = new SimpleIntegerProperty(runner_id);
        this.first_name = first_name;
        this.last_name = last_name;
        this.Registration_date = new SimpleObjectProperty<>(registration_date);
        this.payment_status = new SimpleStringProperty(payment_status);
        this.email = email;
    }


    public String getPayment_status() {
        return payment_status.get();
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

    public int getParticipation_id() {
        return participation_id.get();
    }

    public IntegerProperty participation_idProperty() {
        return participation_id;
    }

    public void setParticipation_id(int participation_id) {
        this.participation_id.set(participation_id);
    }

    public int getMarathon_id() {
        return marathon_id.get();
    }

    public IntegerProperty marathon_idProperty() {
        return marathon_id;
    }

    public void setMarathon_id(int marathon_id) {
        this.marathon_id.set(marathon_id);
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

    public int getRunner_id() {
        return runner_id.get();
    }

    public IntegerProperty runner_idProperty() {
        return runner_id;
    }

    public void setRunner_id(int runner_id) {
        this.runner_id.set(runner_id);
    }

    public String getFirst_name() {
        return first_name.get();
    }

    public StringProperty first_nameProperty() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name.set(first_name);
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

    public LocalDate getRegistration_date() {
        return Registration_date.get();
    }

    public ObjectProperty<LocalDate> registration_dateProperty() {
        return Registration_date;
    }

    public void setRegistration_date(LocalDate registration_date) {
        this.Registration_date.set(registration_date);
    }

    public String isPayment_status() {
        return payment_status.get();
    }

    public StringProperty payment_statusProperty() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status.set(payment_status);
    }
}
