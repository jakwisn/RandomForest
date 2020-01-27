package tests;

import dataload.DataFrame;
import dataload.csvToDataFrame;
import decisiontree.DecisionTree;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;

public class DecisionTreeTests {

    @Test
    public void PredictMethodsTests() throws Exception {
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

        ArrayList<Integer> indexestopredict = new ArrayList<>();
        indexestopredict.add(1);
        indexestopredict.add(10);
        indexestopredict.add(8);

        HashMap<String, ArrayList> df = test.getDataFrame();
        HashMap<String, ArrayList> topredict = new HashMap<>();
        ArrayList<Integer> valuesfrompredictcolumn = new ArrayList<>();

        for(int i : indexestopredict) {
            valuesfrompredictcolumn.add(test.getValuesToPredict().get(i));
        }
        ArrayList<String> cols2 = new ArrayList<>();
        cols2.add("Wealth");
        cols2.add("Sex");
        cols2.add("Age");
        for (String col : cols2) {
            ArrayList<Double> list = new ArrayList<>();
            for(int i : indexestopredict){
                list.add((Double) df.get(col).get(i));
            }
            topredict.put(col, list);
        }

        ArrayList dominants = dt.predict(new DataFrame(topredict, cols2));
        assertEquals(valuesfrompredictcolumn, dominants);
    }

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
