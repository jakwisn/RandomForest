import dataload.DataFrame;
import dataload.csvToDataFrame;
import decisiontree.DecisionTree;
import decisiontree.Node;
import gini.Gini;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        csvToDataFrame csv2df = new csvToDataFrame("test.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
        test.convertToNumeric();
        test.setToPredict("HasCancer");

        ArrayList<Integer> inds = new ArrayList<>();
        inds.add(2);
        inds.add(3);
        inds.add(6);

        ArrayList<String> cols = new ArrayList<>();
        cols.add("Sex");
        cols.add("Wealth");
        cols.add("Age");
        DecisionTree dt = new DecisionTree(test, inds, cols, 3);
        dt.CultureTree();
    }
}