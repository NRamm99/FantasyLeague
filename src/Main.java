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
        System.out.println("""
        ##################################################
                  Velkommen til Fantasy Football        \s
        ##################################################
       \s""");

        int playerCount = -1;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Hvor mange spillere vil starte med indtaste?: ");

            if (input.hasNextInt()) {
                playerCount = input.nextInt();
                input.nextLine(); //Ryder

                if (playerCount >= 1 && playerCount <= 11) {
                    validInput = true;

                } else {
                    System.out.println("\nDu skal indtaste et tal mellem 1-11");
                }
            } else {

                System.out.println("\nUgyldigt input. Du skal indtaste et tal");
                input.nextLine(); //Ryder
            }
        }


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
        int fakeIndex = 0;
        System.out.println("---------- YOUR TEAM ----------");
        for (int n = 0; n < playerNames.length; n++) {
            if (playerNames[n] != null && !playerNames[n].isEmpty()) {
                fakeIndex++;
                System.out.println((fakeIndex) + ": " + playerNames[n] + " |  Rating: " + playerScores[n]);
            }
        }
    }

    public static void menuController(Scanner input, String[] playerNames, int[] playerScores, int playerCount) {
        int userInput = -1;

        do {
            boolean validInput = false;

            while (!validInput) {
                clearConsole();
                System.out.println("""
                        ---------- MENU ----------
                        1... Opret spiller
                        2... Fjern spiller
                        3... Rediger spiller
                        4... Erstat spiller
                        5... Team stats
                        6... Vis hold
                        7... Afslut
                        --------------------------
                        """);
                if (input.hasNextInt()) {
                    userInput = input.nextInt();
                    input.nextLine(); // Rydder

                    if (userInput >= 1 && userInput <= 7) {
                        validInput = true;

                    } else {
                        System.out.println("Ugyldigt valgt. Indtast et tal mellem 1 og 7");
                        waitForUser(input);
                    }
                } else {
                    System.out.println("Du skal indtaste et tal");
                    input.nextLine(); // Rydder
                    waitForUser(input);
                }
            }

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
                    replacePlayer(playerNames, playerScores, input);
                    break;
                case 5:
                    teamReport(playerNames, playerScores);
                    waitForUser(input);
                    break;
                case 6:
                    printTeam(playerNames, playerScores);
                    waitForUser(input);
                    break;
                case 7:
                    break;
            }
        } while (userInput != 7);
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
        String userInput = input.nextLine();

        boolean found = false;

        for (int n = 0; n <= playerNames.length - 1; n++) {
            if (userInput.equalsIgnoreCase(playerNames[n])) {
                found = true;
                int newRating = -1;
                boolean validRating = false;

                while (!validRating) {

                    System.out.println("Hvilken Rating skal " + playerNames[n] + " have?");
                    System.out.print("Rating: ");

                    if (input.hasNext()) {
                        newRating = input.nextInt();
                        input.nextLine(); // Rydder

                        if (newRating >= 0 && newRating <= 100) {
                            validRating = true;
                        } else {
                            System.out.println("Den nye Rating må kun være mellem 0-100");
                        }
                    } else {
                        System.out.println(" Du skal indstaste et heltal");
                        input.nextLine(); // Rydder
                    }
                }

                playerScores[n] = newRating;
                System.out.println("Spillerens nye Rating");
                waitForUser(input);
                break;
            }
        }

        if (!found) {
            System.out.println("Du har ingen spillere på holdet med det navn");
            waitForUser(input);
        }
    }

    public static void replacePlayer(String[] playerNames, int[] playerScores, Scanner input) {
        printTeam(playerNames, playerScores);
        System.out.println();
        System.out.print("Jeg vil gerne erstatte ");
        String userInput = input.nextLine();

        boolean found = false;

        for (int n = 0; n <= playerNames.length - 1; n++) {
            if (userInput.equalsIgnoreCase(playerNames[n])) {
                found = true;

                System.out.println("Hvem skal " + playerNames[n] + " erstattes med?");
                System.out.print("Navn: ");
                String newName = input.nextLine();

                int newRating = -1;
                boolean validRating = false;

                while (!validRating) {
                    System.out.println("Hvilken rating skal " + newName + " have?");
                    System.out.print("Rating: ");

                    if (input.hasNextInt()) {
                        newRating = input.nextInt();
                        input.nextLine(); // Rydder

                        if (newRating >= 0 && newRating <= 100) {
                            validRating = true;
                        } else {
                            System.out.println("\nDen nye Rating skal være mellem 0-100");
                        }
                    } else {
                        System.out.println("\nUgyldigt input. Du skal skrive et heltal");
                        input.nextLine(); //Rydder
                    }
                }
                playerNames[n] = newName;
                playerScores[n] = newRating;
                System.out.println("Spilleren er blevet erstattet");
                waitForUser(input);
                break;
            }
        }
        if (!found){
            System.out.println("Ingen spiller med navnet " + userInput + "blev fundet");
            waitForUser(input);
        }
    }

    public static void teamReport(String[] playerNames, int[] playerScores) {

        printBestPlayer(playerNames, playerScores);
        printWorstPlayer(playerNames, playerScores);
        printAverageScore(playerNames, playerScores);
    }

    public static void printBestPlayer(String[] playerNames, int[] playerScores) {
        int highScore = Integer.MIN_VALUE;
        int bestPlayerIndex = 0;
        for (int n = 0; n <= playerNames.length - 1; n++) {
            if ((playerNames[n] != null) && playerScores[n] > highScore) {
                highScore = playerScores[n];
                bestPlayerIndex = n;
            }
        }
        printToConsole("------------ DIN BEDSTE SPILLER -------------\n" + playerNames[bestPlayerIndex] + " || Score: " + playerScores[bestPlayerIndex] + "\n");
    }

    public static void printWorstPlayer(String[] playerNames, int[] playerScores) {
        int lowestScore = Integer.MAX_VALUE;
        int bestPlayerIndex = 0;
        for (int n = 0; n <= playerNames.length - 1; n++) {
            if ((playerNames[n] != null) && playerScores[n] < lowestScore) {
                lowestScore = playerScores[n];
                bestPlayerIndex = n;
            }
        }
        printToConsole("---------- DIN LAVES RATED SPILLER ----------\n" + playerNames[bestPlayerIndex] + " || Score: " + playerScores[bestPlayerIndex] + "\n", false);
    }

    public static void printAverageScore(String[] playerNames, int[] playerScores) {
        int sum = 0;
        int playerCount = 0;
        int averageScore;

        for (int n = 0; n <= playerNames.length - 1; n++) {
            if (playerNames[n] != null) {
                sum += playerScores[n];
                playerCount++;
            }
        }
        averageScore = sum / playerCount;

        printToConsole("--------------------------------------------- \nDit holds gennemsnitlige score: " + averageScore + "\n\n\n", false);

    }

    public static void printToConsole(String text, boolean clear) {
        if (clear) {
            clearConsole();
        }
        System.out.println(text);
    }

    public static void printToConsole(String text) {
        printToConsole(text, true);
    }

}