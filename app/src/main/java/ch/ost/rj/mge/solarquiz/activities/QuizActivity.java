package ch.ost.rj.mge.solarquiz.activities;

import java.util.Random;
import java.lang.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import ch.ost.rj.mge.solarquiz.App;
import ch.ost.rj.mge.solarquiz.R;
import ch.ost.rj.mge.solarquiz.database.SolarBodyDao;
import ch.ost.rj.mge.solarquiz.database.SolarBodyWithMoons;
import ch.ost.rj.mge.solarquiz.database.SolarDatabase;
import ch.ost.rj.mge.solarquiz.fragments.SeekBarFragment;
import ch.ost.rj.mge.solarquiz.helper.DataInterface;

public class QuizActivity extends AppCompatActivity implements DataInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // TODO Generate question
        // TODO Set fragment based on question (and pass params if necessary)
        // TODO Return user input from fragment
        // TODO Validate user input
        // TODO Show result
        generateSliderQuestion();
    }

    public void generateSliderQuestion() {
        SolarDatabase db = Room.databaseBuilder(getApplicationContext(),
                SolarDatabase.class, "solar-db").allowMainThreadQueries().build();
        SolarBodyDao solarBodyDao = db.solarBodyDao();
        List<SolarBodyWithMoons> sbm = solarBodyDao.getAllWithMeanRadiusBiggerThan(0);
        // FIXME Empty list returned
        int randOne = ThreadLocalRandom.current().nextInt(0,sbm.size() + 1);
        int randTwo = ThreadLocalRandom.current().nextInt(0,sbm.size() + 1);
        while(randOne == randTwo) {
            randTwo = ThreadLocalRandom.current().nextInt(0,sbm.size() + 1);
        }

        SolarBodyWithMoons sbOne;
        SolarBodyWithMoons sbTwo;
        // Ensure bodyOne is always solar body with bigger radius
        if(sbm.get(randOne).getBody().getMeanRadius() > sbm.get(randTwo).getBody().getMeanRadius()) {
             sbOne = sbm.get(randOne);
             sbTwo = sbm.get(randTwo);
        } else {
            sbTwo = sbm.get(randOne);
            sbOne = sbm.get(randTwo);
        }

        String question = "How much bigger is the mean radius of " + sbOne.getBody().getId() + " than " +
                    sbTwo.getBody().getId() + "? Round your answer down.";

        int answer = sbOne.getBody().getMeanRadius() / sbTwo.getBody().getMeanRadius();
        Log.i("hello", Integer.toString(answer) + "answer");
        Random generator = new Random();
        int step = (int) Math.pow(10, Math.floor(Math.log10(answer)));
        Log.i("hello", Integer.toString(step) + "step");
        int answerPlacement = generator.nextInt(Math.min(10,answer/step));
        Log.i("hello", Integer.toString(answerPlacement) + "answerPlacement");

        /*int[] stepValues = new int[10];
        int current = answer - answerPlacement * step;
        for(int i = 0; i < 10; i ++) {
            stepValues[i] = current;
            current += step;
        } */
        // TODO Set question
        startSliderFragment(step, answer - answerPlacement * step);

    }

    @Override
    public void onResult(Object result) { // Receives the user input result from the appropriate fragment
        // TODO
        if(result instanceof Integer) { // Result of slider fragment

        }
    }

    public void startSliderFragment(int stepSize, int startValue) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        SeekBarFragment slider = new SeekBarFragment();
        Bundle args = new Bundle();
        args.putInt("stepSize", stepSize);
        args.putInt("startValue", startValue); // TODO
        slider.setArguments(args);
        ft.replace(R.id.fragmentContainerView, slider);
        ft.commit();
    }
}