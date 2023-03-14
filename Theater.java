import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.time.LocalTime;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;


public class Theater {
  ArrayList<Movie> movies;
  
  public Theater() {
    movies = new ArrayList<>();
  }

  
  @Override
  public String toString() {
    return movies.toString();
  }

  
  public Movie getMovie(int i){
    return movies.get(i);
  }

  
  public void addMovie(Movie m){
    movies.add(m);
  }

  
  public void setMovie(int i, Movie m){
    movies.set(i, m);
  }

  
  public void addAllMovies(ArrayList<Movie> m){
    movies.addAll(m);
   }
  
  
  public int movieCount(){
    return movies.size();
  }
  

  public static Theater readMoviesFromFile(String fileName) throws FileNotFoundException{
    Theater result = new Theater();
    Scanner fileReader = new Scanner(new File(fileName));
    while(fileReader.hasNextLine()){
      String line = fileReader.nextLine();
      String[] parts = line.split(", ");
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
      LocalTime showTime = LocalTime.parse(parts[3], formatter);
      LocalTime end = LocalTime.parse(parts[4], formatter);
      Random rand = new Random();
      int emptySeats = rand.nextInt(30);
      result.addMovie(new Movie(parts[0], parts[1], parts[2], showTime, end, emptySeats));
    }
    return result;
  }

  
  public Theater availableMoviesBasedOnAge(int age){
    Theater result = new Theater();
      if(age >= 18){
        for(int i=0; i < movies.size(); i++){
          if (movies.get(i).getEmptySeats() > 0) {
          result.addMovie(movies.get(i));
        }
      }
    } else if(age < 18 && age >= 13){
      for(int i=0; i < movies.size(); i++){
        if(movies.get(i).getMpaRating().equals("R") == false && movies.get(i).getEmptySeats() > 0) {
          result.addMovie(movies.get(i));
        }
      }
    } else if(age < 13) {
      for(int i = 0; i < movies.size(); i++) {
        if(movies.get(i).getMpaRating().equals("PG") || movies.get(i).getMpaRating().equals("G") && movies.get(i).getEmptySeats() > 0){
          result.addMovie(movies.get(i));
        }
      }
    }
    return result;
  }

  
  public static Theater durationInTimeAvailable(Theater movies, int minutesAvailable){
    Theater result = new Theater();
    for(int i = 0; i < movies.movieCount(); i++) {
      if(MINUTES.between(movies.getMovie(i).getStartTime(), movies.getMovie(i).getEndTime()) < minutesAvailable && movies.getMovie(i).getEmptySeats() > 0) {
        result.addMovie(movies.getMovie(i));
      }
    }
    return result;
  }

  
  public static Theater waitLessThanGivenTime(Theater movies, LocalTime timeArrived, int hoursWaiting, String givenGenre, int groupAmount) {
    Theater result = new Theater();
    LocalTime endOfWaiting = timeArrived.plusHours(hoursWaiting);
    for(int i = 0; i < movies.movieCount(); i++){
      if(movies.getMovie(i).getGenre().equals(givenGenre)) {
        if(movies.getMovie(i).getStartTime().isAfter(timeArrived) && movies.getMovie(i).getStartTime().isBefore(endOfWaiting)) {
          if(movies.getMovie(i).getEmptySeats() >= groupAmount) {
            result.addMovie(movies.getMovie(i));
          }
        }
      }
    }
    return result;
  }


  public static Theater moviesInWindowOfTime(Theater movies, LocalTime timeArrived, LocalTime timeDeparted) {
    Theater result = new Theater();
    for(int i = 0; i < movies.movieCount(); i++) {
      if(movies.getMovie(i).getStartTime().isAfter(timeArrived) && movies.getMovie(i).getEndTime().isBefore(timeDeparted) && movies.getMovie(i).getEmptySeats() > 0) {
        result.addMovie(movies.getMovie(i));
      }
    }
    return result;
  }

  
  public static boolean availableSeating(Movie movie, int xMinutes, int tixNeeded, LocalTime timeArrived){
    if(movie.getEmptySeats() >= tixNeeded && MINUTES.between(timeArrived, movie.getStartTime()) <= xMinutes){
          return true;
    }
    return false;
  }
  

  public static Theater moviesByGenre(Theater movies, String givenGenre) {
    Theater result = new Theater();
    for (int i = 0; i < movies.movieCount(); i++) {
      if (movies.getMovie(i).getGenre().equals(givenGenre) && movies.getMovie(i).getEmptySeats() > 0) {
        result.addMovie(movies.getMovie(i));
      }
    }
    return result;
  }


  public static Theater movieMarathon(Theater movies, String firstGenre, String secondGenre, String thirdGenre) {
    Theater result = new Theater();
    boolean isOverlap = true;
    Random rand = new Random();
    int currentIndex;
    Theater firstGenreMovies = moviesByGenre(movies, firstGenre);
    Theater secondGenreMovies = moviesByGenre(movies, secondGenre);
    Theater thirdGenreMovies = moviesByGenre(movies, thirdGenre);

    currentIndex = rand.nextInt(firstGenreMovies.movieCount());
    result.addMovie(firstGenreMovies.getMovie(currentIndex));
    currentIndex = rand.nextInt(secondGenreMovies.movieCount());
    while (isOverlap) {
      if (secondGenreMovies.getMovie(currentIndex).getStartTime().isBefore(result.getMovie(0).getEndTime()) && secondGenreMovies.getMovie(currentIndex).getEndTime().isAfter(result.getMovie(0).getStartTime())) {
      currentIndex = rand.nextInt(secondGenreMovies.movieCount());
      } else {
        isOverlap = false;
      }
    }
    result.addMovie(secondGenreMovies.getMovie(currentIndex));

    currentIndex = rand.nextInt(thirdGenreMovies.movieCount());
    isOverlap = true;
    while (isOverlap) {
      if ((thirdGenreMovies.getMovie(currentIndex).getStartTime().isBefore(result.getMovie(0).getEndTime()) && thirdGenreMovies.getMovie(currentIndex).getEndTime().isAfter(result.getMovie(0).getStartTime())) || ((thirdGenreMovies.getMovie(currentIndex).getStartTime().isBefore(result.getMovie(1).getEndTime()) && thirdGenreMovies.getMovie(currentIndex).getEndTime().isAfter(result.getMovie(1).getStartTime())))) {
      currentIndex = rand.nextInt(thirdGenreMovies.movieCount());
      } else {
        isOverlap = false;
      }
    }
    result.addMovie(thirdGenreMovies.getMovie(currentIndex));

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2 - i; j++) {
        if (result.getMovie(j).getStartTime().isAfter(result.getMovie(j+1).getStartTime())) {
          Movie temp = result.getMovie(j);
          result.setMovie(j, result.getMovie(j+1));
          result.setMovie(j+1, temp);  
        }
      }
    }

    return result;
  }


  public static Set<String> moviesToWatchInMyArea (Theater theater1, Theater theater2, int age) {
    Set<String> result = new HashSet<>();
    Theater firstTheater = theater1.availableMoviesBasedOnAge(age);
    Theater secondTheater = theater2.availableMoviesBasedOnAge(age);
  
    for (int i = 0; i < firstTheater.movieCount(); i++) {
      result.add(firstTheater.getMovie(i).getName());
    }
    
    for (int i = 0; i < secondTheater.movieCount(); i++) {
      result.add(secondTheater.getMovie(i).getName());
    }

    return result;
  }
}