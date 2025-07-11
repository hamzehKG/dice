import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DiceGameSimulation {

    static final int SIMULATIONS_TO_RUN = 1000;
    static final int NUM_OF_DICE = 5;

    /**
     * Runs a single iteration of the dice board game
     * @param initialDiceCount - The initial number of dices in the game
     * @return The final score of the game
     */
    public static int playBoardGame(int initialDiceCount){
        int finalScore = 0;
        Random random = new Random();
        while (initialDiceCount > 0) {

            List<Integer> rolls = new ArrayList<>();
            int threeCounter = 0;

            // Roll all the dices on the board
            for (int i = 0; i < initialDiceCount; i++) {
                int currentRoll = random.nextInt(6)+1;
                rolls.add(currentRoll);

                // Check if a 3 exists and add it to the counter
                if (currentRoll == 3) {
                    threeCounter += 1;
                }
            }

            if (threeCounter > 0) {
                // If there are any 3’s, all the 3’s are taken off the board and a score of 0 is awarded
                initialDiceCount -= threeCounter;
            } else {
                // If there are not any 3’s, the lowest die is taken off the board and the value
                // of that lowest die is awarded for this roll.
                int lowestValue = Collections.min(rolls);
                finalScore += lowestValue;
                initialDiceCount -= 1;
            }

        }

        return finalScore;
    }

    /**
     * Run the simulation based on the parameters given and logs the result
     * @param numOfRuns The total number of games to simulate
     * @param numOfDices The number of dices each game will be played with
     */
    public static void runSimulation(int numOfRuns, int numOfDices) {
        System.out.printf("Number of simulations was %d using %d dice.\n", numOfRuns, numOfDices);
        long start = System.currentTimeMillis();

        Map<Integer, Integer> scores = new HashMap<>();

        for (int i = 0; i < numOfRuns; i++) {
            int score = playBoardGame(numOfDices);

            // Increment the score for the game we just played
            scores.merge(score, 1, Integer::sum);
        }

        long finish = System.currentTimeMillis();
        double timeElapsed = (finish - start) / 1000.0;

        // Sort and log the final scores alongside their frequencies
        List<Integer> sortedScores = new ArrayList<>(scores.keySet());
        Collections.sort(sortedScores);

        // Logging of the final result
        for (Integer score : sortedScores) {
            int count = scores.get(score);
            double proportion = (double) count / numOfRuns;
            System.out.printf("Total %d occurs %.4f occurred %d times.%n", score, proportion, count);
        }

        System.out.printf("Total simulation took %.3f seconds.%n", timeElapsed);
    }

    public static void main(String[] args) {
        runSimulation(SIMULATIONS_TO_RUN, NUM_OF_DICE);
    }
}