import dataload.DataFrame;
import dataload.csvToDataFrame;
import decisiontree.DecisionTree;
import decisiontree.Node;
import gini.Gini;
import randomforest.RandomForest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        csvToDataFrame csv2df = new csvToDataFrame("data.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
        test.convertToNumeric();
        test.setToPredict("quality");

        RandomForest randomForest = new RandomForest(40,test,60,90,80, 100);

        ArrayList<Integer> results =  randomForest.forestResults();
        System.out.println();
        //approximately 47%
        System.out.println(randomForest.howGoodIsOurForest(results));

    }
}