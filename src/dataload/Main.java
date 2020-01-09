package dataload;

import decisiontree.DecisionTree;
import decisiontree.Node;
import gini.Gini;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
       csvToDataFrame csv2df = new csvToDataFrame("split_gini_test.csv",",");
       DataFrame test = csv2df.convertToDataFrame();
       test.convertToNumeric();
       test.setToPredict("HasCancer");

       Gini gini = new Gini(test);
       ArrayList<Integer> inds = new ArrayList<>();
        inds.add(10);
        inds.add(2);
        inds.add(5);
        inds.add(3);
        inds.add(2);
        inds.add(4);
        inds.add(7);
        inds.add(6);
        inds.add(5);
        inds.add(5);
        inds.add(2);
        inds.add(12);




        ArrayList<String> cols = new ArrayList<>();
        cols.add("Wealth");
        cols.add("Sex");
        cols.add("Sex");
        cols.add("eyes");
        DecisionTree dt = new DecisionTree(test, inds, cols, 5);
        dt.hoduj();



    }
}
