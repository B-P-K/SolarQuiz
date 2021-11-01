package ch.ost.rj.mge.solarquiz.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public abstract class SolarBodyDao {

    public void addSolarBodyWithMoons(SolarBodyWithMoons sbm) {
        if(sbm.getMoons() != null) {
            for(Moon moon : sbm.getMoons()) {
                moon.setSolarBodyId(sbm.getBody().getId());
            }
            addAllMoons(sbm.getMoons());
        }
        addSolarBody(sbm.getBody());
    }

    @Query("select * from SolarBody where meanRadius > :meanRadius")
    public abstract List<SolarBodyWithMoons> getAllWithMeanRadiusBiggerThan(int meanRadius);

    @Query("select * from SolarBody where discoveredBy is not null")
    public abstract List<SolarBodyWithMoons> getAllWhereDiscoveredByIsNotNull();

    @Transaction
    @Query("SELECT * FROM SolarBody")
    public abstract List<SolarBodyWithMoons> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void addAllMoons(List<Moon> moons);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    abstract void addSolarBody(SolarBody sbv);
}
