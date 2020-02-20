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
        orderedArtistList.insertionSort(orderedArtistList.first);
        orderedArtistList.displayArtistList(orderedArtistList.first);

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

    //constructor
    public Artist(String n) throws Exception {
        name = n;
    }
}

class ArtistList {

    public Artist first;
    private Artist sorted;
    public static PrintStream ps;

    //constructor
    public ArtistList() throws Exception {
        first = null;
        ps = new PrintStream("sortedNames.txt");
    }

    public void append(String name) throws Exception {
        // create a new element
        Artist artist = new Artist(name);
        artist.next = first;
        first = artist;
    }

    public void displayArtistList(Artist head) throws Exception {
        while(head != null){
            ps.println(head.name);
            head = head.next;
        }
    }
    public void insertionSort(Artist a){
        // Initialize sorted linked list
        sorted = null;
        Artist current = a;
        // traverse the given linked list and insert every element to sorted
        while(current != null){
            //store next for next iteration
            Artist next = current.next;
            //insert current in sorted linked list
            sortedInsert(current);
            //update current
            current = next;
        }
        first = sorted;
    }
    public void sortedInsert(Artist newArtist){
        //special case for head end
        if(sorted == null || sorted.name.compareTo(newArtist.name)> 0){
            newArtist.next = sorted;
            sorted = newArtist;
        }else{
            Artist current = sorted;
            //locate the artist before the point of insertion
            while(current.next != null && current.next.name.compareTo(newArtist.name)<0){
                current = current.next;
            }
            newArtist.next = current.next;
            current.next = newArtist;
        }
    }
}
