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

        /*
        csvToDataFrame csv2df = new csvToDataFrame("Iris.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
        test.convertToNumeric();
        test.setToPredict("Species");
        RandomForest randomForest = new RandomForest(4,test,80,50,80, 50);

        ArrayList<Integer> results =  randomForest.forestResults();
        System.out.println();
        System.out.println(randomForest.howGoodIsOurForest(results));
         */

        /*
        csvToDataFrame csv2df = new csvToDataFrame("heart.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
        test.convertToNumeric();
        test.setToPredict("target");
        RandomForest randomForest = new RandomForest(4,test,30,50,80, 50);

        ArrayList<Integer> results =  randomForest.forestResults();
        System.out.println();
        System.out.println(randomForest.howGoodIsOurForest(results));
        */


        /*
        csvToDataFrame csv2df = new csvToDataFrame("pulsar_stars.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
        test.convertToNumeric();
        test.setToPredict("target_class");
        RandomForest randomForest = new RandomForest(9,test,20,50,80, 100);

        ArrayList<Integer> results =  randomForest.forestResults();
        System.out.println();
        System.out.println(randomForest.howGoodIsOurForest(results));

        // co jesli zgadywalibysmy zawsze,ze nie jest pulsarem?
        ArrayList<Integer> results2 = new ArrayList<>();
        for (int i =0 ; i < results.size(); i++){
            results2.add(0);
        }

        System.out.println("Wyniki gdyby zgadywać zawsze, że nie jest pulsarem");
        System.out.println(randomForest.howGoodIsOurForest(results2));

         */

        /*
        csvToDataFrame csv2df = new csvToDataFrame("data.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
        test.convertToNumeric();
        test.setToPredict("quality");

        RandomForest randomForest = new RandomForest(40,test,60,90,80, 100);

        ArrayList<Integer> results =  randomForest.forestResults();
        System.out.println();
        //approximately 43-50%
        System.out.println(randomForest.howGoodIsOurForest(results));

    }
}