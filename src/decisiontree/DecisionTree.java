package decisiontree;

import java.util.ArrayList;

public class DecisionTree {

    int min_observation = 2;
    Node head = null;

    ArrayList<String> colnames;
    ArrayList<Integer> Indexes;
    int max_depth;

    public DecisionTree(ArrayList<Integer> Indexes, ArrayList<String> colnames, int max_depth) {
        this.Indexes = Indexes;
        this.colnames = colnames;
        this.max_depth = max_depth;
        this.head = head;
    }


    //@ToDo
    //jakas funckja na wszystkich danych kolumnach, licząca najmniejsze gini i zwracajaca dana kolumne
    //potem na tej kolumnie robimy split
    private static String FindMiniGini(ArrayList<String> colnames){

    }

    //@ToDo
    static double split(String colname){

    }


    //@ToDo
    DecisionTree GrowTree(Node head,ArrayList<Integer> indexes, ArrayList<String> colnames, int max_depth){

    }

    //to dla użytkownika, aby nie musiał podawać head, tylko tworzyć drzewko bez niczego
    DecisionTree GrowTree() {
        return GrowTree(head, Indexes, colnames, max_depth);
    }

}
