package ch.ost.rj.mge.solarquiz.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import ch.ost.rj.mge.solarquiz.R;
import ch.ost.rj.mge.solarquiz.database.Moon;
import ch.ost.rj.mge.solarquiz.database.Mass;
import ch.ost.rj.mge.solarquiz.database.Planet;
import ch.ost.rj.mge.solarquiz.database.SolarBodyWithMoons;
import ch.ost.rj.mge.solarquiz.database.SolarBodyDao;
import ch.ost.rj.mge.solarquiz.database.SolarBody;
import ch.ost.rj.mge.solarquiz.database.SolarDatabase;
import ch.ost.rj.mge.solarquiz.database.Volume;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button reqButton = (Button) findViewById(R.id.reqButton);

        reqButton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {


                                         }
                                     }

        );

        requestAllBodiesFromApi();



    }


    public void requestAllBodiesFromApi() {
        String url = "https://api.le-systeme-solaire.net/rest/bodies";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

                        try {
                            Toast.makeText(getApplicationContext(), "Begin parsing", Toast.LENGTH_LONG).show();//display the response on screen
                            List<SolarBodyWithMoons> solarBodies = parseJsonToObjects(response);
                            Toast.makeText(getApplicationContext(), "Parsed done", Toast.LENGTH_LONG).show();//display the response on screen
                            setupDb(solarBodies);
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "FAIL", Toast.LENGTH_LONG).show();//display the response on screen
                            Log.e("JSON FAILURE", e.toString(),e);
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("Error", error.toString());
                    }
                });
        queue.add(request);
    }

    public List<SolarBodyWithMoons> parseJsonToObjects(JSONObject json) throws JSONException {
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

    public Planet parseJsonAroundPlanetToPojo(JSONObject aroundPlanetJson) throws JSONException {
        Planet aroundPlanet = new Planet();
        aroundPlanet.setPlanet(aroundPlanetJson.getString("planet"));
        aroundPlanet.setRel(aroundPlanetJson.getString("rel"));
        return aroundPlanet;
    }

    public List<Moon> parseJsonMoonArrToList(JSONArray moonJsonArr) throws JSONException {
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

    public Mass parseJsonMassToPojo(JSONObject massJsonObj) throws JSONException {
        Mass mass = new Mass();
        mass.setMassValue(massJsonObj.getInt("massValue"));
        mass.setMassExponent(massJsonObj.getInt("massExponent"));
        return mass;
    }

    public Volume parseJsonVolumeToPojo(JSONObject volJsonObj) throws JSONException {
        Volume vol = new Volume();
        vol.setVolValue(volJsonObj.getInt("volValue"));
        vol.setVolExponent(volJsonObj.getInt("volExponent"));
        return vol;
    }

    public SolarBody parseJsonToSolarBodyPojo(JSONObject bodyJson) throws JSONException {
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

    public void setupDb(List<SolarBodyWithMoons> solarBodies) {
        this.deleteDatabase("database-name");
        this.deleteDatabase("solar-db");

        for(SolarBodyWithMoons sbm : solarBodies) {
            if(sbm.getMoons() != null) {
                Toast.makeText(getApplicationContext(), "MOON NOT NULL", Toast.LENGTH_LONG).show();//display the response on screen
            }
        }

        // FIXME DO NOT RUN IN MAIN THREAD
        SolarDatabase db = Room.databaseBuilder(getApplicationContext(),
                SolarDatabase.class, "solar-db").allowMainThreadQueries().build();
        SolarBodyDao solarBodyDao = db.solarBodyDao();
        for(SolarBodyWithMoons sbc : solarBodies) {
            solarBodyDao.addSolarBodyWithMoons(sbc);
        }
        Toast.makeText(getApplicationContext(), "INSERTS INTO DB DONE", Toast.LENGTH_LONG).show();//display the response on screen

    }


}