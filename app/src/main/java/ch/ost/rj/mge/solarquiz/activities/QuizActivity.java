package ch.ost.rj.mge.solarquiz.activities;

import java.util.Random;
import java.lang.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import ch.ost.rj.mge.solarquiz.R;
import ch.ost.rj.mge.solarquiz.database.SolarBodyDao;
import ch.ost.rj.mge.solarquiz.database.SolarBodyWithMoons;
import ch.ost.rj.mge.solarquiz.database.SolarDatabase;
import ch.ost.rj.mge.solarquiz.fragments.SeekBarFragment;
import ch.ost.rj.mge.solarquiz.helper.DataInterface;
import ch.ost.rj.mge.solarquiz.helper.SliderQuestion;

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
        // contains index of correct answer in seekbar
        int answerPlacement = generator.nextInt(Math.min(10,answer/step));
        Log.i("hello", Integer.toString(answerPlacement) + "answerPlacement");

        SliderQuestion sliderQuestion = new SliderQuestion(answer - answerPlacement * step, step, answerPlacement);
        startSliderFragment(sliderQuestion);
    }

    @Override
    public void onResult(Object result) { // Receives the user input result from the appropriate fragment
        if(result instanceof SliderQuestion) { // Result of slider fragment
            SliderQuestion sliderQuestion = (SliderQuestion)result;
            int correctAnswer = sliderQuestion.getAnswerValue();
            int userGuess = sliderQuestion.getUserGuessValue();
            if (correctAnswer == userGuess) {
                showDialog("Correct Answer", "Your guess " + userGuess + " is correct!");
            } else {
                showDialog("Wrong Answer", "Your guess was " + userGuess + ". The correct answer is " + correctAnswer + ".");
            }
        }
    }

    public void showDialog(String title, String answer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(answer).setTitle(title)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // TODO Generate next question
                        generateSliderQuestion();
                    }
                });
        builder.create().show();
    }

    public void startSliderFragment(SliderQuestion sliderQuestion) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        SeekBarFragment slider = SeekBarFragment.newInstance(sliderQuestion);
        ft.replace(R.id.fragmentContainerView, slider);
        ft.commit();
    }
}