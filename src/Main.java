import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String[] playerNames = new String[11];
        int[] playerScores = new int[11];
        int playerCount;

        playerCount = createTeam(input, playerNames, playerScores);

        menuController(input, playerNames, playerScores, playerCount);

    }

    public static int createTeam(Scanner input, String[] playerNames, int[] playerScores) {

        clearConsole();
        System.out.println("Hvor mange spillere vil du indtaste?");
        int playerCount = input.nextInt();
        input.nextLine();

        for (int n = 0; n <= playerCount - 1; n++) {
            clearConsole();
            System.out.print("indtast navn på spiller nr " + (n + 1) + ": ");
            playerNames[n] = input.nextLine();
            playerScores[n] = getRandomScore();

        }
        printTeam(playerNames, playerScores);
        waitForUser(input);
        return playerCount;
    }

    public static int getRandomScore() {
        return (int) (Math.random() * 99) + 1;
    }

    public static void clearConsole() {
        for (int n = 0; n < 20; n++) {
            System.out.println();
        }
    }

    public static void printTeam(String[] playerNames, int[] playerScores) {
        clearConsole();
        int i = 0;
        System.out.println("---------- YOUR TEAM ----------");
        for (int n = 0; n < playerNames.length; n++) {
            if (playerNames[n] != null && !playerNames[n].isEmpty()) {
                i++;
                System.out.println((i) + ": " + playerNames[n] + " |  Rating: " + playerScores[n]);
            }
        }

    }

    public static void menuController(Scanner input, String[] playerNames, int[] playerScores, int playerCount) {
        int userInput;
        do {

            do {
                System.out.println("""
                        ---------- MENU ----------
                        1... Opret spiller
                        2... Fjern spiller
                        3... Rediger spiller
                        4... Se højeste rated spiller
                        5... Vis hold
                        6... Afslut
                        --------------------------
                        """);
                userInput = input.nextInt();

                if ((userInput < 1) || (userInput > 6)) {
                    System.out.println("Ugyldigt input. (Indtast tal mellem 1-5)");
                }
            } while ((userInput < 1) || (userInput > 6));

            switch (userInput) {
                case 1:
                    playerCount = addPlayer(playerNames, playerScores, input, playerCount);
                    break;
                case 2:
                    playerCount = removePlayer(playerNames, playerScores, playerCount, input);
                    break;
                case 3:
                    editPlayer(playerNames, playerScores, input);
                    break;
                case 4:

                    break;
                case 5:
                    input.nextLine();
                    printTeam(playerNames, playerScores);
                    waitForUser(input);
                    break;
                case 6:
                    break;
            }
        } while (userInput != 6);
    }


    public static int addPlayer(String[] playerNames, int[] playerScores, Scanner input, int playerCount) {
        if (playerCount < 11) {
            for (int n = 0; n < playerNames.length; n++) {
                if (playerNames[n] == null || playerNames[n].isEmpty()) {
                    System.out.print("Indtast navnet på din spiller: ");
                    input.nextLine();
                    playerNames[n] = input.nextLine();
                    playerScores[n] = getRandomScore();
                    playerCount++;
                    break;
                }
            }

        } else {
            System.out.println("Holdet er fuldt. Du kan ikke tilføje flere spillere.");
        }
        return playerCount;
    }

    public static void waitForUser(Scanner input) {
        System.out.println("Tryk enter for at fortsætte...");
        input.nextLine();
    }

    public static int removePlayer(String[] playerNames, int[] playerScores, int playerCount, Scanner input) {
        printTeam(playerNames, playerScores);
        System.out.println();
        System.out.print("Jeg vil gerne fjerne ");
        input.nextLine();
        String userInput = input.nextLine();

        for (int n = 0; n <= playerNames.length - 1; n++) {
            if (userInput.equalsIgnoreCase(playerNames[n])) {
                playerNames[n] = null;
                playerScores[n] = 0;
                playerCount--;
            }
        }
        return playerCount;
    }

    public static void editPlayer(String[] playerNames, int[] playerScores, Scanner input) {
        printTeam(playerNames, playerScores);
        System.out.println();
        System.out.print("Jeg vil gerne redigere ");
        input.nextLine();
        String userInput = input.nextLine();

        for (int n = 0; n <= playerNames.length - 1; n++) {
            if (userInput.equalsIgnoreCase(playerNames[n])) {
                System.out.println("Hvilken score skal " + playerNames[n] + " have?");
                System.out.print("Score: ");
                playerScores[n] = input.nextInt();
            }
        }
    }

}