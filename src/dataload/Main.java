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
       csvToDataFrame csv2df = new csvToDataFrame("test.csv",",");
       DataFrame test = csv2df.convertToDataFrame();
       test.convertToNumeric();

        System.out.println(test.getColumn("Age"));
        System.out.println(test.getColumn("Pclass"));
/*
        ArrayList<String>  strings = new ArrayList<>();
        strings.add("pa");
        strings.add("g");
        strings.add("dsd");
        strings.add("sadas");
        strings.add("asda");
        strings.add("fdfd");
        strings.add("gfg");
        strings.add("hg");
        strings.add("hggg");
        strings.add("fds");
        strings.add("fsdf");
        test.setColnames(strings);
        test.setToPredict("g");
        System.out.println(test.getColnames());
        System.out.println(test.getValuesToPredict());

 */
        ArrayList inds = new ArrayList();
        ArrayList cols = new ArrayList();
        inds.add(0);
        inds.add(1);
        inds.add(2);
        inds.add(3);
        cols.add("Name");
        cols.add("Age");
        DecisionTree dt = new DecisionTree(test, inds, cols, 3);
        test.setToPredict("Sex");
        Gini gini = new Gini(test);

        Node node = new Node.Decision(inds,cols);
        System.out.println(dt.split(new Node.Decision(inds,cols), gini));

    }
}
