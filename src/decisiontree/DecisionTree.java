package decisiontree;

import dataload.DataFrame;
import gini.Gini;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DecisionTree {

    int min_observation = 2;
    Node head = null;

    static ArrayList<String> colnames;
    ArrayList<Integer> Indexes;
    int max_depth;
    static DataFrame dataFrame;

    public DecisionTree(DataFrame dataFrame, ArrayList<Integer> Indexes, ArrayList<String> colnames, int max_depth) {
        this.Indexes = Indexes;
        this.colnames = colnames;
        this.max_depth = max_depth;
        this.head = head;
        this.dataFrame = dataFrame;
    }


    //@ToDo
    //jakas funckja na wszystkich danych kolumnach, licząca najmniejsze gini i zwracajaca dana kolumne
    //potem na tej kolumnie robimy split
    // znalezc dobre kryterium wyboru kolumny
    private static String FindMiniGini(ArrayList<String> colnames){
        return colnames.get(0);
    }

    public static double split(Node.Decision node, Gini gini) throws Exception {
        node.setColname(FindMiniGini(colnames));
        ArrayList<Double> dlist = dataFrame.getColumn(node.getColname());
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=0;i<dlist.size();i++){
            list.add((int) Math.round(dlist.get(i)));
        }
        Collections.sort(list);
        int bestSplitIndex = 0;
        ArrayList listPart1 = new ArrayList();
        ArrayList listPart2 = new ArrayList();
        for (int i=0;i<list.size();i++){
            if (list.get(i)<list.get(0)){
                listPart1.add(list.get(i));
            }
            else{
                listPart2.add(list.get(i));
            }
        }
        System.out.println(list);
        double gini1 = gini.calculateGiniIndex(listPart1);
        double gini2 = gini.calculateGiniIndex(listPart2);
        double bestSplitValue = Math.pow((Math.pow(gini1,2)+Math.pow(gini2,2))/2,0.5);
        for (int i=1;i<list.size();i++){
            listPart1 = new ArrayList();
            listPart2 = new ArrayList();
            for (int j=0;j<list.size();i++){
                if (list.get(j)<list.get(i)){
                    listPart1.add(list.get(j));
                }
                else{
                    listPart2.add(list.get(j));
                }
            }
            gini1 = gini.calculateGiniIndex(listPart1);
            gini2 = gini.calculateGiniIndex(listPart2);
            if(Math.pow((Math.pow(gini1,2)+Math.pow(gini2,2))/2,0.5)<bestSplitValue){
                bestSplitValue = Math.pow((Math.pow(gini1,2)+Math.pow(gini2,2))/2,0.5);
                bestSplitIndex = i;
            }
        }
        return bestSplitIndex;
    }

/*
    //@ToDo
    DecisionTree GrowTree(Node head,ArrayList<Integer> indexes, ArrayList<String> colnames, int max_depth){

    }

    //to dla użytkownika, aby nie musiał podawać head, tylko tworzyć drzewko bez niczego
    DecisionTree GrowTree() {
        return GrowTree(head, Indexes, colnames, max_depth);
    }
*/
}
