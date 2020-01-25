package randomforest;

import dataload.DataFrame;
import decisiontree.DecisionTree;

import java.util.ArrayList;
import java.util.Random;

public class RandomForest {
    int height;
    DataFrame dataFrame;
    double rowPercentage;
    double columnPercentage;
    double DivisionPercentage;
    int number_of_trees;

    public RandomForest(int height, DataFrame dataFrame, double rowPercentage, double columnPercentage, double DivisionPercentage, int number_of_trees) {
        this.height = height;
        this.dataFrame = dataFrame;
        this.rowPercentage = rowPercentage;
        this.columnPercentage = columnPercentage;
        this.DivisionPercentage = DivisionPercentage;
        this.number_of_trees = number_of_trees;
    }

    //chcemy gdzies sprawdzac co przewiduje - czy ma ustawione - moze w train()

    /*
    private ArrayList<DataFrame> DivisionData(){

        //korzystamy z DiviosionPercentage i dzielimy DataFrame


    }
*/
    public ArrayList<DecisionTree> train() throws Exception {

        if (dataFrame.getColnameToPredict() == null ) {
            throw new Exception("Colname to predict has not been set!");
        }

        //chcemy wybierac kolumny oprocz tej do przewidywania - losowo

        // Our target forest:
        ArrayList<DecisionTree> forest = new ArrayList<>();
        ArrayList<Integer> indexes = new ArrayList<>();
        ArrayList<String> colnames = new ArrayList<>();
        int rowSize = dataFrame.getValuesToPredict().size();
        int colSize = dataFrame.getColnames().size();

        ArrayList<String> colnamesToPickFrom = new ArrayList<>();

        for (String col:dataFrame.getColnames()) {
            if (col == dataFrame.getColnameToPredict()){continue;}
            colnamesToPickFrom.add(col);
        }
        Random random = new Random();

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

            DecisionTree decisionTree = new DecisionTree(dataFrame,indexes,colnames,height);
            decisionTree.CultureTree();
            forest.add(decisionTree);
        }

        return forest;

    }
/*
    public double test(){

    }
*/
}
