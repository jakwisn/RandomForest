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

        csvToDataFrame csv2df = new csvToDataFrame("data.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
        test.convertToNumeric();
        test.setToPredict("qquality");


        ArrayList<Integer> inds = new ArrayList<>();
        for (int i=0; i<test.getColumn("quality").size();i++){
            inds.add(i);
        }
        ArrayList<String> cols = new ArrayList<>();
        for (String s:test.getColnames()){
            cols.add(s);
        }


        DecisionTree dt = new DecisionTree(test, inds, cols, 9);
        dt.CultureTree();

        System.out.println(dt.search(1));
    }
}