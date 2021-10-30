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
    @Embedded(prefix="around_planet_")
    Planet aroundPlanet;
    int density;
    int gravity;
    int meanRadius;
    int equaRadius;
    String dimension;
    String discoveredBy;
    String discoveryDate; // FIXME Use iso
    int avgTemp;
    String rel;

    public String getEnglishName() {
        return englishName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPlanet() {
        return isPlanet;
    }

    public void setPlanet(boolean planet) {
        isPlanet = planet;
    }

    public int getInclination() {
        return inclination;
    }

    public void setInclination(int inclination) {
        this.inclination = inclination;
    }

    public Mass getMass() {
        return mass;
    }

    public void setMass(Mass mass) {
        this.mass = mass;
    }

    public Planet getAroundPlanet() {
        return aroundPlanet;
    }

    public void setAroundPlanet(Planet aroundPlanet) {
        this.aroundPlanet = aroundPlanet;
    }

    public Volume getVol() {
        return vol;
    }

    public void setVol(Volume vol) {
        this.vol = vol;
    }

    public int getDensity() {
        return density;
    }

    public void setDensity(int density) {
        this.density = density;
    }

    public int getMeanRadius() {
        return meanRadius;
    }

    public void setMeanRadius(int meanRadius) {
        this.meanRadius = meanRadius;
    }

    public int getEquaRadius() {
        return equaRadius;
    }

    public void setEquaRadius(int equaRadius) {
        this.equaRadius = equaRadius;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getDiscoveredBy() {
        return discoveredBy;
    }

    public void setDiscoveredBy(String discoveredBy) {
        this.discoveredBy = discoveredBy;
    }

    public String getDiscoveryDate() {
        return discoveryDate;
    }

    public void setDiscoveryDate(String discoveryDate) {
        this.discoveryDate = discoveryDate;
    }

    public int getAvgTemp() {
        return avgTemp;
    }

    public void setAvgTemp(int avgTemp) {
        this.avgTemp = avgTemp;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }
}
