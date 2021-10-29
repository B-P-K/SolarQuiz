package ch.ost.rj.mge.solarquiz.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"id", "key"})
public class BodyKeyValueCrossref {
    @NonNull
    public String id;
    @NonNull
    public String key;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getKey() {
        return key;
    }

    public void setKey(@NonNull String key) {
        this.key = key;
    }
}
