package comp1140.ass2.selfDevTests;

import comp1140.ass2.ExampleGames;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static comp1140.ass2.CatanDiceExtra.generateAction;
import static comp1140.ass2.CatanDiceExtra.isActionSequenceValid;

/**
 * This is a sanity test to check that the action sequences given by
 * generateAction are valid and within 2 seconds. It uses *your* implementation of the
 * isActionSequenceValid function to check the action sequence given by
 * generateAction. Therefore, if you have not implemented isActionSequenceValid
 * correctly then the test result here is meaningless.
 */
//@Timeout(value = 60000, unit = TimeUnit.MILLISECONDS)
public class GenerateActionSanityTimeTest {
    double TIME_MAX = 1.5;
    private String testGenerateActionSanity(String boardState) {
        String[] actionSequence = generateAction(boardState);
        boolean validity;
        if (actionSequence == null)
            validity = true;
        else
            validity = isActionSequenceValid(boardState, actionSequence);

        Assertions.assertTrue(
                validity,
                "generated action sequence " + Arrays.toString(actionSequence) + " on board state " + boardState
        );

        return Arrays.toString(actionSequence);
    }

    @Test
    public void testFullGame1() {
        for (String[][] step : ExampleGames.FULL_GAME_WITH_ACTIONS1) {

            long start = System.nanoTime();
            String action = testGenerateActionSanity(step[0][0]);
            long diff = System.nanoTime() - start;

            double timeInSec = (double) diff / 1_000_000_000;
            System.out.println("Board State: " + step[0][0] + "    Action: " + action);
            System.out.println("Time Taken: " + timeInSec);
            System.out.println();
            Assertions.assertTrue(timeInSec < TIME_MAX); // Checking less than 2 seconds
        }
    }

    @Test
    public void testFullGame2() {
        for (String[][] step : ExampleGames.FULL_GAME_WITH_ACTIONS2) {

            long start = System.nanoTime();
            String action = testGenerateActionSanity(step[0][0]);
            long diff = System.nanoTime() - start;

            double timeInSec = (double) diff / 1_000_000_000;
            System.out.println("Board State: " + step[0][0] + "    Action: " + action);
            System.out.println("Time Taken: " + timeInSec);
            System.out.println();
            Assertions.assertTrue(timeInSec < TIME_MAX); // Checking less than 2 seconds
        }
    }

    @Test
    public void testFullGame3() {
        for (String[][] step : ExampleGames.FULL_GAME_WITH_ACTIONS3) {

            long start = System.nanoTime();
            String action = testGenerateActionSanity(step[0][0]);
            long diff = System.nanoTime() - start;

            double timeInSec = (double) diff / 1_000_000_000;
            System.out.println("Board State: " + step[0][0] + "    Action: " + action);
            System.out.println("Time Taken: " + timeInSec);
            System.out.println();
            Assertions.assertTrue(timeInSec < TIME_MAX); // Checking less than 2 seconds
        }
    }
}

