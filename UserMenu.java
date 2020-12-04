package SpotifyMusicAdvisor;

import SpotifyMusicAdvisor.SpotifyOperator.Categories;
import SpotifyMusicAdvisor.SpotifyOperator.Featured;
import SpotifyMusicAdvisor.SpotifyOperator.Playlists;
import SpotifyMusicAdvisor.SpotifyOperator.Releases;

class UserMenu {

    static void accessMenu(GetAccess access) {
        System.out.println("Welcome to the Rocking OAuth Spotify Advisor");
        System.out.print("Please log in using following link: ");
            access.getAccessCode();
        if (access.getAuthorized()) {
            access.accessToken();
        }
    }

    static void mainMenu() {
        System.out.println("Type \"new\" for the newest releases");
        System.out.println("Type \"featured\" for the Featured list");
        System.out.println("Type \"categories\" to see all the available categories");
        System.out.println("Type \"playlists\" and the name of your desired category for your favorite music");
        System.out.println("Type \"exit\" to exit the application");
        System.out.print("What would you like to do?: ");
    }

    static void menuOperator(String choice) {
        String[] query = choice.split(" ");
        switch (query[0]) {
            case "new":
                Releases releases = new Releases();
                System.out.println(releases.parseNewReleases());
                break;
            case "featured":
                Featured featured = new Featured();
                System.out.println(featured.getFeatured());
                break;
            case "categories":
                Categories categories = new Categories();
                System.out.println(categories.getCategories());
                break;
            case "playlists":
                StringBuilder temp = new StringBuilder();
                for(int i = 1; i < query.length; i++){
                    temp.append(query[i]).append(" ");
                }
                Playlists playlists = new Playlists(temp.toString().trim());
                String ID = playlists.getCategoryIdByName();
                if ("Unknown category name.".equals(ID)) {
                    System.out.println(ID);
                } else {
                    playlists.setCategoryID(ID);
                    System.out.println(playlists.getPlaylist());
                }
                break;
            case "exit":
                System.out.println("---GOODBYE!---");
                System.exit(0);
                break;
            default:
                System.out.println("\nWrong input. Choose one of the options below\n");
                break;
        }
    }
}
