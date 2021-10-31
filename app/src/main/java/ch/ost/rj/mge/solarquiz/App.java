package ch.ost.rj.mge.solarquiz;

import androidx.room.Room;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import ch.ost.rj.mge.solarquiz.database.SolarBodyWithMoons;
import ch.ost.rj.mge.solarquiz.database.SolarBodyDao;
import ch.ost.rj.mge.solarquiz.database.SolarDatabase;
import ch.ost.rj.mge.solarquiz.helper.JsonHelper;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        loadSolarBodiesFromApiIntoDb();
    }

    public void loadSolarBodiesFromApiIntoDb() {
        String url = "https://api.le-systeme-solaire.net/rest/bodies";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

                        try {
                            Toast.makeText(getApplicationContext(), "Begin parsing", Toast.LENGTH_LONG).show();//display the response on screen
                            List<SolarBodyWithMoons> solarBodies = JsonHelper.parseJsonToObjects(response);
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



    public void setupDb(List<SolarBodyWithMoons> solarBodies) {
        this.deleteDatabase("database-name");
        this.deleteDatabase("solar-db");

        // FIXME DO NOT RUN IN MAIN THREAD
        SolarDatabase db = Room.databaseBuilder(getApplicationContext(),
                SolarDatabase.class, "solar-db").allowMainThreadQueries().build();
        SolarBodyDao solarBodyDao = db.solarBodyDao();
        for(SolarBodyWithMoons sbc : solarBodies) {
            solarBodyDao.addSolarBodyWithMoons(sbc);
        }
    }
}