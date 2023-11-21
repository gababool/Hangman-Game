import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hangman {

    static String filePath = "words.txt";
    static List<String> wordList = readWordList();
    final static Scanner sc = new Scanner(System.in);
    static int count;

    public static void main(String[] args) {
        System.out.println("Welcome to Hangman!");
        String word = pickRandomWord().toLowerCase();
        play(word);
    }

    private static List<String> readWordList() {
        List<String> wordList = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                wordList.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordList;
    }

    private static String pickRandomWord(){
        Random random = new Random();
        int index = random.nextInt(wordList.size());
        String word = wordList.get(index);
        return word;
    }
    
    private static void play(String word) {
        List<String> guessedLetters = new ArrayList<>();
        String[] outputArray = new String[word.length()];
        Arrays.fill(outputArray, "_");
        count = 0;

        do {
            System.out.print("Type a letter: ");
            String input = sc.nextLine().toLowerCase();

            if (input.length() != 1){
                System.out.println("Please enter a single letter!");
                continue;
            };

            if (guessedLetters.contains(input) && word.contains(input)){
                System.out.println("You already correctly guessed that letter!");
                continue;
            }

            guessedLetters.add(input);

            if (word.contains(input)) {
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == input.charAt(0)) {
                        outputArray[i] = input;
                    }
                } 

                String output = String.join("", outputArray);

                if (output.equals(word)) {
                    System.out.println("You win! The word was: " + word);
                    newGame();
                } else {
                    System.out.println(output);
                }

            } else {
                System.out.println(Stages.hangmanStages[count]);
                count++;
            }

        } while (count != Stages.hangmanStages.length);
        System.out.println("You lose! The word was: " + word);
        newGame();
    }

    public static void newGame(){
        System.out.print("Play again? Type yes/no: ");
        String input = sc.nextLine();

        if (input.toLowerCase().equals("yes")){
            System.out.println("New round started!");
            play(pickRandomWord());
        } 
        else if (input.toLowerCase().equals("no")) {
            System.out.println("Good bye! Thank you for playing!");
            sc.close();
        }
        else{
            System.out.println("Please enter a valid option!");
            newGame();
        }
    }

}
