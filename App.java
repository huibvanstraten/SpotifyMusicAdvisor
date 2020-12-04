package SpotifyMusicAdvisor;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice;

        GetAccess access = new GetAccess();
        UserMenu.accessMenu(access);

        while(true) {
            UserMenu.mainMenu();
            choice = scanner.nextLine().toLowerCase();
            UserMenu.menuOperator(choice);
        }
    }
}
