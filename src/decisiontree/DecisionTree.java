package decisiontree;

import dataload.DataFrame;
import gini.Gini;

import java.util.*;

public class DecisionTree {


    int min_observation = 2;
    Node.Decision head = null;

    static ArrayList<String> colnames;
    ArrayList<Integer> Indexes;
    int max_depth;
    static DataFrame dataFrame;
    private Gini gini;

    public DecisionTree(DataFrame dataFrame, ArrayList<Integer> Indexes, ArrayList<String> colnames, int max_depth) {
        this.Indexes = Indexes;
        this.colnames = colnames;
        this.max_depth = max_depth;
        this.head = head;
        this.dataFrame = dataFrame;
        this.gini = new Gini(dataFrame);
    }


    //@ToDo
    //jakas funckja na wszystkich danych kolumnach, licząca najmniejsze gini i zwracajaca dana kolumne
    //potem na tej kolumnie robimy split
    // znalezc dobre kryterium wyboru kolumny
    public static ArrayList findColToSplit(ArrayList<String> colnames, ArrayList<Integer> indexes , Gini gini) throws Exception {
        // Zwraca Arraylista (Nazwa kolumny, Wartość po której splitujemy)

        double bestGiniSplit= 9;
        int bestSplit = 0;
        String bestCol = "";
        for (String colname:colnames){
            ArrayList tmp = split(colname,indexes,gini);
            double giniSplit = (double) tmp.get(0);
            int splitOnThis = (int)tmp.get(1);

            if (giniSplit < bestGiniSplit){
                bestGiniSplit = giniSplit;
                bestSplit = splitOnThis;
                bestCol = colname;
            }
        }

        ArrayList toReturn = new ArrayList();
        toReturn.add(bestCol);
        toReturn.add(dataFrame.getColumn(bestCol).get(indexes.get(bestSplit)));

        return toReturn;

    }



    // dla node znajduje najlepszy punkt splitu
    public static ArrayList split(String colname,ArrayList<Integer> indexes, Gini gini) throws Exception {
        ArrayList<Double> fullList = dataFrame.getColumn(colname);
        ArrayList<Double> list = new ArrayList<>();

        ArrayList<Integer> integers = new ArrayList<>();
        for (int i=0 ; i< indexes.size(); i++){

        }


        for (int i:indexes){
            list.add(fullList.get(i));
        }

        int bestSplitIndex = 0;
        ArrayList listPart1 = new ArrayList();
        ArrayList listPart2 = new ArrayList();
        for (int i=0;i<indexes.size();i++){
            if (fullList.get(indexes.get(i))<fullList.get(indexes.get(0))){
                listPart1.add(indexes.get(i));
            }
            else{
                listPart2.add(indexes.get(i));
            }
        }

        double gini1 = gini.calculateGiniIndex(listPart1);
        double gini2 = gini.calculateGiniIndex(listPart2);
        double bestSplitValue = (gini1*listPart1.size()+gini2*listPart2.size())/(list.size());

        for (int i=1;i<list.size();i++){
            listPart1 = new ArrayList();
            listPart2 = new ArrayList();
            for (int j=0;j<indexes.size();j++){
                if (fullList.get(indexes.get(j))<fullList.get(indexes.get(i))){
                    listPart1.add(indexes.get(j));
                }
                else{
                    listPart2.add(indexes.get(j));
                }
            }

            gini1 = gini.calculateGiniIndex(listPart1);
            gini2 = gini.calculateGiniIndex(listPart2);
            double x;
            if (listPart1.size()*listPart2.size()==0) x = 1;
            else x = 0;
            if((gini1*listPart1.size()+gini2*listPart2.size())/(list.size())+x<bestSplitValue){
                bestSplitValue = (gini1*listPart1.size()+gini2*listPart2.size())/(list.size());
                bestSplitIndex = i;
            }
        }



        ArrayList toReturn = new ArrayList();
        toReturn.add(bestSplitValue);
        toReturn.add(bestSplitIndex);
        return toReturn;
    }


    void GrowTree(Node.Decision node) throws Exception {
        ArrayList bestSplit = findColToSplit( node.getColumns(), node.getIndexes(), gini);
        node.setVal((double)bestSplit.get(1));
        node.setColname((String)bestSplit.get(0));

        ArrayList list1 = new ArrayList();
        ArrayList list2 = new ArrayList();
        for (int i=0; i<node.Indexes.size(); i++){
           if (dataFrame.getColumn(node.getColname()).get(node.Indexes.get(i)) >= node.val){
               list2.add(node.Indexes.get(i));
           }
           else{
               list1.add(node.Indexes.get(i));
           }
        }

        node.gini1 = gini.calculateGiniIndex(list1);
        node.gini2 = gini.calculateGiniIndex(list2);

        node.list1 = list1;
        node.list2 = list2;



        ArrayList columns = node.getColumns();
        columns.remove(node.getColname());

        if( node.gini1==0 || node.depth==2 || columns.size()==0){
            System.out.println("creating Left leaf " + node.list1);
            node.Left = new Node.Leaf(node.list1);
        }
        else{
            System.out.println("creating Left dec " + node.list1);
            node.Left = new Node.Decision(node.list1, columns, node.depth-1);
            GrowTree((Node.Decision) node.Left);
        }
        if( node.gini2==0 || node.depth==2 || columns.size()==0){
            System.out.println("creating Right leaf " + node.list2);
            node.Right = new Node.Leaf(node.list2);
        }
        else{
            System.out.println("creating Right dec " + node.list2);
            node.Right = new Node.Decision(node.list2, columns, node.depth-1);
            GrowTree((Node.Decision) node.Right);

        }
        return;
    }

    public void hoduj() throws Exception {
        System.out.println("Growing tree...");
        head = new Node.Decision(Indexes, colnames, max_depth);
        System.out.println("Head: " + head.getIndexes());
        GrowTree(head);
        System.out.println("Tree created succesfully!");
    }

    public double search(int indexToFind) throws Exception {
        Node curNode = head;
        String col;
        while(curNode instanceof Node.Decision){
            col = ((Node.Decision) curNode).getColname();
            if(dataFrame.getColumn(col).get(indexToFind) < ((Node.Decision) curNode).getVal()){
                curNode = ((Node.Decision) curNode).getLeft();
            }
            else curNode = ((Node.Decision) curNode).getRight();
        }
        ArrayList<Integer> indexes = curNode.Indexes;
        System.out.println(indexes);
        ArrayList<Integer> vals = new ArrayList<>();
        double ones=0;
        for (int i=0; i<indexes.size(); i++){
            if (dataFrame.getValuesToPredict().get(indexes.get(i)) == 1) ones++;
        }
        return ones/indexes.size();
    }

/*
    //to dla użytkownika, aby nie musiał podawać head, tylko tworzyć drzewko bez niczego
    DecisionTree GrowTree() {
        return GrowTree(head, Indexes, colnames, max_depth);
    }
*/
}
