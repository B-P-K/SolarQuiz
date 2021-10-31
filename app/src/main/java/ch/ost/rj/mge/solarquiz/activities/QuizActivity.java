package ch.ost.rj.mge.solarquiz.activities;

import java.util.Random;
import java.lang.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
    TextView questionTextView;
    Random generator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        questionTextView = findViewById(R.id.questionTextView);
        // TODO Generate question
        // TODO Set fragment based on question (and pass params if necessary)
        // TODO Return user input from fragment
        // TODO Validate user input
        // TODO Show result
        generator = new Random();
        generateSliderQuestion();
    }

    public void generateSliderQuestion() {
        SolarDatabase db = Room.databaseBuilder(getApplicationContext(),
                SolarDatabase.class, "solar-db").allowMainThreadQueries().build();
        SolarBodyDao solarBodyDao = db.solarBodyDao();
        List<SolarBodyWithMoons> sbm = solarBodyDao.getAllWithMeanRadiusBiggerThan(0);
        // FIXME Empty list returned
        Log.i("hello", Integer.toString(sbm.size()));
        int randOne = generator.nextInt(sbm.size());
        int randTwo = generator.nextInt(sbm.size());
        Log.i("hello", Integer.toString(randOne) + "randOne");
        Log.i("hello", Integer.toString(randTwo) + "randTwo");
        while(randOne == randTwo) {
            randTwo = ThreadLocalRandom.current().nextInt(sbm.size());
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

        String question = "How much bigger is the mean radius of " + sbOne.getBody().getId() + " than that of " +
                    sbTwo.getBody().getId() + "? Round your answer down.";
        questionTextView.setText(question);

        int answer = sbOne.getBody().getMeanRadius() / sbTwo.getBody().getMeanRadius();
        Log.i("hello", Integer.toString(answer) + "answer");
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