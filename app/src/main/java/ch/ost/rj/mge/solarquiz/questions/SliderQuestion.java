package ch.ost.rj.mge.solarquiz.questions;

public class SliderQuestion extends Question {
    int startValue;
    int stepSize;
    int correctAnswerPlacement;
    int userGuessPlacement;

    public SliderQuestion(String questionText, int startValue, int stepSize, int correctAnswerPlacement) {
        this.questionText = questionText;
        this.startValue = startValue;
        this.stepSize = stepSize;
        this.correctAnswerPlacement = correctAnswerPlacement;
    }

    public int getUserGuessValue() {
        return startValue + stepSize * userGuessPlacement;
    }

    public int getCorrectAnswerValue() {
        return startValue + stepSize * correctAnswerPlacement;
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

    public int getCorrectAnswerPlacement() {
        return correctAnswerPlacement;
    }

    public void setCorrectAnswerPlacement(int correctAnswerPlacement) {
        this.correctAnswerPlacement = correctAnswerPlacement;
    }

    public int getUserGuessPlacement() {
        return userGuessPlacement;
    }

    public void setUserGuessPlacement(int userGuessPlacement) {
        this.userGuessPlacement = userGuessPlacement;
    }
}
