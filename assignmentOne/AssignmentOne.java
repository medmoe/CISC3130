/*Author: Mohammed Bekhouche
  CISC 3130
  Date 2/19/2020

Discription: The program display artist names that appears on the list. The program
does that by reading the CSV file using BufferedReader and FileReader classes. 
Each line in the file is converted to a set of strings stored in an array named
singleRow. the program copies each element of the singleRow array in a multidimentional 
array named list. that process ocurres simultaniously with the task of reading data
from the CSV file.
The second part of the program creates two classes named Artist and ArtistList.
those classes are created in order to have the list of artist names displayed in 
alphabetical order in the list
  
 */
package assignmentone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;

public class AssignmentOne {

    public static void main(String[] args) throws Exception {
        PrintStream ps = new PrintStream("output.txt");

        /*create a multidimentional array that will hold the data read from the
            CSV file*/
        int cols = 5;
        int rows = 200;
        String line;
        String[] singleRow;
        String[][] list = new String[rows][cols];
        ArtistList orderedArtistList = new ArtistList();

        BufferedReader csvReader = new BufferedReader(new FileReader("artistList.csv"));
        for (int i = 0; i < rows; i++) {
            line = csvReader.readLine();
            singleRow = line.split(",");
            for (int j = 0; j < cols; j++) {
                if (j == 2) {
                    if (isThere(singleRow[j], list)) {
                        j++;
                    }
                }
                list[i][j] = singleRow[j];
            }
        }
        for (int i = 0; i < rows; i++) {
            if (list[i][2] != null) {
                ps.println(list[i][2]);
                //add the name of the artist to the list
                orderedArtistList.append(list[i][2]);    
            }
        }

        orderedArtistList.displayArtistList();

    }

    /*This method verifies is the artist's name repeats in the CSV file*/
    public static boolean isThere(String name, String[][] myList) {
        for (int i = 0; i < myList.length; i++) {
            if (name.equalsIgnoreCase(myList[i][2])) {

                return true;
            }
        }
        return false;
    }

}

class Artist {

    public String name;
    public Artist next;
    public static PrintStream ps;

    //constructor
    public Artist(String n) throws Exception {
        ps = new PrintStream("artistList.txt");
        name = n;
    }

    // print the artist's name
    public void displayName() throws Exception {
        ps.println(name);
    }
}

class ArtistList {

    private Artist first;

    //constructor
    public ArtistList() {
        first = null;
    }

    public void append(String name) throws Exception {
        // create a new element
        Artist artist = new Artist(name);
        artist.next = first;
        first = artist;
    }

    public void displayArtistList() throws Exception {
        Artist current = first;
        while (current != null) {
            current.displayName();
            current = current.next;
        }
    }
}
