package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ChronoAgent {

        private IntegerProperty agentId;
        private StringProperty name;
        private StringProperty cin;
        private IntegerProperty marathonId;

        public ChronoAgent(int agentId, String name, int marathonId, String cin) {
            this.agentId = new SimpleIntegerProperty(agentId);
            this.name = new SimpleStringProperty(name);
            this.cin = new SimpleStringProperty(cin);
            this.marathonId = new SimpleIntegerProperty(marathonId);
        }

    public IntegerProperty getMarathon_id() {
        return marathonId;
    }

    public void setMarathon_id(IntegerProperty marathon_id) {
        this.marathonId  = marathon_id;
    }

    public IntegerProperty agentIdProperty() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId.set(agentId);
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

    public String getCin() {
        return cin.get();
    }

    public StringProperty cinProperty() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin.set(cin);
    }

    public int getMarathonId() {
        return marathonId.get();
    }

    public IntegerProperty marathonIdProperty() {
        return marathonId;
    }

    public void setMarathonId(int marathonId) {
        this.marathonId.set(marathonId);
    }
}

