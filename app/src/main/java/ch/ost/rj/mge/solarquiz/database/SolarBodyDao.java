package ch.ost.rj.mge.solarquiz.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.security.Key;
import java.util.List;

@Dao
public abstract class SolarBodyDao {

    public void addSolarBodyComplete(SolarBodyComplete sbc) {
        String bodyId = sbc.getBody().getId();
        List<KeyValue> moons = sbc.getMoons();
        if(moons != null) {
            addAllMoons(moons);
            for (KeyValue moon : moons) {
                BodyKeyValueCrossref bkvc = new BodyKeyValueCrossref();
                bkvc.setId(bodyId);
                bkvc.setKey(moon.getKey());
                addCrossRefs(bkvc);
            }
        }

        // Todo maybe move this up if foreing key constraints are violated
        addValueBody(sbc.getBody());
    }

    @Query("SELECT * FROM SolarBodyValues")
    abstract List<SolarBodyComplete> getAll();

    // FIXME Insert?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void addAllMoons(List<KeyValue> moons);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    abstract void addValueBody(SolarBodyValues sbv);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void addCrossRefs(BodyKeyValueCrossref bkvc);
}
