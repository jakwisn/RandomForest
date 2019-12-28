package dataload;

import gini.Gini;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
       csvToDataFrame csv2df = new csvToDataFrame("test.csv",",");
       DataFrame test = csv2df.convertToDataFrame();
       System.out.println(test.getColumn("Pclass"));
       test.convertToNumeric();
       System.out.println(test.getColumn("Pclass"));


       test.setToPredict("Sex");
       Gini gini = new Gini(test);
        ArrayList<Integer> arrayOfIndexes = new ArrayList<>();
        arrayOfIndexes.add(0);

       System.out.println( gini.calculateGiniIndex(arrayOfIndexes) );
    }

}
