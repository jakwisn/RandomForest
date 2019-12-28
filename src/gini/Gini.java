package gini;

import dataload.DataFrame;

import java.util.ArrayList;

public class Gini {

    private DataFrame dataFrame;
    private String nameOfColToPredict ;
    public Gini(DataFrame dataFrame, String nameOfColToPredict) {
        this.dataFrame = dataFrame;
        this.nameOfColToPredict = nameOfColToPredict;
    }

    public ArrayList<Double> calculateGiniIndex(){

    }

}
