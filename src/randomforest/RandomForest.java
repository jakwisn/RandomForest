package randomforest;

import dataload.DataFrame;
import decisiontree.DecisionTree;
import decisiontree.Node;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;

public class RandomForest {
    int height;
    DataFrame dataFrame;
    double rowPercentage;
    double columnPercentage;
    double DivisionPercentage;
    int number_of_trees;
    ArrayList<DecisionTree> trees ;

    public RandomForest(int height, DataFrame dataFrame, double rowPercentage, double columnPercentage, double DivisionPercentage, int number_of_trees) {
        this.height = height;
        this.dataFrame = dataFrame;
        this.rowPercentage = rowPercentage;
        this.columnPercentage = columnPercentage;
        this.DivisionPercentage = DivisionPercentage;
        this.number_of_trees = number_of_trees;
        this.trees = trees;
    }

    //chcemy gdzies sprawdzac co przewiduje - czy ma ustawione - moze w train()

    public ArrayList<DataFrame> DivisionData() throws Exception {

        ArrayList<String> colnames = dataFrame.getColnames();
        int AllRowIndexes = dataFrame.getColumn(colnames.get(0)).size();

        int MaxRowForTrainingData = (int) Math.floor(DivisionPercentage/100 * AllRowIndexes);

        HashMap<String, ArrayList> data = dataFrame.getDataFrame();
        HashMap<String, ArrayList> trainingData = new HashMap<>();
        HashMap<String, ArrayList> testingData = new HashMap<>();

        if(DivisionPercentage >= 50.0){
            for(String column : colnames){
                ArrayList trainingRows = data.get(column);
                ArrayList testingRows = new ArrayList<>();
                for(int i = MaxRowForTrainingData+1; i < AllRowIndexes; i++){
                    testingRows.add(trainingRows.remove(MaxRowForTrainingData+1));
                }
                trainingData.put(column, trainingRows);
                testingData.put(column, testingRows);
        }
        }else{
            for(String column : colnames){
                ArrayList testingRows = data.get(column);
                ArrayList trainingRows = new ArrayList<>();
                for(int i = 0; i <= MaxRowForTrainingData; i++){
                    trainingRows.add(testingRows.remove(0));
                }
                trainingData.put(column, trainingRows);
                testingData.put(column, testingRows);
            }
        }

        ArrayList<DataFrame> listOfData = new ArrayList<>();
        DataFrame training = new DataFrame(trainingData, colnames);
        DataFrame testing = new DataFrame(testingData, colnames);

        listOfData.add(training);
        listOfData.add(testing);
        return listOfData;
    }

    public void train(DataFrame trainData) throws Exception {

        if (trainData.getColnameToPredict() == null ) {
            throw new Exception("Colname to predict has not been set!");
        }

        // Our target forest:
        ArrayList<DecisionTree> forest = new ArrayList<>();

        // empty arrays
        ArrayList<Integer> indexes = new ArrayList<>();
        ArrayList<String> colnames = new ArrayList<>();

        // getting size of rows and columns
        int rowSize = trainData.getValuesToPredict().size();
        int colSize = trainData.getColnames().size();

        // array of columns without the one to predict
        ArrayList<String> colnamesToPickFrom = new ArrayList<>();

        for (String col:trainData.getColnames()) {
            if (col.equals(trainData.getColnameToPredict())){continue;}
            colnamesToPickFrom.add(col);
        }
        Random random = new Random();

        // growing trees
        int index = 0 ;
        String colname;
        for (int i = 0; i < number_of_trees;  i++) {
            // preparing data for decisionTree
            for (int j = 0 ; j < rowPercentage*rowSize/100; j++) {
               index = random.nextInt(trainData.getValuesToPredict().size());
               indexes.add(index);
            }


            for (int j = 0 ; j < columnPercentage*colSize/100; j++) {
                colname = colnamesToPickFrom.get(random.nextInt(colnamesToPickFrom.size()));
                colnames.add(colname);
            }
            // with random colnames and rows lets grow tree
            DecisionTree decisionTree = new DecisionTree(trainData,indexes,colnames,height);
            System.out.println();
            decisionTree.CultureTree();
            forest.add(decisionTree);

            System.out.print("dupa \r");

        }

        this.trees = forest;

    }

    public ArrayList<Integer> test(DataFrame testData) throws Exception {
        ArrayList<ArrayList<Integer>> doms = new ArrayList<>();

        for (int i=0; i<trees.size();i++){
            doms.add(trees.get(i).predict(testData));
        }
        ArrayList<Integer> results = new ArrayList<>();
        ArrayList<Integer> resultsColumns = new ArrayList<>();

        for (int i=0 ; i < doms.get(0).size(); i++){
            for (int j=0; j < doms.size(); j++ ){
                resultsColumns.add(doms.get(j).get(i));
            }
            results.add(trees.get(0).dominant(resultsColumns));
        }

        return results;
    }

}

