/**
*@author: Mohammed Bekhouche
*@since: 3/17/2020
*@version:1.0

Description: The program extracts data from a CSV file that is used to create movie objects that hold title and
the release year of the movie. The program stores movies lexicographically in a binary search tree. the binary
search tree class has a method that print any subset given withing the tree. 
*/

import java.util.Scanner;
import java.io.File;
import java.io.PrintStream;

public class AssignmentThree{

	public static void main(String[] args) throws Exception{
		File file = new File("input/movies.csv");
		Scanner sc = new Scanner(file);
		String [] row = new String[3];// create an array that hold data of each row of the csv file
		BinaryTree tree = new BinaryTree();
		String line;

		while(sc.hasNextLine()){ // process the csv file line by line
			String title = "";
			String releaseYear = "";

			line = sc.nextLine();
			row = line.split(",");
			/*parse the title and the realease year for each title extracted from the csv file*/
			for (int i = 0; i < row[1].length() ;i++ ) {
				if (Character.isLetter(row[1].charAt(i)) || Character.isWhitespace(row[1].charAt(i))) {
					title = title + Character.toString(row[1].charAt(i));
				}else{
					releaseYear = releaseYear + Character.toString(row[1].charAt(i));
				}
			}
			// insert the movie to the tree
			tree.insert(new Movie(title, releaseYear));
		}

		//tree.subset(tree.getRoot(),"Bug's Life", "Harry Potter");
		//tree.subset(tree.getRoot(),"Back to the Future", "Hulk");
		tree.subset(tree.getRoot(),"Toy Story", "WALL-E");


	}
}

class Movie {
	private String title;
	private String releaseYear;
	public Movie left;
	public Movie right;

	//constructor
	public Movie(String title, String releaseYear){
		this.title = title;
		this.releaseYear = releaseYear;
	}
	//getters
	public String getTitle(){
		return title;
	}
	public String getReleaseYear(){
		return releaseYear;
	}
}

class BinaryTree{
	private Movie root;
		PrintStream ps ;

	// constructor
	public BinaryTree()throws Exception{
		root = null;
		ps = new PrintStream("sample3.txt");


	}
	// getters
	public Movie getRoot(){
		return this.root;
	}
	// insert movie to the tree
	public void insert(Movie movie){
		// special case if the tree is empty
		if(this.root == null){
			movie.left = this.root;
			movie.right = this.root;
			root = movie;
		}else{
			/* if tree is not empty then another method is invoked which will insert the movie in the tree.
			creating another method  that is private facilitates the insertion of objects recursively*/
			add(movie, this.root);
				
		}
	}

	private void add(Movie movie, Movie root){
		 
		if(movie.getTitle().compareToIgnoreCase(root.getTitle()) < 0){
			// once there are no more elements to traverse the movie is inserted on the left side of the parent
			if (root.left == null){
				movie.left = root.left;
				movie.right = root.left;
				root.left = movie;
			}else{
				// traverse the tree recursively until there are no more elements
				add(movie, root.left);
			}
		}else{ // insert the movie on the right
			if(root.right == null){
				movie.left = root.right;
				movie.right = root.right;
				root.right = movie;
			}else{
				add(movie, root.right);
			}
		}
	}
/*This method print the elements of the tree using a inorder traversal*/
	public void displayTree(Movie head){
		// special case if tree is empty
		if(head == null){
			ps.println("tree is empty");
		}else{
			
			if(head.left != null){ //print the left child
				displayTree(head.left);
			}

			ps.println(head.getTitle()); // print the parent

			if (head.right != null) { // print the right child
				displayTree(head.right);
			}
			
			
		}
	}
/*This method prints the elements of any given range within the tree*/
	public void subset(Movie head, String str1, String str2){
		if(head == null){ // base case 
			return;
		}

		if(str1.compareToIgnoreCase(head.getTitle()) < 0){
			subset(head.left, str1, str2);
		}
		// the title of the movie is printed once the element is withing the given range
		if((head.getTitle().compareToIgnoreCase(str1) >= 0) && (head.getTitle().compareToIgnoreCase(str2) <= 0)){
			ps.println(head.getTitle());
		}

		if(str2.compareToIgnoreCase(head.getTitle()) > 0){
			subset(head.right, str1, str2);
		}
	}

}