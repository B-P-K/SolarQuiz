package ch.ost.rj.mge.solarquiz.database;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class BodyWithKeyValues {
    @Embedded
    SolarBody body;
    /* FIXME
    @Relation(
            parentColumn = "id",
            entityColumn = "key",
            associateBy = @Junction(BodyKeyValueCrossref.class)
    )
    KeyValue[] aroundPlanet; // key: planet, val: rel
    */
    @Relation(
            parentColumn = "id",
            entityColumn = "key",
            associateBy = @Junction(BodyKeyValueCrossref.class)
    )
    List<KeyValue> moons; // key: moon, val: rel
}
