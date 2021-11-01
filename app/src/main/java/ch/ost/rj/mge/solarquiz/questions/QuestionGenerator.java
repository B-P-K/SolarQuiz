package ch.ost.rj.mge.solarquiz.questions;

import android.content.Context;

import androidx.room.Room;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import ch.ost.rj.mge.solarquiz.database.SolarBody;
import ch.ost.rj.mge.solarquiz.database.SolarBodyDao;
import ch.ost.rj.mge.solarquiz.database.SolarBodyWithMoons;
import ch.ost.rj.mge.solarquiz.database.SolarDatabase;

public class QuestionGenerator {
    static Random generator;

    public static SliderQuestion generateSliderQuestion(Context context) {
        generator = new Random();
        SolarDatabase db = Room.databaseBuilder(context,
                SolarDatabase.class, "solar-db").allowMainThreadQueries().build();
        SolarBodyDao solarBodyDao = db.solarBodyDao();
        List<SolarBodyWithMoons> sbm = solarBodyDao.getAllWithMeanRadiusBiggerThan(0);
        // FIXME Empty list returned
        int randOne = generator.nextInt(sbm.size());
        int randTwo = generator.nextInt(sbm.size());
        while(randOne == randTwo) {
            randTwo = generator.nextInt(sbm.size());
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

        String name1 = sbOne.getBody().getEnglishName();
        if(name1 == null || name1.equals("")) {
            name1 = sbOne.getBody().getId();
        }
        String name2 = sbTwo.getBody().getEnglishName();
        if(name2 == null || name2.equals("")) {
            name2 = sbTwo.getBody().getId();
        }
        String question = "How many times could " + name2 + " fit into " +
                name1 + ", based on its mean radius? Round your answer down.";

        int answer = sbOne.getBody().getMeanRadius() / sbTwo.getBody().getMeanRadius();
        int step = (int) Math.pow(10, Math.floor(Math.log10(answer)));
        // contains index of correct answer in seekbar
        int answerPlacement = generator.nextInt(Math.min(10,answer/step));

        return new SliderQuestion(question,answer - answerPlacement * step, step, answerPlacement);
    }

    public static TextViewQuestion generateTextViewQuestion(Context context) {
        Random generator = new Random();
        SolarDatabase db = Room.databaseBuilder(context,
                SolarDatabase.class, "solar-db").allowMainThreadQueries().build();
        SolarBodyDao solarBodyDao = db.solarBodyDao();
        List<SolarBodyWithMoons> sbm = solarBodyDao.getAllWhereDiscoveredByIsNotNull();
        int rand = generator.nextInt(sbm.size());
        String discoveredBy= sbm.get(rand).getBody().getDiscoveredBy();
        if (discoveredBy == null || discoveredBy.equals("")) {
            discoveredBy = "no one";
        }
        String name = sbm.get(rand).getBody().getEnglishName();
        if(name == null || name.equals("")) {
            name = sbm.get(rand).getBody().getId();
        }
        String question = "Who discovered " + name + "?";
        TextViewQuestion textViewQuestion = new TextViewQuestion(question, discoveredBy);
        return textViewQuestion;
    }

    public static SingleChoiceQuestion generateSingleChoiceQuestion(Context context) {
        Random generator = new Random();
        SolarDatabase db = Room.databaseBuilder(context,
                SolarDatabase.class, "solar-db").allowMainThreadQueries().build();
        SolarBodyDao solarBodyDao = db.solarBodyDao();
        List<SolarBodyWithMoons> sbm = solarBodyDao.getAll();

        List<SolarBodyWithMoons> planets = solarBodyDao.getAllBodiesWhereIsPlanet(true);
        List<SolarBodyWithMoons> moons = solarBodyDao.getAllBodiesWhereIsPlanet(false);

        generator = new Random();


        String[] answerChoices = new String[4];
        int correctAnswerIndex = generator.nextInt(4);


        for(int i = 0; i < answerChoices.length; i++) {
            int randIndex1;
            SolarBody s;
            if (i == correctAnswerIndex) {
                randIndex1 = generator.nextInt(planets.size());
                s = planets.get(randIndex1).getBody();
            } else {
                randIndex1 = generator.nextInt(moons.size());
                s = moons.get(randIndex1).getBody();
            }

            if (s.getEnglishName() != null && !s.getEnglishName().equals("")) {
                answerChoices[i] = s.getEnglishName();
            } else { // Some solar bodies do not have an english translation
                answerChoices[i] = s.getId();
            }
        }

        int rand = generator.nextInt(sbm.size());
        String question = "Which one of these solar bodies is a (dwarf) planet?";
        SingleChoiceQuestion singleChoiceQuestion = new SingleChoiceQuestion(question, correctAnswerIndex, answerChoices);
        return singleChoiceQuestion;
    }
}
