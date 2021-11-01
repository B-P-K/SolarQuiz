package ch.ost.rj.mge.solarquiz.activities;

import java.lang.*;
import java.util.Random;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ch.ost.rj.mge.solarquiz.App;
import ch.ost.rj.mge.solarquiz.R;
import ch.ost.rj.mge.solarquiz.fragments.SeekBarFragment;
import ch.ost.rj.mge.solarquiz.fragments.SingleChoiceFragment;
import ch.ost.rj.mge.solarquiz.fragments.TextViewFragment;
import ch.ost.rj.mge.solarquiz.helper.DataInterface;
import ch.ost.rj.mge.solarquiz.helper.DialogHelper;
import ch.ost.rj.mge.solarquiz.questions.Question;
import ch.ost.rj.mge.solarquiz.questions.QuestionGenerator;
import ch.ost.rj.mge.solarquiz.questions.SingleChoiceQuestion;
import ch.ost.rj.mge.solarquiz.questions.SliderQuestion;
import ch.ost.rj.mge.solarquiz.questions.TextViewQuestion;

public class QuizActivity extends AppCompatActivity implements DataInterface {
    TextView questionTextView;
    Random generator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        generator = new Random();
        setContentView(R.layout.activity_quiz);
        questionTextView = findViewById(R.id.questionTextView);

        // FIXME Really ugly hack
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                waitUntilDBSetup();
            }
        }, 5);
    }

    public void waitUntilDBSetup(){
        if(App.db != null && App.db.solarBodyDao().getAll().size() != 0){
            showRandomQuestion();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.i("hello", "waiting for db to be set up");
                    waitUntilDBSetup();
                }
            }, 5);
        }
    }

    public void showRandomQuestion() {
        int type = generator.nextInt(3);
        switch (type) {
            case 0:
                showSingleChoiceQuestion();
                break;
            case 1:
                showTextViewQuestion();
                break;
            case 2:
                showSliderQuestion();
                break;
        }
    }

    public void showSingleChoiceQuestion() {
        SingleChoiceQuestion textViewQuestion = QuestionGenerator.generateSingleChoiceQuestion(getApplicationContext());
        questionTextView.setText(textViewQuestion.getQuestionText());
        startSingleChoiceFragment(textViewQuestion);
    }

    public void showTextViewQuestion() {
        TextViewQuestion textViewQuestion = QuestionGenerator.generateTextViewQuestion(getApplicationContext());
        questionTextView.setText(textViewQuestion.getQuestionText());
        startTextViewFragment(textViewQuestion);
    }

    public void showSliderQuestion() {
        SliderQuestion sliderQuestion = QuestionGenerator.generateSliderQuestion(getApplicationContext());
        questionTextView.setText(sliderQuestion.getQuestionText());
        startSliderFragment(sliderQuestion);
    }

    @Override
    public void onResult(Question result) {
        showAnswerDialog(result);
    }

    public void showAnswerDialog(Question question) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ImageView astronaut = DialogHelper.getAppropriateAstronautImage(this, question.getDialogTitle());

        builder.setView(astronaut).setMessage(question.getDialogText()).setTitle(question.getDialogTitle())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        showRandomQuestion(); // Next question
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

    public void startTextViewFragment(TextViewQuestion textViewQuestion) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        TextViewFragment textViewFragment = TextViewFragment.newInstance(textViewQuestion);
        ft.replace(R.id.fragmentContainerView, textViewFragment);
        ft.commit();
    }

    public void startSingleChoiceFragment(SingleChoiceQuestion singleChoiceQuestion) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        SingleChoiceFragment singleChoiceFragment = SingleChoiceFragment.newInstance(singleChoiceQuestion);
        ft.replace(R.id.fragmentContainerView, singleChoiceFragment);
        ft.commit();
    }
}