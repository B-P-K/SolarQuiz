package ch.ost.rj.mge.solarquiz;

import androidx.room.Room;

import android.app.Application;
import android.util.Log;
import android.view.View;
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
import ch.ost.rj.mge.solarquiz.helper.Status;

public class App extends Application {
    public static SolarDatabase db;
    public static Status status = Status.LOADING;

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
                        try {
                            List<SolarBodyWithMoons> solarBodies = JsonHelper.parseJsonToObjects(response);
                            setupDb(solarBodies);
                        } catch (JSONException e) {
                            Log.e("JSON FAILURE", e.toString(),e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                        db = Room.databaseBuilder(getApplicationContext(),
                                SolarDatabase.class, "solar-db").allowMainThreadQueries().build();
                        if(db.solarBodyDao().getAll().size() != 0){
                            status = Status.READY;
                        } else {
                            status = Status.FAILED;
                        }
                    }
                });
        queue.add(request);
    }

    public void setupDb(List<SolarBodyWithMoons> solarBodies) {
        // FIXME IN PRODUCTION: DO NOT RUN IN MAIN THREAD
        // FIXME OFFLINE PERSISTENCE! WHAT IF API UNREACHABLE?
        db = Room.databaseBuilder(getApplicationContext(),
                SolarDatabase.class, "solar-db").allowMainThreadQueries().build();
        SolarBodyDao solarBodyDao = db.solarBodyDao();
        solarBodyDao.addSolarBodiesWithMoons(solarBodies);
        status = Status.READY;
    }
}