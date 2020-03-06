/**
 *@Author: Mohammed Bekhouche
 * @since: 3/5/2020
 * @version: 1.0
 *
 * Description: The program is responsible for creating a play list of the
 * latest music tracks listed in the last four weeks. At the first section the
 * program extract the data from four CSV files where each file presents one
 * week of music tracks. the program store these files in array. The second
 * section of the program presents the steps of creating a play list. The
 * program creates a Queue that uses a doubly linked list. This Queue will hold
 * the music tracks. we have chosen doubly linked list data structure because we
 * need those tracks to be sorted in the Queue. The program starts processing
 * the CSV files one at a time. it reads each line from the file, then it
 * extracts the music track name ,at that point it creates an object that
 * presents this particular song and add it to the Queue. each time the program
 * add an object to the Queue, it makes sure that they are sorted in ascending
 * order. the final section of the program describes the step of processing the
 * content of the Queue. Once the user play a song. the program removes it from
 * the Queue and saves it in a stack that uses a linked list in order to keep
 * track of the music tracks played by the user.
 *
 */
package assignment2;

import java.util.Scanner;
import java.io.File;
import java.io.PrintStream;

public class Assignment2 {

    public static void main(String[] args) throws Exception {
        
        PrintStream ps = new PrintStream("output.txt");

        File week1 = new File("firstWeek.csv");
        File week2 = new File("secondWeek.csv");
        File week3 = new File("thirdWeek.csv");
        File week4 = new File("fourthWeek.csv");

        File[] weeks = {week1, week2, week3, week4};

        Queue playList = new Queue();
        Stack history = new Stack();

        String line;
        String[] row;

        for (int i = 0; i < weeks.length; i++) {
            Scanner scanner = new Scanner(weeks[i]);
            //read line from the csv File
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                row = line.split(",");
                //add track to the queue
                playList.addSong(new Song(removeQuotes(row[1])));
            }
        }
        ps.println("********************************************\n"
                + "******** LIST OF MUSIC TRACKS *****************\n"
                + "***************************************");
        playList.display(ps);
        ps.println();
        ps.println("****************************************\n"
                + "*********** PLAY 10 Songs From the Play List*************\n"
                + "*****************************************************\n");
        // play song from the play list
        for (int i = 0; i < 10; i++) {
            String track = playList.ListenToSong();
            // save the song in history
            ps.println(track);
            
            history.addSong(new Song(track));
        }
        ps.println();
        ps.println("*************************************************\n"
                + "*********Replay the Songs that been Listened To Recently\n"
                + "***************************************************\n");
        for (int i = 0; i < 15; i++) {
            ps.println(history.lastListened());
        }
    }

    /*This method remove quotes from track names*/
    public static String removeQuotes(String trackName) {
        if (trackName.charAt(0) == '\"') {
            return trackName.substring(1, trackName.length() - 1);
        }
        return trackName;
    }

}

class Song {

    private String name;
    public Song next;
    public Song previous;

    //constructor
    public Song(String name) {
        this.name = name;
        next = null;
        previous = null;
    }

    //getters
    public String getName() {
        return name;
    }
}

class Queue {

    private Song head;// the entrence of objects
    private Song tail;// the exit of objects

    //constructor
    public Queue() {
        head = null;
        tail = null;
    }

    //getters
    public Song getHead() {
        return head;
    }

    public Song getTail() {
        return tail;
    }

    //this method updates the position of tail
    public void updateTail() {
        Song current = head;
        while (current != null) {
            tail = current;
            current = current.next;
        }
    }

    /*This method show whether the queue is empty*/
    public boolean isEmpty() {
        return (head == null);
    }

    /*This method add songs to the queue in ascending order*/
    public void addSong(Song song) {
        Song hold;
        // special case if the Queue is empty
        if (head == null) {
            song.next = head;
            head = song;
            tail = song;
        } else {
            while (tail != null) {
                if (song.getName().compareToIgnoreCase(tail.getName()) > 0) {
                    tail = tail.previous;
                } else {
                    hold = tail.next;
                    tail.next = song;
                    song.previous = tail;
                    song.next = hold;
                    if (hold != null) {//special case if we are adding at the end of the queue
                        hold.previous = song;
                    }
                    //update tail position
                    this.updateTail();
                    break;
                }
            }
            if (tail == null) {
                song.next = head;
                head.previous = song;
                head = song;
                this.updateTail();
            }
        }
    }

    /*This method is responsible for playing tracks from the queue*/
    public String ListenToSong() {
        if (!this.isEmpty()) { // the Queue should not be empty
            this.updateTail();
            Song hold = tail;
            if (tail != head) {
                tail = tail.previous;
            } else {// special case if there is only one object in the Queue
                head = null;
            }
            tail.next = null;
            return hold.getName();
        } else {
            String str = "PlayList is empty";
            return str;
        }
    }

    // this method return the size of the queue
    public int length() {
        Song current = head;
        int counter = 0;
        while (current != null) {
            counter++;
            current = current.next;
        }
        return counter;
    }

    // this method display the objects in the queue
    public void display(PrintStream ps) {
        Song current = head;
        while (current != null) {
            ps.println(current.getName());
            current = current.next;
        }
    }
}

class Stack {

    Song top;

    public Stack() {
        top = null;
    }

    // add a song to the stack
    public void addSong(Song song) {
        song.next = top;
        top = song;
    }

    // remove a song from the stack
    public String lastListened() {
        if (top != null) {
            Song hold;
            hold = top;
            top = top.next;
            return hold.getName();
        } else {
            String str = "no more songs to play";
            return str;
        }
    }
}
