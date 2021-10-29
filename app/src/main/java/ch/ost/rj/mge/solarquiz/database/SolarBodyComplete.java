package ch.ost.rj.mge.solarquiz.database;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class SolarBodyComplete {
    @Embedded
    SolarBodyValues body;

    @Relation(
            parentColumn = "id",
            entityColumn = "key",
            associateBy = @Junction(BodyKeyValueCrossref.class)
    )
    List<KeyValue> moons; // key: moon, val: rel

    public SolarBodyValues getBody() {
        return body;
    }

    public void setBody(SolarBodyValues body) {
        this.body = body;
    }

    public List<KeyValue> getMoons() {
        return moons;
    }

    public void setMoons(List<KeyValue> moons) {
        this.moons = moons;
    }
}
