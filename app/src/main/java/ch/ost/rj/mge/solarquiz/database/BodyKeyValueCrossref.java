package ch.ost.rj.mge.solarquiz.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(primaryKeys = {"id", "key"})
public class BodyKeyValueCrossref {
    @NonNull
    public String id;
    @NonNull
    public String key;
}
