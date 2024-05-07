import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Dice Game!");

        // Get game settings from the user
        int numPlayers = getIntInput(scanner, "Enter the number of players: ");
        int numDiceSides = getIntInput(scanner, "Enter the number of sides on the dice: ");
        int numRounds = getIntInput(scanner, "Enter the number of rounds: ");

        // Initialize players and dice
        Player[] players = initializePlayers(scanner, numPlayers);
        Dice dice = new Dice(numDiceSides);

        // Play the game
        playGame(players, dice, numRounds);

        // Display final results
        displayFinalResults(players);
    }

    private static int getIntInput(Scanner scanner, String prompt) {
        int input = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt);
            try {
                input = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return input;
    }

    private static Player[] initializePlayers(Scanner scanner, int numPlayers) {
        Player[] players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter name for player " + (i + 1) + ": ");
            String playerName = scanner.nextLine();
            players[i] = new Player(playerName);
        }
        return players;
    }

    private static void playGame(Player[] players, Dice dice, int numRounds) {
        System.out.println("\nLet's start the game!");

        for (int round = 1; round <= numRounds; round++) {
            System.out.println("\nRound " + round + ":");
            playRound(players, dice);
            displayRoundResults(players);
        }
    }

    private static void playRound(Player[] players, Dice dice) {
        int highestRoll = 0;

        for (Player player : players) {
            int roll = dice.roll();
            System.out.println(player.getName() + " rolled a " + roll);
            if (roll > highestRoll) {
                highestRoll = roll;
            }
            player.addToScore(roll);
        }

        for (Player player : players) {
            if (player.getScore() == highestRoll) {
                player.incrementVictories();
            }
        }
    }

    private static void displayRoundResults(Player[] players) {
        for (Player player : players) {
            System.out.println(player.getName() + "'s total score: " + player.getScore());
        }
    }

    private static void displayFinalResults(Player[] players) {
        System.out.println("\nFinal Results:");
        for (Player player : players) {
            System.out.println(player.getName() + " - Victories: " + player.getVictories() + ", Average Roll: " + player.getAverageRoll());
        }
    }
}

class Dice {
    private final Random random;
    private final int sides;

    public Dice(int sides) {
        this.sides = sides;
        this.random = new Random();
    }

    public int roll() {
        return random.nextInt(sides) + 1;
    }
}

class Player {
    private final String name;
    private int score;
    private int victories;
    private int rollsCount;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.victories = 0;
        this.rollsCount = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addToScore(int value) {
        score += value;
        rollsCount++;
    }

    public void incrementVictories() {
        victories++;
    }

    public int getVictories() {
        return victories;
    }

    public double getAverageRoll() {
        return rollsCount > 0 ? (double) score / rollsCount : 0;
    }
}
