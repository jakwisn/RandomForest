package randomforest;

import dataload.DataFrame;
import decisiontree.DecisionTree;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;

public class RandomForest {

    private int height;
    private DataFrame dataFrame;
    private double rowPercentage;
    private double columnPercentage;
    // percentage of rows for the trainData
    private double DivisionPercentage;
    private int number_of_trees;

    private ArrayList<DecisionTree> trees;
    private DataFrame trainData;
    private DataFrame testData;

    public RandomForest(int height, DataFrame dataFrame, double rowPercentage, double columnPercentage, double DivisionPercentage, int number_of_trees) {
        this.height = height;
        this.dataFrame = dataFrame;
        this.rowPercentage = rowPercentage;
        this.columnPercentage = columnPercentage;
        this.DivisionPercentage = DivisionPercentage;
        this.number_of_trees = number_of_trees;
        this.trees = null;
        this.testData = null;
        this.trainData = null;
    }

    //divides dataFrame into training and test DataFrame using DivisionPercentage
    private void DivisionData() throws Exception {

        ArrayList<String> colnames = dataFrame.getColnames();
        int AllRowIndexes = dataFrame.getColumn(colnames.get(0)).size();

        int MaxRowForTrainingData = (int) Math.floor(DivisionPercentage/100 * AllRowIndexes);

        HashMap<String, ArrayList> data = dataFrame.getDataFrame();
        HashMap<String, ArrayList> trainingData = new HashMap<>();
        HashMap<String, ArrayList> testingData = new HashMap<>();

        if(DivisionPercentage >= 50.0){
            for(String column : colnames){
                ArrayList trainingRows = data.get(column);
                ArrayList testingRows = new ArrayList();
                for(int i = MaxRowForTrainingData+1; i < AllRowIndexes; i++){
                    testingRows.add(trainingRows.remove(MaxRowForTrainingData+1));
                }
                trainingData.put(column, trainingRows);
                testingData.put(column, testingRows);
        }
        }else{
            for(String column : colnames){
                ArrayList testingRows = data.get(column);
                ArrayList trainingRows = new ArrayList();
                for(int i = 0; i <= MaxRowForTrainingData; i++){
                    trainingRows.add(testingRows.remove(0));
                }
                trainingData.put(column, trainingRows);
                testingData.put(column, testingRows);
            }
        }

        DataFrame training = new DataFrame(trainingData, colnames);
        DataFrame testing = new DataFrame(testingData, colnames);
        training.setToPredict(dataFrame.getColnameToPredict());
        testing.setToPredict(dataFrame.getColnameToPredict());
        trainData = training;
        testData = testing;
    }

    // creates a forest from the trainData for later prediction
    private void train() throws Exception {

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
        int index;
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

            System.out.print(i+1);

        }

        this.trees = forest;

    }

    //uses a testData to test forest quality
    //return ArrayList of dominants for each row from the testData
    private ArrayList<Integer> test() throws Exception {

        ArrayList<ArrayList<Integer>> doms = new ArrayList<>();

        for (DecisionTree tree : trees) {
            doms.add(tree.predict(testData));
        }
        ArrayList<Integer> results = new ArrayList<>();
        ArrayList<Integer> resultsRows = new ArrayList<>();

        for (int i=0 ; i < doms.get(0).size(); i++){
            for (ArrayList<Integer> dom : doms) {
                resultsRows.add(dom.get(i));
            }
            results.add(trees.get(0).dominant(resultsRows));
        }

        return results;
    }

    //this method is public and return results from test()
    //using DivisionData() and train()
    public ArrayList<Integer> forestResults() throws Exception {

        DivisionData();
        train();

        return test();
    }

    // calculates prediction quality for the forest
    // returns the percentage of predicted compliance with the exact result
    public double howGoodIsOurForest(ArrayList<Integer> forestResults) {

        ArrayList<Integer> genuineResults =  testData.getValuesToPredict();

        double sum = 0;
        for (int i = 0 ; i < genuineResults.size(); i++){
            if (forestResults.get(i).equals(genuineResults.get(i))){
                sum ++;
            }
        }

        return Math.round(sum/genuineResults.size()*10000)/100D;
    }

}

