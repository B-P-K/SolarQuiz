package ch.ost.rj.mge.solarquiz.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class SolarBodyWithMoons {
    @Embedded
    SolarBody body;

    @Relation(
            parentColumn = "id",
            entityColumn = "moon"
    )
    List<Moon> moons; // key: moon, val: rel

    public SolarBody getBody() {
        return body;
    }

    public void setBody(SolarBody body) {
        this.body = body;
    }

    public List<Moon> getMoons() {
        return moons;
    }

    public void setMoons(List<Moon> moons) {
        this.moons = moons;
    }
}
