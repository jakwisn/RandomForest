package tests;

import dataload.*;
import org.junit.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataLoadTests {

    @org.junit.Test
    public void DataFrameGetColumns() throws IOException, CustomException {

        csvToDataFrame csv2df = new csvToDataFrame("test.csv",",");
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
    public void ReadColumnsFile() throws IOException{
        csvToDataFrame csv2df = new csvToDataFrame("test.csv",",");
        ArrayList<String> colnames = csv2df.getColnames();

        Assert.assertEquals("Name",colnames.get(2));
        Assert.assertEquals("Parch",colnames.get(6));
        Assert.assertEquals(11,colnames.size());
    }

    @org.junit.Test
    public void DataFrameGetColnames() throws IOException, CustomException {
        csvToDataFrame csv2df = new csvToDataFrame("test.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
        ArrayList<String> colnames = test.getColnames();

        Assert.assertEquals("Name",colnames.get(2));
        Assert.assertEquals("Parch",colnames.get(6));
        Assert.assertEquals(11,colnames.size());
    }

    @org.junit.Test
    public void GetDataFrame() throws IOException, CustomException {
        csvToDataFrame csv2df = new csvToDataFrame("test.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
        HashMap<String, ArrayList> df = test.getDataFrame();

        Assert.assertEquals(11, df.size());
        Assert.assertEquals(true, df.containsKey("Pclass"));
        Assert.assertEquals(false, df.containsKey("Pclass2"));

        List<String> data1 = new ArrayList<>();
        data1.add("ser");
        data1.add("bor");
        data1.add("thor");
        data1.add("ser");
        data1.add("thor");

        Assert.assertEquals(true, df.containsValue(data1));

    }


    @org.junit.Test(expected = RuntimeException.class)
    public void ReadNonExsitingFile() throws IOException, CustomException {
        csvToDataFrame csv2df = new csvToDataFrame("noexistingfile.csv",",");
        DataFrame test = csv2df.convertToDataFrame();

    }

    @org.junit.Test(expected = RuntimeException.class)
    public void ReadColumnsNonExsitingFile() throws IOException{
        csvToDataFrame csv2df = new csvToDataFrame("noexistingfile.csv",",");
        ArrayList<String> colnames = csv2df.getColnames();

    }

    //ToDo
    @org.junit.Test(expected = NullPointerException.class)
    public void ReadEmptyFile() throws IOException, CustomException {
        csvToDataFrame csv2df = new csvToDataFrame("emptyfile.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
    }

    //ToDo
    @org.junit.Test(expected = NullPointerException.class)
    public void ReadColumnsEmptyFile() throws IOException{
        csvToDataFrame csv2df = new csvToDataFrame("emptyfile.csv",",");
        ArrayList<String> colnames = csv2df.getColnames();
    }

    //ToDo
    @org.junit.Test(expected = IOException.class)
    public void ReadWrongFormatFile() throws IOException, CustomException {
        csvToDataFrame csv2df = new csvToDataFrame("Konspekt.pdf",",");
        DataFrame test = csv2df.convertToDataFrame();
    }

    //ToDo
    @org.junit.Test(expected = IOException.class)
    public void ReadColumnsWrongFormatFile() throws IOException, CustomException {
        csvToDataFrame csv2df = new csvToDataFrame("Konspekt.pdf",",");
        ArrayList<String> colnames = csv2df.getColnames();
    }

    @org.junit.Test(expected = CustomException.class)
    public void ConvertFileWithMixedColumns() throws IOException, CustomException {
        csvToDataFrame csv2df = new csvToDataFrame("test1.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
    }



}
