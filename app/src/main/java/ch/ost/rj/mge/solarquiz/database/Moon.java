package ch.ost.rj.mge.solarquiz.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Moon {
    @PrimaryKey @NonNull
    String moon;
    String rel;

    @NonNull
    public String getMoon() {
        return moon;
    }

    public void setMoon(@NonNull String moon) {
        this.moon = moon;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }
}
