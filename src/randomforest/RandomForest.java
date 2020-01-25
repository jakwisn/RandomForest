package randomforest;

import dataload.DataFrame;
import decisiontree.DecisionTree;
import decisiontree.Node;

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

    private ArrayList<DataFrame> DivisionData() throws Exception {

        ArrayList<String> colnames = dataFrame.getColnames();
        int AllRowIndexes = dataFrame.getColumn(colnames.get(0)).size();

        int MaxRowForTrainingData = (int) Math.floor(DivisionPercentage/100 * AllRowIndexes);

        HashMap<String, ArrayList> data = dataFrame.getDataFrame();
        HashMap<String, ArrayList> trainingData = new HashMap<>();
        HashMap<String, ArrayList> testingData = new HashMap<>();

        if(DivisionPercentage >= 50.0){
            for(String column : colnames){
                ArrayList<Integer> trainingRows = data.get(column);
                ArrayList<Integer> testingRows = new ArrayList<>();
                for(int i = MaxRowForTrainingData+1; i < AllRowIndexes; i++){
                    testingRows.add(trainingRows.remove(MaxRowForTrainingData+1));
                }
                trainingData.put(column, trainingRows);
                testingData.put(column, testingRows);
        }
        }else{
            for(String column : colnames){
                ArrayList<Integer> testingRows = data.get(column);
                ArrayList<Integer> trainingRows = new ArrayList<>();
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

    public void train() throws Exception {

        if (dataFrame.getColnameToPredict() == null ) {
            throw new Exception("Colname to predict has not been set!");
        }

        // Our target forest:
        ArrayList<DecisionTree> forest = new ArrayList<>();

        // empty arrays
        ArrayList<Integer> indexes = new ArrayList<>();
        ArrayList<String> colnames = new ArrayList<>();

        // getting size of rows and columns
        int rowSize = dataFrame.getValuesToPredict().size();
        int colSize = dataFrame.getColnames().size();

        // array of columns without the one to predict
        ArrayList<String> colnamesToPickFrom = new ArrayList<>();

        for (String col:dataFrame.getColnames()) {
            if (col == dataFrame.getColnameToPredict()){continue;}
            colnamesToPickFrom.add(col);
        }
        Random random = new Random();

        // growing trees
        int index = 0 ;
        String colname;
        for (int i = 0; i < number_of_trees;  i++) {
            // preparing data for decisionTree
            for (i = 0 ; i < rowPercentage*rowSize/100; i++) {
               index = random.nextInt(dataFrame.getValuesToPredict().size());
               indexes.add(index);
            }


            for (i = 0 ; i < columnPercentage*colSize/100; i++) {
                colname = colnamesToPickFrom.get(random.nextInt(colnamesToPickFrom.size()));
                colnames.add(colname);
            }
            // with random colnames and rows lets grow tree
            DecisionTree decisionTree = new DecisionTree(dataFrame,indexes,colnames,height);
            decisionTree.CultureTree();
            forest.add(decisionTree);
        }

        this.trees = forest;

    }

    public int test1(int indexToPredict) throws Exception {
        ArrayList<Integer> doms = new ArrayList<>();
        for (int i=0; i<trees.size();i++){
            doms.add(trees.get(i).search(indexToPredict));
        }
        return(trees.get(0).dominant(doms));
    }

}

