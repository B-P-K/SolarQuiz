package ch.ost.rj.mge.solarquiz.helper;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import ch.ost.rj.mge.solarquiz.database.SolarBodyDao;
import ch.ost.rj.mge.solarquiz.database.SolarBodyWithMoons;
import ch.ost.rj.mge.solarquiz.database.SolarDatabase;

public class QuestionGenerator {

    public static SliderQuestion generateSliderQuestion(Context context) {
        Random generator = new Random();
        SolarDatabase db = Room.databaseBuilder(context,
                SolarDatabase.class, "solar-db").allowMainThreadQueries().build();
        SolarBodyDao solarBodyDao = db.solarBodyDao();
        List<SolarBodyWithMoons> sbm = solarBodyDao.getAllWithMeanRadiusBiggerThan(0);
        // FIXME Empty list returned
        int randOne = generator.nextInt(sbm.size());
        int randTwo = generator.nextInt(sbm.size());
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

        int answer = sbOne.getBody().getMeanRadius() / sbTwo.getBody().getMeanRadius();
        int step = (int) Math.pow(10, Math.floor(Math.log10(answer)));
        // contains index of correct answer in seekbar
        int answerPlacement = generator.nextInt(Math.min(10,answer/step));

        return new SliderQuestion(question,answer - answerPlacement * step, step, answerPlacement);
    }
}
