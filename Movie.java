import java.time.LocalTime;


public class Movie {
  private String name;
  private String genre;
  private String mpaRating;
  private LocalTime startTime;
  private LocalTime endTime;
  private int emptySeats;

  public Movie (String name, String genre, String mpaRating, LocalTime startTime, LocalTime endTime, int emptySeats) {

    this.name = name;
    this.genre = genre;    
    this.mpaRating = mpaRating;
    this.startTime = startTime;
    this.endTime = endTime;
    this.emptySeats = emptySeats;
  }

  public String getName(){return name;}
  
  public String getMpaRating(){return mpaRating;}

  public String getGenre(){return genre;}

  public LocalTime getStartTime(){return startTime;}

  public LocalTime getEndTime(){return endTime;}

  public int getEmptySeats(){return emptySeats;}

  public void setEmptySeats(int newEmptySeats){
    if(newEmptySeats >= 0){
      emptySeats = newEmptySeats;      
    }
  }
  
  public String toString() {
    return String.format("%s %s %s %s %s %s\n", name, genre, mpaRating, startTime, endTime, emptySeats);
  }
}