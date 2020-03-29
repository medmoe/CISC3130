/**
*@author Mohammed Bekhouche
*@since 3/29/2020
*@version 1.0
*/
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;



public class AssignmentFour {

    public static void main(String[] args) throws Exception {

        final char COMMA = ',';
        final char DOUBLE_QUOTE = '"';
        Scanner sc = new Scanner(new File("../Data/input/movies.csv"));
        
        //create a list that will contain movies
        ArrayList<Movie> movies = new ArrayList<>();
        
        /*create a hashMap that will hold genres and how many times each genre
        shows in he file */
        HashMap<String, Integer> genres = new HashMap<>();
        
        String[] row = new String[3];
        String line;
        //process the file line by line
        while (sc.hasNext()) {
            line = sc.nextLine();
            String field = "";
            boolean betweenQuotes = false;
            int i = 0;
            int counter = 0;
            //convert the line to array of characters
            char[] characters = line.toCharArray();
            for (char ch : characters) {
                if (ch != COMMA && betweenQuotes == false) {
                    field = field + Character.toString(ch);
                } else if(betweenQuotes == false){ 
                    row[i] = field;
                    field = "";
                    i++;
                    
                }
                if(ch == DOUBLE_QUOTE){
                    betweenQuotes = !betweenQuotes;
                }else if(betweenQuotes == true){
                    field = field + Character.toString(ch); 
                }
                // special case of processing the last character
                if(counter == characters.length - 1){
                    row[i] = field;
                }
                counter ++;
            }
            
            String title = parseTitle(row[1]);
            String year = parseYear(row[1]);
            //identifie the movies that have no release year in the file
            if(year.equalsIgnoreCase("")){
                year = "unknown";
            }
            // create a movie object and add it to the list
            movies.add(new Movie(title, row[2], year)); 
        }
        // insert the genre and how many times it repeats in the hashmap
        genres = insertToHashMap(movies, genres);
        showReport(genres);
        showAverage(genres);
        showYears(movies);
            
    }
    
    public static HashMap<String, Integer> insertToHashMap(ArrayList<Movie> list,
            HashMap<String, Integer> genres){
        
        for(Movie m: list){
            String genre = m.getGenre();
            if(genres.containsKey(genre)){
                int num = genres.get(genre);
                genres.put(genre, ++num);
            }else{
                genres.put(genre, 1);
            }
        }
        return genres;
    }


    public static String parseTitle(String line){
        char [] characters = line.toCharArray();
        String title = "";
        for(char ch: characters){
            if(ch != '('){
                title = title + Character.toString(ch);
            }else{
                break;
            }
        }
        return title;
    }
    public static String parseYear(String line){
        char [] characters = line.toCharArray();
        String year = "";
        boolean betweenParentheses = false;
        for(int i = characters.length - 1; i >= 0; i--){
            if(betweenParentheses == false){
                if(characters[i] == ')'){
                    betweenParentheses = true;
                }
            }else{
                if(characters[i] == '('){
                    break;
                }else{
                    year = Character.toString(characters[i])+ year;
                }
            }
        }
        return year;
    }
    public static void showReport(HashMap<String, Integer> genres)throws Exception{
        PrintStream ps = new PrintStream("../Data/output/report.txt");
        // convert hash table to array list
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<>(genres.entrySet());
        // sort the array list in ascending order
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>(){
            //@Override compare function
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2){
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        //display content of the array list in decending order
        ListIterator<Map.Entry<String, Integer>> listIte = list.listIterator(list.size());
        while(listIte.hasPrevious()){
            ps.println(listIte.previous());
        }     
    }
    public static void showAverage(HashMap<String, Integer> genres)throws Exception{
        /* display the average number of movies per genre and devide data into more and less 
        than average */
        PrintStream ps = new PrintStream("../Data/output/the_average.txt");
        int total = 0;
        int average;
        int counter = 0;
        for(String key: genres.keySet()){
            total = total + genres.get(key);
            counter ++;
        }
        average = total / counter;
        ps.printf("The average number of movies per genre is: %d%n", average);
        /* traverse the list and divide data to 3 categories less than average,
        equale to average and more than average*/
        int lessThanAverage = 0;
        int moreThanAverage = 0;
        int equaleToAverage = 0;
        for(String key: genres.keySet()){
            if(genres.get(key) == average){
                equaleToAverage++;
            }else if(genres.get(key) < average){
                lessThanAverage++;
            }else{
                moreThanAverage++;
            }
        }
        ps.printf("Number of movies that are less than average are : %d%n"
                + "Number of movies that are equale to average are : %d%n"
                + "Number of movies that are more than average are : %d%n",
                lessThanAverage, equaleToAverage, moreThanAverage);
    }
    
    public static void showYears(ArrayList<Movie> movies)throws Exception{
        /*create a treeMap that append the years in a natural order associated
        with number of movies released each year
        */
        TreeMap<String, Integer> years = new TreeMap<>();
        PrintStream ps = new PrintStream("../Data/output/years.txt");
        for(Movie m: movies){
            String year = m.getYear();
            if(years.containsKey(year)){
                int num = years.get(year);
                years.put(year, ++num);
            }else{
                years.put(year, 1);
            }
        }

        for(String str: years.keySet()){
            ps.println(str +":"+years.get(str));
        }
        
    }

    
}
class Movie{
    private String title;
    private String genre;
    private String year;
    
    //constructor
    public Movie(String title, String genre, String year){
        this.title = title;
        this.genre = genre;
        this.year = year;
    }
    //getters
    public String getTitle(){
        return title;
    }
    public String getGenre(){
        return genre;
    }
    public String getYear(){
        return year;
    }
}
