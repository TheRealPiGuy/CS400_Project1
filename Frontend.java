// Name: Sherry Wong
// Email: bwong24@wisc.edu
// Team: FB Red
// Role: Frontend Developer
// TA: Daniel
// Lecturer: Gary Dahl
// Notes to Grader:

import java.util.Scanner;
import java.util.List;

/**
 * The frontend of project 1. Processes user input and displays movie output.
 */
public class Frontend{
    private Scanner input;

    //Used to reference non-static methods in each class
    private Frontend frontend;
    private Backend backend;

    /**
     * This method is what gets run initially
     */
    public static void main(String[] args){
        //TODO: Implement this class.
        new Frontend().run();
    }

    /**
     * This method is the main driver of the program. 
     * 
     * It will: 
     * allow the user to switch between modes as needed.
     * set up instances of the classes we will use later.
     * take user input for the path of the .csv file, as per @486 (loosely).
     */
    public void run(){
        //TODO: Implement this class.
        input = new Scanner(System.in); //process user input
        System.out.println("Welcome to Project 1: MovieMapper.");
        System.out.println("Please enter the exact path to the .csv file.");
        String in = input.nextLine();
        backend = new Backend(in); //pass in the .CSV file
        // System.out.println("Backend initiated w/ filename " + backend.filename);
        frontend = new Frontend(); //instantiate the frontend
        frontend.baseMode(input, frontend, backend);

    }

    /**
     * This is the baseMode. From here you can switch to genre or rating selection mode.
     */
    private void baseMode(Scanner input, Frontend frontend, Backend backend){
        //TODO: Implement this class.
        //Display a welcome message
        System.out.println("Welcome!");
        System.out.println("Enter g to enter genre selection mode.");
        System.out.println("Enter r to enter rating selection mode.");
        System.out.println("Enter x to exit.");
        String in = "";
        while(!validInput(in, new String[] {"g", "r", "x"})){
            in = input.nextLine();
            switch(in){
                case "g":
                this.genreMode(input, frontend, backend);
                case "r":
                this.ratingsMode(input, frontend, backend);
                case "x":
                System.out.println("Goodbye.");
                System.exit(0); //exit program
            }
            System.out.println("Invalid input.");
        }
        //Display a list of the top 3 (by average rating) selected movies.
        //Users will be able to scroll through the list by typing in numbers as commands 
        //of the rank (by rating) of movies to display.

        //g for genre
        //r for rating selection
        //x for program exit
    }

    private void genreMode(Scanner input, Frontend frontend, Backend backend){
        //TODO: Implement this class.
        String in = "";
        System.out.println("Genre Selection Mode: ");

        //Lets users select and deselect genres
        //When multiple genres are selected, only movies that have ALL genres are displayed
        //When no genre is selected, displayed list should be empty.
        //Display a brief introduction/instructions/how-to
        //Display a list of all genres, with each genre assigned a number
        while(true){
            //Users can type in this number as a command to select/deselect each genre
            //Genres are clearly maked as selected or unselected
            //Display instructions to return to the base mode
            System.out.println("Currently selected genres:");
            System.out.println(backend.getGenres());
            System.out.println("All possible genres:");
            System.out.println(backend.getAllGenres());
            System.out.println("Please enter any available genre to select/deselect it.");
            System.out.println("Or enter x to exit.");
            in = input.nextLine();

            List<String> validInputs = backend.getAllGenres();
            validInputs.add("x");
            String[] validStrings = new String[validInputs.size()];
            validStrings = (String[]) validInputs.toArray(validStrings);
            while(!frontend.validInput(in, validStrings) ){
                System.out.println("Invalid input.");
                in = input.nextLine();
            }
            //numbers to select/deselect
            //x to return to base mode
            if(in.equals("x")){
                break;
            }
            if(backend.getGenres().contains(in)){
                backend.addAvgRating(in);
            }else{
                backend.removeAvgRating(in);
            }
        }
        System.out.println("Returning you to base mode . . . ");
        frontend.baseMode(input, frontend, backend); //return to base mode
    }

    private void ratingsMode(Scanner input, Frontend frontend, Backend backend){
        //TODO: Implement this class.
        String in = "";
        System.out.println("Rating Selection Mode: ");

        //Initially, all ratings are selected.
        for(int i=0; i<=10; i++){
            backend.addAvgRating(String.valueOf(i));
        }

        while(true){
            //Lets users select and deselect ratings.
            //Display numbered list of ratings that users can select        
            //Ratings are clearly maked as selected or unselected
            //Ratings are from 0-10, and 
            //each rating x should range from x.000 to x.999
            //ex.  rating 8 should range from 8.000 to 8.999
            while(true){
                System.out.println("Currently selected ratings:");
                System.out.println(backend.getAvgRatings());
                System.out.println("Please enter any rating 0-10 to select/deselect it.");
                System.out.println("Or enter x to exit.");
                in = input.nextLine();

                while(!frontend.validInput(in, new String[] {"x", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})){
                    System.out.println("Invalid input.");
                    in = input.nextLine();
                }
                if(in.equals("x")){
                    break; //exit mode
                }
                if(backend.getAvgRatings().contains(in)){
                    backend.addAvgRating(in);
                }else{
                    backend.removeAvgRating(in);
                }
            }
            if(in.equals("x")){
                break;
            }
            //numbers to select/deselect
            //x to return to base mode
        }
        System.out.println("Returning you to base mode . . . ");
        frontend.baseMode(input, frontend, backend); //return to base mode
    }

    private boolean validInput(String input, String[] validInputs){
        for(String x : validInputs){
            if(x.equals(input)){
                return true;
            }
        }
        return false;
    }
}
