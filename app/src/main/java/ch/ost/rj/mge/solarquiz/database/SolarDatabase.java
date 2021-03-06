package ch.ost.rj.mge.solarquiz.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Moon.class, SolarBody.class}, version = 1)
public abstract class SolarDatabase extends RoomDatabase {
    public abstract SolarBodyDao solarBodyDao();
}

