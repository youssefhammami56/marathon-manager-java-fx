package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ranking {
    public StringProperty marathonname;
    public StringProperty runnername;
    public StringProperty rank;

    public Ranking(StringProperty marathonname, StringProperty runnername, StringProperty rank) {
        this.marathonname = marathonname;
        this.runnername = runnername;
        this.rank = rank;
    }

    public Ranking(String marathonname, String runnername, String rank) {
        this.marathonname = new SimpleStringProperty(marathonname);
        this.runnername = new SimpleStringProperty(runnername);
        this.rank = new SimpleStringProperty(rank);
    }

    public String getMarathonname() {
        return marathonname.get();
    }

    public StringProperty marathonnameProperty() {
        return marathonname;
    }

    public void setMarathonname(String marathonname) {
        this.marathonname.set(marathonname);
    }

    public String getRunnername() {
        return runnername.get();
    }

    public StringProperty runnernameProperty() {
        return runnername;
    }

    public void setRunnername(String runnername) {
        this.runnername.set(runnername);
    }

    public String getRank() {
        return rank.get();
    }

    public StringProperty rankProperty() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank.set(rank);
    }
}
