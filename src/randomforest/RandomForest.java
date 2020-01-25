package randomforest;

import dataload.DataFrame;
import decisiontree.DecisionTree;

import java.util.ArrayList;

public class RandomForest {
    int height;
    DataFrame dataFrame;
    double rowPercentage;
    double columnPercentage;
    double DivisionPercentage;

    public RandomForest(int height, DataFrame dataFrame, double rowPercentage, double columnPercentage, double DivisionPercentage) {
        this.height = height;
        this.dataFrame = dataFrame;
        this.rowPercentage = rowPercentage;
        this.columnPercentage = columnPercentage;
        this.DivisionPercentage = DivisionPercentage;
    }

    //chcemy gdzies sprawdzac co przewiduje - czy ma ustawione - moze w train()

    private ArrayList<DataFrame> DivisionData(){

        //korzystamy z DiviosionPercentage i dzielimy DataFrame

    }

    public ArrayList<DecisionTree> train(){

        //chcemy wybierac kolumny oprocz tej do przewidywania - losowo

    }

    public double test(){

    }

}
