import dataload.DataFrame;
import dataload.csvToDataFrame;
import decisiontree.DecisionTree;
import decisiontree.Node;
import gini.Gini;
import randomforest.RandomForest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        csvToDataFrame csv2df = new csvToDataFrame("data.csv",",");
        DataFrame test = csv2df.convertToDataFrame();
        test.convertToNumeric();
        test.setToPredict("quality");



        RandomForest randomForest = new RandomForest(5,test,30,30,80, 20);

        ArrayList<DataFrame> test_train_split = randomForest.DivisionData();
        test_train_split.get(0).setToPredict("quality");
        test_train_split.get(1).setToPredict("quality");
        randomForest.train(test_train_split.get(0));

        ArrayList<Integer> results =  randomForest.test(test_train_split.get(1));

        DataFrame testujacy = test_train_split.get(1);
        ArrayList<Integer> genuineResults =  testujacy.getValuesToPredict();

        System.out.println(genuineResults);
        System.out.println(results);
        double suma = 0;
        for (int i = 0 ; i < genuineResults.size(); i++){
            if (results.get(i).equals(genuineResults.get(i))){
                suma ++;
            }
        }

        System.out.println(suma/genuineResults.size());



    }
}