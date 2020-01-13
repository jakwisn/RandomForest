package tests;

import dataload.*;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataLoadTests {

    @org.junit.Test
    public void DataFrameGetColumns() throws Exception {

        csvToDataFrame csv2df = new csvToDataFrame("test1.csv",",");
        DataFrame test = csv2df.convertToDataFrame();

        List<String> data1 = new ArrayList<>();
        data1.add("ser");
        data1.add("bor");
        data1.add("thor");
        data1.add("ser");
        data1.add("thor");

        Assert.assertEquals(data1, test.getColumn("Pclass"));

        test.convertToNumeric();
        List<Number> data2 = new ArrayList<>();
        data2.add(0);
        data2.add(1);
        data2.add(2);
        data2.add(0);
        data2.add(2);
        Assert.assertEquals(data2,test.getColumn("Pclass"));

    }

    @org.junit.Test
    public void ReadColumnsFile() throws Exception {
        csvToDataFrame csv2df = new csvToDataFrame("test1.csv",",");
        ArrayList<String> colnames = csv2df.getColnames();

        Assert.assertEquals("Name",colnames.get(2));
        Assert.assertEquals("Parch",colnames.get(6));
        Assert.assertEquals(11,colnames.size());
    }

    @org.junit.Test
    public void DataFrameGetColnames() throws Exception {
        csvToDataFrame csv2df = new csvToDataFrame("test1.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
        ArrayList<String> colnames = test.getColnames();

        Assert.assertEquals("Name",colnames.get(2));
        Assert.assertEquals("Parch",colnames.get(6));
        Assert.assertEquals(11,colnames.size());
    }

    @org.junit.Test
    public void GetDataFrame() throws Exception {
        csvToDataFrame csv2df = new csvToDataFrame("test1.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
        HashMap<String, ArrayList> df = test.getDataFrame();

        Assert.assertEquals(11, df.size());
        Assert.assertTrue(df.containsKey("Pclass"));
        Assert.assertFalse(df.containsKey("Pclass2"));

        List<String> data1 = new ArrayList<>();
        data1.add("ser");
        data1.add("bor");
        data1.add("thor");
        data1.add("ser");
        data1.add("thor");

        Assert.assertTrue(df.containsValue(data1));

    }


    @org.junit.Test(expected = RuntimeException.class)
    public void ReadNonExsitingFile() throws Exception {
        csvToDataFrame csv2df = new csvToDataFrame("noexistingfile.csv",",");
        DataFrame test = csv2df.convertToDataFrame();

    }

    @org.junit.Test(expected = RuntimeException.class)
    public void ReadColumnsNonExsitingFile() throws Exception {
        csvToDataFrame csv2df = new csvToDataFrame("noexistingfile.csv",",");
        ArrayList<String> colnames = csv2df.getColnames();

    }


    @org.junit.Test(expected = Exception.class)
    public void ReadEmptyFile() throws Exception {
        csvToDataFrame csv2df = new csvToDataFrame("emptyfile.csv",",");
        DataFrame test = csv2df.convertToDataFrame();


    }


    @org.junit.Test(expected = Exception.class)
    public void ReadColumnsEmptyFile() throws Exception {
        csvToDataFrame csv2df = new csvToDataFrame("emptyfile.csv",",");
        ArrayList<String> colnames = csv2df.getColnames();

    }

    @org.junit.Test
    public void ReadNumericColumn() throws Exception {
        csvToDataFrame csv2df = new csvToDataFrame("test1.csv",",");
        DataFrame df = csv2df.convertToDataFrame();
        df.convertToNumeric();
        System.out.println(df.getColumn("Age").get(1));
        Assert.assertEquals(df.getColumn("Age").get(1), 47.0, 0001);
    }


    @org.junit.Test(expected = Exception.class)
    public void ReadWrongFormatFile() throws Exception {
        csvToDataFrame csv2df = new csvToDataFrame("Konspekt.pdf",",");
        DataFrame test = csv2df.convertToDataFrame();
    }


    @org.junit.Test(expected = Exception.class)
    public void ReadColumnsWrongFormatFile() throws Exception {
        csvToDataFrame csv2df = new csvToDataFrame("Konspekt.pdf",",");
        ArrayList<String> colnames = csv2df.getColnames();
    }

    @org.junit.Test(expected = CustomException.class)
    public void ConvertFileWithMixedColumns() throws Exception {
        csvToDataFrame csv2df = new csvToDataFrame("test2.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
    }

    @org.junit.Test(expected = Exception.class)
    public void NoColumnInDataFrame() throws Exception {
        csvToDataFrame csv2df = new csvToDataFrame("test.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
        test.convertToNumeric();
        test.setToPredict("noname");
    }



}
