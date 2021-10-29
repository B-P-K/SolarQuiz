package ch.ost.rj.mge.solarquiz.database;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SolarBody {
    // Incomplete, the api provides more attributes
    @PrimaryKey @NonNull
    String id;
    String name;
    String englishName;
    boolean isPlanet;

    int inclination;
    @Embedded
    Mass mass;
    @Embedded
    Volume vol;
    int density;
    int meanRadius;
    int equaRadius;
    String dimension;

    String discoveredBy;
    String discoveryDate; // FIXME Use iso
    int avgTemp;
    String rel;

}
