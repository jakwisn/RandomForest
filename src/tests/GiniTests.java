package tests;
import dataload.DataFrame;
import dataload.csvToDataFrame;
import gini.Gini;
import org.junit.Assert;

import java.util.ArrayList;

public class GiniTests {

    @org.junit.Test
    public void Gini0() throws Exception {

        csvToDataFrame csv2df = new csvToDataFrame("test1.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
        test.convertToNumeric();

        test.setToPredict("Sex");
        Gini gini = new Gini(test);
        ArrayList<Integer> arrayOfIndexes = new ArrayList<>();
        arrayOfIndexes.add(0);

        Assert.assertEquals(0.0,gini.calculateGiniIndex(arrayOfIndexes),0.0000001);
    }



    @org.junit.Test
    public void Gini05() throws Exception {

        csvToDataFrame csv2df = new csvToDataFrame("test1.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
        test.convertToNumeric();

        test.setToPredict("Sex");
        Gini gini = new Gini(test);
        ArrayList<Integer> arrayOfIndexes = new ArrayList<>();
        arrayOfIndexes.add(0);
        arrayOfIndexes.add(1);
        arrayOfIndexes.add(2);
        arrayOfIndexes.add(3);

        Assert.assertEquals(0.5,gini.calculateGiniIndex(arrayOfIndexes),0.0000001);
    }

    @org.junit.Test
    public void GiniMoreClasses() throws Exception {

        csvToDataFrame csv2df = new csvToDataFrame("test1.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
        test.convertToNumeric();

        test.setToPredict("Name");
        Gini gini = new Gini(test);
        ArrayList<Integer> arrayOfIndexes = new ArrayList<>();
        arrayOfIndexes.add(0);
        arrayOfIndexes.add(1);
        arrayOfIndexes.add(2);
        arrayOfIndexes.add(3);
        arrayOfIndexes.add(4);
        double p = gini.calculateGiniIndex(arrayOfIndexes);
        Assert.assertEquals(0.72,p,0.0000001);
    }
}
