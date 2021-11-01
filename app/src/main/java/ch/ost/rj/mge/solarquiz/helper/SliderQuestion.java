package ch.ost.rj.mge.solarquiz.helper;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class SliderQuestion implements Serializable {
    int startValue;
    int stepSize;
    int answerPlacement;
    int userGuessPlacement;

    public SliderQuestion(int startValue, int stepSize, int answerPlacement) {
        this.startValue = startValue;
        this.stepSize = stepSize;
        this.answerPlacement = answerPlacement;
    }

    public int getUserGuessValue() {
        return startValue + stepSize * userGuessPlacement;
    }

    public int getAnswerValue() {
        return startValue + stepSize * answerPlacement;
    }

    public int getStartValue() {
        return startValue;
    }

    public void setStartValue(int startValue) {
        this.startValue = startValue;
    }

    public int getStepSize() {
        return stepSize;
    }

    public void setStepSize(int stepSize) {
        this.stepSize = stepSize;
    }

    public int getAnswerPlacement() {
        return answerPlacement;
    }

    public void setAnswerPlacement(int answerPlacement) {
        this.answerPlacement = answerPlacement;
    }

    public int getUserGuessPlacement() {
        return userGuessPlacement;
    }

    public void setUserGuessPlacement(int userGuessPlacement) {
        this.userGuessPlacement = userGuessPlacement;
    }
}
