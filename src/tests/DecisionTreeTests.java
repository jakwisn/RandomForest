package tests;

import dataload.DataFrame;
import dataload.csvToDataFrame;
import decisiontree.DecisionTree;
import org.junit.Assert;

import java.util.ArrayList;

public class DecisionTreeTests {

    @org.junit.Test
    public void SearchMethodsTests() throws Exception {
        csvToDataFrame csv2df = new csvToDataFrame("test.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
        test.convertToNumeric();
        test.setToPredict("HasCancer");

        ArrayList<Integer> inds = new ArrayList<>();
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

        ArrayList<String> cols = new ArrayList<>();
        cols.add("Wealth");
        cols.add("Sex");
        cols.add("Age");
        DecisionTree dt = new DecisionTree(test, inds, cols, 3);
        dt.CultureTree();

        //when we know indexes and value we can check this with function search();
        double val1 = dt.search(7);
        double val2 = dt.search(4);
        double val3 = dt.search(2);

        Assert.assertEquals(0.0, val3, 0.0);
        Assert.assertEquals(0.0, val2, 0.0);
        Assert.assertEquals(1.0, val1, 0.0);

    }

    //@ToDo
    @org.junit.Test
    public void IndexesWithTheSameValueTests() throws Exception {
        csvToDataFrame csv2df = new csvToDataFrame("test.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
        test.convertToNumeric();
        test.setToPredict("HasCancer");

        ArrayList<Integer> inds = new ArrayList<>();
        inds.add(2);
        inds.add(3);
        inds.add(6);

        ArrayList<String> cols = new ArrayList<>();
        cols.add("Wealth");
        cols.add("Sex");
        cols.add("Age");
        DecisionTree dt = new DecisionTree(test, inds, cols, 3);
        dt.CultureTree();
    }

    @org.junit.Test
    public void DepthTests() throws Exception {
        csvToDataFrame csv2df = new csvToDataFrame("test.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
        test.convertToNumeric();
        test.setToPredict("HasCancer");

        ArrayList<Integer> inds = new ArrayList<>();
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

        ArrayList<String> cols = new ArrayList<>();
        cols.add("Wealth");
        cols.add("Sex");
        cols.add("Age");

        //max_depth < tree depth
        DecisionTree dt1 = new DecisionTree(test, inds, cols, 2);
        dt1.CultureTree();
        //max_depth < tree depth and tree has only a head
        DecisionTree dt2 = new DecisionTree(test, inds, cols, 1);
        dt2.CultureTree();
        //max_depth > tree depth
        DecisionTree dt = new DecisionTree(test, inds, cols, 5);
        dt.CultureTree();
    }

    @org.junit.Test(expected = Exception.class)
    public void ColnamesTests() throws Exception {
        csvToDataFrame csv2df = new csvToDataFrame("test.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
        test.convertToNumeric();
        test.setToPredict("HasCancer");

        ArrayList<Integer> inds = new ArrayList<>();
        inds.add(2);
        inds.add(5);
        inds.add(3);
        inds.add(2);
        inds.add(4);
        inds.add(7);

        ArrayList<String> cols = new ArrayList<>();
        cols.add("Wealth");
        cols.add("noexistingcolumn");
        cols.add("Age");

        DecisionTree dt = new DecisionTree(test, inds, cols, 3);
        dt.CultureTree();
    }

    @org.junit.Test(expected = Exception.class)
    public void IndexOutOfBoundsForLengthColumnsTests() throws Exception {
        csvToDataFrame csv2df = new csvToDataFrame("test.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
        test.convertToNumeric();
        test.setToPredict("HasCancer");

        ArrayList<Integer> inds = new ArrayList<>();
        inds.add(2);
        inds.add(5);
        inds.add(3);
        inds.add(100);

        ArrayList<String> cols = new ArrayList<>();
        cols.add("Wealth");
        cols.add("Sex");
        cols.add("Age");

        DecisionTree dt = new DecisionTree(test, inds, cols, 3);
        dt.CultureTree();
    }
}
