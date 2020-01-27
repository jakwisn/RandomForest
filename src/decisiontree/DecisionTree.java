package decisiontree;

import dataload.DataFrame;
import gini.Gini;

import java.util.*;
import java.util.stream.Collectors;

public class DecisionTree {

    private Node head;
    private static ArrayList<String> colnames;
    private ArrayList<Integer> Indexes;
    private int max_depth;
    private static DataFrame dataFrame;
    private Gini gini;

    public DecisionTree(DataFrame dataFrame, ArrayList<Integer> Indexes, ArrayList<String> colnames, int max_depth) {
        this.Indexes = Indexes;
        this.colnames = colnames;
        this.max_depth = max_depth;
        this.head = null;
        this.dataFrame = dataFrame;
        this.gini = new Gini(dataFrame);
    }


    // method checks indexes
    // if index doesn't appear in the dataFrame then method returns exception
    private void CheckIndexes() throws Exception {

        int max_index = dataFrame.getColumn(dataFrame.getColnames().get(0)).size() - 1;
        for(int i : Indexes){
            if(i > max_index){
                throw new Exception("In DataFrame there is no index " + i);
            }
        }
    }

    // method finds the best split point from all given columns and their indexes
    // returns ArrayList of 2: column name and split value
    private static ArrayList findBestSplit(ArrayList<String> colnames, ArrayList<Integer> indexes, Gini gini) throws Exception {

        double bestGiniSplit= 10;
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

    // method finds best split point in a particular column
    // used by findBestSplit()
    private static ArrayList split(String colname,ArrayList<Integer> indexes, Gini gini) throws Exception {

        ArrayList<Double> fullList = dataFrame.getColumn(colname);
        ArrayList<Double> list = new ArrayList<>();
        for (int i:indexes){list.add(fullList.get(i));}


        // sorting values on our indexes
        ArrayList<Double> tmpList2 = list.stream().sorted().collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Double> splits = new ArrayList<>();


        ArrayList<Integer> listPart1;
        ArrayList<Integer> listPart2;

        int bestSplitIndex = 1;

        for (int i=0 ; i < list.size(); i += (int)Math.floor(list.size()/10) +1){
            splits.add(list.get(i));
        }

        // comparing splits by weighted arithmetic mean with weights being numbers of elements in both splits
        double gini1 = 2;
        double gini2 = 2;
        double bestSplitValue = 4;

        for (double split:splits){
            listPart1 = new ArrayList<>();
            listPart2 = new ArrayList<>();
            for (Integer index : indexes) {
                if (fullList.get(index) < split) {
                    listPart1.add(index);
                } else {
                    listPart2.add(index);
                }
            }

            gini1 = gini.calculateGiniIndex(listPart1);
            gini2 = gini.calculateGiniIndex(listPart2);

            // adding "penalty" to the split value if one of its parts is empty
            double x;
            if (listPart1.size()*listPart2.size()==0) x = 1;
            else x = 0;
            if((gini1*listPart1.size()+gini2*listPart2.size())/(list.size())+x<bestSplitValue){
                bestSplitValue = (gini1*listPart1.size()+gini2*listPart2.size())/(list.size());
                bestSplitIndex = (int)list.indexOf(split);
            }
        }

        ArrayList toReturn = new ArrayList();
        toReturn.add(bestSplitValue);
        toReturn.add(bestSplitIndex);
        return toReturn;
    }

    // recursive method building tree from given node; called by grow() method
    private void GrowTree(Node.Decision node) throws Exception {

        // finds best split and saves it as node field
        ArrayList bestSplit = findBestSplit( node.getColumns(), node.getIndexes(), gini);
        node.setVal((double)bestSplit.get(1));


        node.setColname((String)bestSplit.get(0));
        // divide indexes according to best split
        ArrayList list1 = new ArrayList();
        ArrayList list2 = new ArrayList();
        for (int i=0; i<node.Indexes.size(); i++){
            if (dataFrame.getColumn(node.getColname()).get(node.Indexes.get(i)) >= node.val){
                list2.add(node.Indexes.get(i));
            }
            else{list1.add(node.Indexes.get(i));}
        }

        node.gini1 = gini.calculateGiniIndex(list1);
        node.gini2 = gini.calculateGiniIndex(list2);

        node.list1 = list1;
        node.list2 = list2;

        node.getColumns().remove(node.getColname());

        // case if list1 or list2 is empty
        // we get other column
        while (node.columns.size()>0 && node.list1.size()*node.list2.size() ==0){

            node.getColumns().remove(node.colname);
            if (node.getColumns().size() == 0){
                // columns are empty, there will be leaf created
                break;
            }
            // code same as above
            bestSplit = findBestSplit( node.getColumns(), node.getIndexes(), gini);
            node.setVal((double)bestSplit.get(1));
            node.setColname((String)bestSplit.get(0));

            // divide indexes according to best split
             list1 = new ArrayList();
             list2 = new ArrayList();
            for (int i=0; i<node.Indexes.size(); i++){
                if (dataFrame.getColumn(node.getColname()).get(node.Indexes.get(i)) >= node.val){
                    list2.add(node.Indexes.get(i));
                }
                else{list1.add(node.Indexes.get(i));}
            }

            node.gini1 = gini.calculateGiniIndex(list1);
            node.gini2 = gini.calculateGiniIndex(list2);

            node.list1 = list1;
            node.list2 = list2;

        }

        // create node's children ang grow tree recursively
        if( node.gini1==0 || node.depth==2 || node.getColumns().size()==0){

            ArrayList<Integer> vals = new ArrayList<>();
            for (int i=0;i<node.list1.size();i++){
                vals.add(dataFrame.getValuesToPredict().get(node.list1.get(i)));
            }
            //System.out.println("creating Left leaf " + node.list1);
            node.Left = new Node.Leaf(node.list1, dominant(vals));
        }
        else{
            //System.out.println("creating Left dec " + node.list1);
            node.Left = new Node.Decision(node.list1, node.getColumns(), node.depth-1);
            GrowTree((Node.Decision) node.Left);
        }
        if( node.gini2==0 || node.depth==2 || node.getColumns().size()==0){
            ArrayList<Integer> vals = new ArrayList<>();
            for (int i=0;i<node.list2.size();i++){
                vals.add(dataFrame.getValuesToPredict().get(node.list2.get(i)));
            }
            //System.out.println("creating Right leaf " + node.list2);
            node.Right = new Node.Leaf(node.list2, dominant(vals));
        }
        else{
            //System.out.println("creating Right dec " + node.list2);
            node.Right = new Node.Decision(node.list2, node.getColumns(), node.depth-1);
            GrowTree((Node.Decision) node.Right);
        }
    }

    // grow tree, soon possibly added to DecisionTree constructor
    public void CultureTree() throws Exception {

        for(String col : colnames){
            if (!dataFrame.getColnames().contains(col)){
                throw new Exception("In DataFrame there is no column named: " + col);
            }
        }
        CheckIndexes();

        //System.out.println("Growing tree...");

        //when somebody gives max_depth == 1 then the tree only has a head
        if(max_depth == 1 || gini.calculateGiniIndex(Indexes) == 0){
            ArrayList<Integer> vals = new ArrayList<>();
            for (Integer index : Indexes) {
                vals.add(dataFrame.getValuesToPredict().get(index));
            }
            head = new Node.Leaf(Indexes, dominant(vals));
            //  System.out.println("Head: " + ((Node.Leaf) head).getIndexes());
        } else {
            head = new Node.Decision(Indexes, colnames, max_depth);
            // System.out.println("Head: " + ((Node.Decision) head).getIndexes());
            GrowTree((Node.Decision) head);
        }
        //System.out.println("Tree created successfully!");
    }

    //predict a value for each row from the given DataFrame
    public ArrayList<Integer> predict(DataFrame data) throws Exception {

        ArrayList<Integer> dominants = new ArrayList<>();
        int size = data.getColumn(data.getColnames().get(0)).size();
        for (int indexToFind=0 ; indexToFind < size ; indexToFind++ ) {

            CheckIndexes();

            Node curNode = head;
            String col;
            // go to tree's leaf
            while (curNode instanceof Node.Decision) {
                col = ((Node.Decision) curNode).getColname();
                if (data.getColumn(col).get(indexToFind) < ((Node.Decision) curNode).getVal()) {
                    curNode = ((Node.Decision) curNode).getLeft();
                } else curNode = ((Node.Decision) curNode).getRight();
            }
            dominants.add(((Node.Leaf) curNode).getDominant());
        }

        return dominants;
    }

    // calculates the dominant for the given values from the Predict column
    // used by CultureTree()
    public int dominant(ArrayList<Integer> vals){

        HashMap<Integer, Integer> values = new HashMap<>();
        for (Integer v : vals) {
            if (values.containsKey(v)) {
                int temp = values.get(v);
                values.put(v, temp + 1);
            } else {
                values.put(v, 1);
            }
        }
        int max = 0;
        int maxVal = -1;

        for (int i:values.keySet()){
            if (values.get(i) > max){
                maxVal = i;
                max = values.get(i);
            }
        }

        return maxVal;
    }

    public ArrayList<Double> calculatePercentages(ArrayList<Integer> indexes){
        List uniques =  dataFrame.getValuesToPredict().stream().distinct().collect(Collectors.toList());
        ArrayList<Double> probOfClass = new ArrayList<Double>(uniques);

        HashMap<Integer, Double> values = new HashMap<>();

        for (Integer index : indexes) {
            if (values.containsKey(index)) {
                double temp = values.get(index);
                values.put(index, temp + 1);
            } else {
                values.put(index, (double) 1);
            }
        }
        for(int key:values.keySet()){
            values.put(key,values.get(key)/indexes.size());

        }
        for(int key:values.keySet()){
            probOfClass.set(key,values.get(key));
        }
        return  probOfClass;
    }


}
