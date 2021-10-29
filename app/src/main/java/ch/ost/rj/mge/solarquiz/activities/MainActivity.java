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

import org.json.JSONObject;

import java.util.List;

import ch.ost.rj.mge.solarquiz.R;
import ch.ost.rj.mge.solarquiz.database.BodyWithKeyValues;
import ch.ost.rj.mge.solarquiz.database.SolarBody;
import ch.ost.rj.mge.solarquiz.database.SolarBodyDao;
import ch.ost.rj.mge.solarquiz.database.SolarDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button reqButton = (Button) findViewById(R.id.reqButton);

        reqButton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {

                                             requestAllBodiesFromApi();

                                         }
                                     }

        );
    }

    public void requestAllBodiesFromApi() {
        String url = "https://api.le-systeme-solaire.net/rest/bodies";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
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

    public void setupDb() {
        SolarDatabase db = Room.databaseBuilder(getApplicationContext(),
                SolarDatabase.class, "database-name").build();
        SolarBodyDao solarBodyDao = db.solarBodyDao();
        List<BodyWithKeyValues> solarBodies = solarBodyDao.getAll();
    }
}