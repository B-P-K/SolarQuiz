package ch.ost.rj.mge.solarquiz.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import ch.ost.rj.mge.solarquiz.database.Mass;
import ch.ost.rj.mge.solarquiz.database.Moon;
import ch.ost.rj.mge.solarquiz.database.Planet;
import ch.ost.rj.mge.solarquiz.database.SolarBody;
import ch.ost.rj.mge.solarquiz.database.SolarBodyWithMoons;
import ch.ost.rj.mge.solarquiz.database.Volume;

public class JsonHelper {
    public static List<SolarBodyWithMoons> parseJsonToObjects(JSONObject json) throws JSONException {
        JSONArray bodiesJsonArr = json.getJSONArray("bodies");
        List<SolarBodyWithMoons> solarBodies = new LinkedList<>();
        for(int i = 0; i < bodiesJsonArr.length(); i++) {
            JSONObject bodyJson = bodiesJsonArr.getJSONObject(i);

            // Add value types
            SolarBody solarBodyVal = parseJsonToSolarBodyPojo(bodyJson);

            // Add reference types
            SolarBodyWithMoons solarBodyWithMoons = new SolarBodyWithMoons();
            solarBodyWithMoons.setBody(solarBodyVal);
            if(!bodyJson.isNull("moons")) {
                List<Moon> moons = parseJsonMoonArrToList(bodyJson.getJSONArray("moons"));
                solarBodyWithMoons.setMoons(moons);
            }

            solarBodies.add(solarBodyWithMoons);
        }
        return solarBodies;
    }

    private static Planet parseJsonAroundPlanetToPojo(JSONObject aroundPlanetJson) throws JSONException {
        Planet aroundPlanet = new Planet();
        aroundPlanet.setPlanet(aroundPlanetJson.getString("planet"));
        aroundPlanet.setRel(aroundPlanetJson.getString("rel"));
        return aroundPlanet;
    }

    private static List<Moon> parseJsonMoonArrToList(JSONArray moonJsonArr) throws JSONException {
        LinkedList<Moon> moons = new LinkedList<>();
        for(int i = 0; i < moonJsonArr.length(); i++) {
            Moon currMoon = new Moon();
            if  (moonJsonArr.getJSONObject(i) != null) {
                currMoon.setMoon(moonJsonArr.getJSONObject(i).getString("moon"));
                currMoon.setRel(moonJsonArr.getJSONObject(i).getString("rel"));
                moons.add(currMoon);
            }
        }
        if(moons.isEmpty()) {
            return null;
        }
        return moons;
    }

    private static Mass parseJsonMassToPojo(JSONObject massJsonObj) throws JSONException {
        Mass mass = new Mass();
        mass.setMassValue(massJsonObj.getInt("massValue"));
        mass.setMassExponent(massJsonObj.getInt("massExponent"));
        return mass;
    }

    private static Volume parseJsonVolumeToPojo(JSONObject volJsonObj) throws JSONException {
        Volume vol = new Volume();
        vol.setVolValue(volJsonObj.getInt("volValue"));
        vol.setVolExponent(volJsonObj.getInt("volExponent"));
        return vol;
    }

    private static SolarBody parseJsonToSolarBodyPojo(JSONObject bodyJson) throws JSONException {
        SolarBody solarBodyVal = new SolarBody();
        solarBodyVal.setId(bodyJson.getString("id"));
        solarBodyVal.setName(bodyJson.getString("name"));
        solarBodyVal.setEnglishName(bodyJson.getString("englishName"));
        solarBodyVal.setPlanet(bodyJson.getBoolean("isPlanet"));
        solarBodyVal.setInclination(bodyJson.getInt("inclination"));
        solarBodyVal.setDensity(bodyJson.getInt("density"));
        solarBodyVal.setGravity(bodyJson.getInt("gravity"));
        solarBodyVal.setMeanRadius(bodyJson.getInt("meanRadius"));
        solarBodyVal.setEquaRadius(bodyJson.getInt("equaRadius"));
        solarBodyVal.setDimension(bodyJson.getString("dimension"));
        solarBodyVal.setDiscoveredBy(bodyJson.getString("discoveredBy"));
        solarBodyVal.setDiscoveryDate(bodyJson.getString("discoveryDate"));
        solarBodyVal.setAvgTemp(bodyJson.getInt("avgTemp"));
        solarBodyVal.setRel(bodyJson.getString("rel"));

        // build embedded objects
        if(!bodyJson.isNull("mass")) {
            Mass mass = parseJsonMassToPojo(bodyJson.getJSONObject("mass"));
            solarBodyVal.setMass(mass);
        }
        if(!bodyJson.isNull("aroundPlanet")) {
            Planet aroundPlanet = parseJsonAroundPlanetToPojo(bodyJson.getJSONObject("aroundPlanet"));
            solarBodyVal.setAroundPlanet(aroundPlanet);
        }
        if(!bodyJson.isNull("vol")) {
            Volume volume = parseJsonVolumeToPojo(bodyJson.getJSONObject("vol"));
            solarBodyVal.setVol(volume);
        }
        return solarBodyVal;
    }
}
