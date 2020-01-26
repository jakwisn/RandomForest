package decisiontree;

import dataload.DataFrame;
import gini.Gini;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.*;

public class DecisionTree {

    Node head;
    static ArrayList<String> colnames;
    ArrayList<Integer> Indexes;
    int max_depth;
    static DataFrame dataFrame;
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
    // returns Arraylist of 2: column name and split value
    public static ArrayList findBestSplit(ArrayList<String> colnames, ArrayList<Integer> indexes , Gini gini) throws Exception {

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


        List listTMP = list.stream().sorted().collect(Collectors.toList());
        ArrayList<Double> sorted = new ArrayList<Double>(listTMP);

        List<Double> splits = new ArrayList<>();
        int upTo = 0 ;
        while ( upTo < list.size()){
            splits.add(sorted.get(upTo));
            upTo += (int)Math.floor(list.size()/11) + 1;
        }

        int bestSplitIndex = 0;
        ArrayList listPart1;
        ArrayList listPart2;



        double gini1 = 2;
        double gini2 = 2;
        double bestSplitValue = 4;

        for (double split:splits){
            listPart1 = new ArrayList();
            listPart2 = new ArrayList();
            for (int j=0;j<indexes.size();j++){
                if (fullList.get(indexes.get(j))<split){
                    listPart1.add(indexes.get(j));
                }
                else{
                    listPart2.add(indexes.get(j));
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
                bestSplitIndex = list.indexOf(split);
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

        ArrayList columns = node.getColumns();
        columns.remove(node.getColname());

        // create node's children ang grow tree recursively
        if( node.gini1==0 || node.depth==2 || columns.size()==0){

            ArrayList vals = new ArrayList();
            for (int i=0;i<node.list1.size();i++){
                vals.add(dataFrame.getValuesToPredict().get(node.list1.get(i)));
            }
            System.out.println("creating Left Leaf " + node.list1);
            node.Left = new Node.Leaf(node.list1, dominant(vals));
        }
        else{
            System.out.println("creating Left dec " + node.list1);
            node.Left = new Node.Decision(node.list1, columns, node.depth-1);
            GrowTree((Node.Decision) node.Left);
        }
        if( node.gini2==0 || node.depth==2 || columns.size()==0){
            System.out.println("creating Right leaf " + node.list2);
            ArrayList vals = new ArrayList();
            for (int i=0;i<node.list2.size();i++){
                vals.add(dataFrame.getValuesToPredict().get(node.list2.get(i)));
            }
            node.Right = new Node.Leaf(node.list2, dominant(vals));
        }
        else{
            System.out.println("creating Right dec " + node.list2);
            node.Right = new Node.Decision(node.list2, columns, node.depth-1);
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

        System.out.println("Growing tree...");
        //when somebody gives max_depth == 1 then the tree only has a head
        if(max_depth == 1 || gini.calculateGiniIndex(Indexes) == 0){
            ArrayList vals = new ArrayList();
            for (int i=0;i<Indexes.size();i++){
                vals.add(dataFrame.getValuesToPredict().get(Indexes.get(i)));
            }
            head = new Node.Leaf(Indexes, dominant(vals));
              System.out.println("Head: " + ((Node.Leaf) head).getIndexes());
        } else {
            head = new Node.Decision(Indexes, colnames, max_depth);
             System.out.println("Head: " + ((Node.Decision) head).getIndexes());
            GrowTree((Node.Decision) head);
        }
        System.out.println("Tree created succesfully!");
    }

    // searches grown tree with given dataFrame index
    // returns probability that value predicted is the same that this of index, based on previous observations
    public ArrayList<Integer> predict(DataFrame data ) throws Exception {


        ArrayList<Integer> dominants = new ArrayList<>();
        for (int i=0 ; i <  data.getColumn(data.getColnames().get(0)).size(); i++ ) {

            int indexToFind = i;
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

    public int dominant(ArrayList<Integer> indexes){

        HashMap<Integer, Integer> values = new HashMap<>();
        //System.out.println(indexes);
        for (int i=0; i<indexes.size(); i++){
            if (values.containsKey(indexes.get(i))){
                int temp = values.get(indexes.get(i));
                values.put(indexes.get(i), temp+1);
            }
            else {
                values.put(indexes.get(i),1);
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
        //System.out.println(maxVal);
        return maxVal;
    }



}