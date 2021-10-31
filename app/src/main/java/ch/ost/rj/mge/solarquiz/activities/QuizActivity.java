package ch.ost.rj.mge.solarquiz.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import ch.ost.rj.mge.solarquiz.R;
import ch.ost.rj.mge.solarquiz.fragments.SeekBarFragment;
import ch.ost.rj.mge.solarquiz.helper.DataInterface;

public class QuizActivity extends AppCompatActivity implements DataInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setFragment();

        // TODO Generate question
        // TODO Set fragment based on question (and pass params if necessary)
        // TODO Return user input from fragment
        // TODO Validate user input
        // TODO Show result
    }

    @Override
    public void onResult(Object result) { // Receives the user input result from the appropriate fragment
        // TODO
        if(result instanceof String) {

        }
    }

    public void setFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        SeekBarFragment slider = new SeekBarFragment();
        Bundle args = new Bundle();
        args.putInt("max", 15); // TODO Get max based on question
        slider.setArguments(args);
        ft.replace(R.id.fragmentContainerView, slider);
        ft.commit();
    }
}