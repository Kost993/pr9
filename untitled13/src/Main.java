import java.util.Scanner;

public class Main {
    private static int size = 3;
    private static char[][] game;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        gameMenu();
    }

    private static void gameMenu() {
        boolean run = true;
        while (run) {
            System.out.println("Головне меню:");
            System.out.println("1. Грати (Нова гра)");
            System.out.println("2. Налаштування");
            System.out.println("3. Вихід");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    startGame();
                    break;
                case "2":
                    changeSettings();
                    break;
                case "3":
                    System.out.println("Ви вийшли з гри.");
                    run = false;
                    break;
                default:
                    System.out.println("Невірний вибір, спробуйте ще раз.");
            }
        }
    }

    private static void changeSettings() {
        System.out.println("Оберiть розмiр гри: 3x3 (1), 5x5 (2), 7x7 (3), 9x9 (4)");
        switch (scanner.nextLine()) {
            case "1": size = 3; break;
            case "2": size = 5; break;
            case "3": size = 7; break;
            case "4": size = 9; break;
            default: System.out.println("Невірний вибір, залишено стандартний розмір.");
        }
    }

    private static void startGame() {
        char player = 'X';
        int moves = 0;
        game = new char[size * 2 - 1][size * 2 - 1];
        initBoard();

        while (true) {
            printBoard();
            playerMove(player);
            moves++;

            if (checkWin(player)) {
                printBoard();
                System.out.println("Переможець: " + player);
                return;
            }

            if (moves == size * size) {
                System.out.println("Нiчия");
                return;
            }

            player = (player == 'X') ? 'O' : 'X';
        }
    }

    private static void initBoard() {
        for (int i = 0; i < game.length; i++) {
            for (int j = 0; j < game.length; j++) {
                game[i][j] = (i % 2 == 0) ? (j % 2 == 0 ? ' ' : '|') : '-';
            }
        }
    }

    private static void printBoard() {
        for (char[] row : game) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    private static void playerMove(char player) {
        int row, col;
        while (true) {
            System.out.println("Ви: " + player);
            System.out.print("Введiть номер строки: ");
            row = scanner.nextInt() - 1;
            System.out.print("Введiть номер стовпця: ");
            col = scanner.nextInt() - 1;
            scanner.nextLine();

            if (row >= 0 && row < size && col >= 0 && col < size && game[row * 2][col * 2] == ' ') {
                game[row * 2][col * 2] = player;
                return;
            } else {
                System.out.println("Ця клитина зайнята або виходить за межі");
            }
        }
    }

    private static boolean checkWin(char player) {
        int[][] directions = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (game[i * 2][j * 2] == player) {
                    for (int[] dir : directions) {
                        int count = 1;
                        for (int sign = -1; sign <= 1; sign += 2) {
                            for (int step = 1; step < size; step++) {
                                int newRow = i + dir[0] * step * sign;
                                int newCol = j + dir[1] * step * sign;
                                if (newRow < 0 || newCol < 0 || newRow >= size || newCol >= size || game[newRow * 2][newCol * 2] != player) {
                                    break;
                                }
                                count++;
                            }
                        }
                        if (count >= 3) return true;
                    }
                }
            }
        }
        return false;
    }
}