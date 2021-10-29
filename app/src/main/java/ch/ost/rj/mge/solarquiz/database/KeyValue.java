package ch.ost.rj.mge.solarquiz.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class KeyValue {
    @PrimaryKey @NonNull
    String key;
    String val;
}
