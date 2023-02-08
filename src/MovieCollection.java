import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Collections;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;
    private ArrayList<String> cast;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
        cast = new ArrayList<String>();

        for (int i = 0; i < movies.size(); i++) {
            String ac = movies.get(i).getCast();
            String[] ca = ac.split("\\|");
            for (int x = 0; x < ca.length; x++) {
                if (!cast.contains(ca[x])) {
                    cast.add(ca[x]);
                }
            }
        }
        Collections.sort(cast);
        cast.remove(0);

    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast() {
        System.out.print("Enter a cast member: ");
        String member = scanner.nextLine().toLowerCase();
        ArrayList<String> results = new ArrayList<String>();

        for (int i = 0; i < cast.size(); i++) {
            String mem = cast.get(i);
            if (mem.toLowerCase().contains(member)) {
                results.add(mem);
            }
        }

        for (int i = 0; i < results.size(); i++)
        {
            String castMember = results.get(i);
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + castMember);
        }

        System.out.println("Which cast member would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        String selectedActor = results.get(choice - 1);
        ArrayList<Movie> mResults = new ArrayList<Movie>();

        for (int i = 0; i < movies.size(); i++) {
            String[] actors = movies.get(i).getCast().split("\\|");

            for (int x = 0; x < actors.length; x++) {
                if (actors[x].equals(selectedActor)) {
                    mResults.add(movies.get(i));
                }
            }
        }
        sortResults(mResults);

        for (int i = 0; i < mResults.size(); i++) {
            String title = mResults.get(i).getTitle();
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = mResults.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void searchKeywords() {
        System.out.print("Enter your keyword here: ");
        String keyword = scanner.nextLine().toLowerCase();
        ArrayList<Movie> results = new ArrayList<Movie>();

        for (int i = 0; i < movies.size(); i++) {
            String[] keywords = movies.get(i).getKeywords().split("\\|");

            for (int x = 0; x < keywords.length; x++) {
                String certainWord = keywords[x].toLowerCase();
                if (certainWord.contains(keyword)) {
                    results.add(movies.get(i));
                    x = keyword.length();
                }

            }
        }

        sortResults(results);

        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres() {
        ArrayList<String> gResults = new ArrayList<String>();

        for(int i = 0; i < movies.size(); i++) {
            Movie m = movies.get(i);

            String[] genres = m.getGenres().split("\\|");
            for (String g : genres) {
                if (!gResults.contains(g)) {
                    gResults.add(g);
                }
            }
        }
        Collections.sort(gResults);

        for (int i = 0; i < gResults.size(); i++)
        {
            String genre = gResults.get(i);
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + genre);
        }

        System.out.print("Choose a genre: ");
        int gInd = scanner.nextInt() - 1;

        String genreC = gResults.get(gInd);
        ArrayList<Movie> mResults = new ArrayList<Movie>();

        for (Movie m : movies) {
            String[] genres = m.getGenres().split("\\|");
            for (String g : genres) {
                if (g.contains(genreC) && !mResults.contains(m)) {
                    mResults.add(m);
                }
            }
        }
        sortResults(mResults);

        for (int i = 0; i < mResults.size(); i++)
        {
            String title = mResults.get(i).getTitle();
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.print("Choose a title: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = mResults.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated() {
        ArrayList<Double> highestR = new ArrayList<Double>();
        Movie[] m1 = new Movie[50];

        for (Movie m : movies) {
            highestR.add(m.getUserRating());
        }
        Collections.sort(highestR);
        Collections.reverse(highestR);

        for (Movie m : movies) {
            for (int i = 0; i < 50; i++) {
                if (m.getUserRating() == highestR.get(i) && m1[i] == null) {
                    m1[i] = m;
                    i = 50;
                }
            }
        }

        for (int i = 0; i < m1.length; i++)
        {
            String title = m1[i].getTitle();
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title + ": " + highestR.get(i));
        }

        System.out.print("Choose a title: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = m1[choice - 1];

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();


    }

    private void listHighestRevenue() {
        ArrayList<Integer> highestRev = new ArrayList<Integer>();
        Movie[] m1 = new Movie[50];

        for (Movie m : movies) {
            highestRev.add(m.getRevenue());
        }
        Collections.sort(highestRev);
        Collections.reverse(highestRev);

        for (Movie m : movies) {
            for (int i = 0; i < 50; i++) {
                if (m.getRevenue() == highestRev.get(i) && m1[i] == null) {
                    m1[i] = m;
                    i = 50;
                }
            }
        }

        for (int i = 0; i < m1.length; i++)
        {
            String title = m1[i].getTitle();
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title + ": " + highestRev.get(i));
        }

        System.out.print("Choose a title: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = m1[choice - 1];

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}