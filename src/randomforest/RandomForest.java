package RandomForest;

import dataload.DataFrame;
import decisiontree.DecisionTree;

import java.util.ArrayList;

public class RandomForest {
    int height;
    DataFrame dataFrame;
    double rowPrecentage;
    double columnPercentage;

    public RandomForest(int height, DataFrame dataFrame, double rowPrecentage, double columnPercentage) {
        this.height = height;
        this.dataFrame = dataFrame;
        this.rowPrecentage = rowPrecentage;
        this.columnPercentage = columnPercentage;
    }
}
