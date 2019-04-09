package graph2;

/**
 *
 * @author Micah Roberts
 */

//import docs needed
import java.io.*;
import java.util.*;
import java.util.Scanner;

public class Graph2 {

    //graph
    static Map<String, LinkedList<String>> adj;

    public Graph2() {

        //creates a graph
        adj = new HashMap<String, LinkedList<String>>();
    }

    //creates a node of all actors if they do not already have one
    public static void addNode(String node) {
        adj.putIfAbsent(node, new LinkedList<String>());
    }

    //method to link actors that worked together
    public static void addNeighbor(LinkedList<String> v1, String v2) {
        v1.add(v2);
    }

    //method to find all links to the actor
    public static List<String> getNeighbors(String v) {
        return adj.get(v);
    }

    //method to go through the string and find actors names
    public static void concate(String a) {
        String[][] s2 = new String[50][4];
        String b = "";
        int d = 0;
        int e = -1;

        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == ',') {
                addNode(b.trim());
                e++;
                if (e > 3) {
                    d++;
                    e = 0;
                }
                s2[d][e] = search3(s2, b.trim(), d, e);
                b = "";
                continue;
            }
            b += a.charAt(i);
        }
    }

    //method to create a array of strings for each group of actors that 
    //worked together
    public static String search3(String[][] s2, String a, int i, int j) {

        s2[i][j] = a;
        if (i == 49 && j == 3) {
            //sending 2d array to other methods
            search4(s2);
            highestDegree(s2);
        }
        return s2[i][j];
    }

    //method to link actors that worked together
    public static void search4(String[][] a) {
  
        // linking actors using for loops to get actors worked with
        for(int i = 0; i < 50; i++){
            for(int j = 0; j < 4; j++){
                for(int k = 1; k < 4; k++){
                    if(!adj.get(a[i][j]).contains(a[i][k])
                            && !a[i][j].equals(a[i][k])){
                          addNeighbor(adj.get(a[i][j]), a[i][k].trim());
                    }
                    if(!adj.get(a[i][k]).contains(a[i][j]) 
                            && !a[i][k].equals(a[i][j]) ){
                          addNeighbor(adj.get(a[i][k]), a[i][j].trim());
                    }
                }
            }
        }
    }

    //method to find the highest degree
    public static void highestDegree(String[][] a) {
        
        //variables
        int high = 0;
        String empty = "";
        String name = "";
        
        //go through 2d array to get highest degree or degrees
        for(int i = 0; i < 50; i++){
            for (int j = 0; j < 4; j++){
                if (adj.get(a[i][j]).size() > high){
                    high = adj.get(a[i][j]).size();
                    name = empty;
                    name = a[i][j];
                    break;
                }
                
                if(adj.get(a[i][j]).size() == high && !name.contains(a[i][j])){
                    name += ", " + a[i][j];
                }
            }
        }

        System.out.print("Highest degree(s): ");
        System.out.println(name);
    }

    public static void main(String[] args) throws Exception {

        //create a graph
        Graph2 g = new Graph2();
        
        //import file
       File file = //new File("C:\\Users\\Micah\\Downloads\\IMDBDataset.tsv");
        new File("\\IMDBDataset.tsv");
        
        Scanner sc = new Scanner(file);

        //variable instances
        String[] a1 = new String[50];
        String[][] a2 = new String[50][3];

        //converting file to tab seperated array
        sc.nextLine();
        for (int i = 0; i < 50; i++) {
            a1[i] = sc.nextLine();
        }

        for (int i = 0; i < 50; i++) {
            int j = 0;
            for (String e : a1[i].split("\\t")) {
                a2[i][j] = e;
                j++;
            }
        }

        //converting array of actors to a string.
        String[][] a3 = new String[50][1];
        String[] a4 = new String[50];

        for (int i = 0; i < 50; i++) {
            a4[i] = a2[i][2];
        }

        for (int i = 0; i < 50; i++) {

            a3[i][0] = a2[i][2];
        }

        //list of actors as a string
        String s1 = new String();

        for (String temp : a4) {
            s1 += temp.trim() + ",";
        }

        //creating graph connecting actors.
       concate(s1);

        //print degrees
        System.out.print("Anne Hathaways degree: ");
        System.out.println(adj.get("Anne Hathaway").size());
       
    }
}
