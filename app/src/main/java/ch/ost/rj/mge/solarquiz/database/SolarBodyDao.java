package ch.ost.rj.mge.solarquiz.database;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SolarBodyDao {
    @Query("SELECT * FROM SolarBody")
    List<BodyWithKeyValues> getAll();
}
