package ch.ost.rj.mge.solarquiz.questions;

import java.io.Serializable;

public class Question implements Serializable {
    String questionText;
    String dialogText;
    String dialogTitle;

    public Question() {
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getDialogText() {
        return dialogText;
    }

    public void setDialogText(String dialogText) {
        this.dialogText = dialogText;
    }

    public String getDialogTitle() {
        return dialogTitle;
    }

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }
}
