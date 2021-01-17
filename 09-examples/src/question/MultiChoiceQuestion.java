package question;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MultiChoiceQuestion extends ChoiceQuestion {
    List<String> allAnswers;

    public MultiChoiceQuestion() {
        allAnswers = new ArrayList<>();
    }

    public void setAnswer(String answer) {
        allAnswers.add(answer);
    }

    public boolean checkAnswer(String response) {
        List<String> answersSeen = new ArrayList<>();
        Scanner answers = new Scanner(response); // Analyse response with a scanner
        while (answersSeen != null && answers.hasNext()) {
            String answer = answers.next();
            if (allAnswers.contains(answer)) {
                if (!answersSeen.contains(answer)) answersSeen.add(answer); // add reviewed answers
            } else
                answersSeen = null; // exit loop
        }
        return true;
    }

    public void display() {
        System.out.print(allAnswers);
    }
}
