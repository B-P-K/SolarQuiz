package ch.ost.rj.mge.solarquiz.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public abstract class SolarBodyDao {

    // FIXME
    public void addSolarBodyWithMoons(SolarBodyWithMoons sbm) {
        if(sbm.getMoons() != null) {
            addAllMoons(sbm.getMoons());
        }
        addSolarBody(sbm.getBody());

        /*
        String bodyId = sbc.getBody().getId();
        List<Moon> moons = sbc.getMoons();
        if(moons != null) {
            addAllMoons(moons);
            for (Moon moon : moons) {
                bkvc.setId(bodyId);
                bkvc.setKey(moon.getMoon());
                addCrossRefs(bkvc);
            }
        }

        // Todo maybe move this up if foreing key constraints are violated
        addValueBody(sbc.getBody());
        */
    }

    @Transaction
    @Query("SELECT * FROM SolarBody")
    abstract List<SolarBodyWithMoons> getAll();

    // FIXME Insert?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void addAllMoons(List<Moon> moons);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    abstract void addSolarBody(SolarBody sbv);
}
