import java.io.FileNotFoundException;
import java.time.LocalTime;

class Main {
  public static void main(String[] args) throws FileNotFoundException {

    Theater westbankTheater = Theater.readMoviesFromFile("AMC Westbank.txt");
    Theater elmwoodTheater = Theater.readMoviesFromFile("AMC Elmwood.txt");

    Customer c1 = new Customer(234645632, 17);

    System.out.println("Based on your age, you can watch the following movies:\n" + westbankTheater.availableMoviesBasedOnAge(c1.getAge()) + "\n");

    System.out.println("In the available time that you gave, you can watch the following movies:\n" + Theater.durationInTimeAvailable(westbankTheater, 90) + "\n");
    
    System.out.println("Based on your arrival time and given amount of hours that you are willing to wait, you can watch the following movies:\n" + Theater.waitLessThanGivenTime(elmwoodTheater, LocalTime.of(11, 30), 5, "action", 5) + "\n");

    System.out.println("Based on your given window of time, you can watch the following movies:\n" + Theater.moviesInWindowOfTime(westbankTheater, LocalTime.of(15, 30), LocalTime.of(17, 30)) + "\n");

    System.out.println("Is this movie, " + westbankTheater.getMovie(0).getName() + ", able to seat your entire party without waiting more than the expected amount of time?:\n" + Theater.availableSeating(westbankTheater.getMovie(0), 30, 10, LocalTime.of(12, 45)) + "\n");
  
    System.out.println("Here is your schedule of movies to watch before the theater closes:\n" + Theater.movieMarathon(elmwoodTheater, "suspense", "comedy", "action") + "\n");
  
    System.out.println("The titles that you can ask your child if they would like to watch are:\n" + Theater.moviesToWatchInMyArea(elmwoodTheater, westbankTheater, 13));
  }
}