package gini;

import dataload.DataFrame;
import java.util.ArrayList;
import java.util.HashMap;
import static java.lang.Math.*;

public class Gini {

    private DataFrame dataFrame;

    public Gini(DataFrame dataFrame) {
        this.dataFrame = dataFrame;
    }

    public double calculateGiniIndex(ArrayList<Integer> rowIndexes){
        double giniIndex = 0;
        ArrayList<Integer> giniValues = new ArrayList<>();

        for (int rowIndex:rowIndexes) {
            giniValues.add(dataFrame.getValuesToPredict().get(rowIndex));
        }

        ArrayList<Integer> unique = new ArrayList<>();
        HashMap<Integer, Integer> count = new HashMap<>();

        // if there is no iteger like this add
        // count all integers
        for (int integer:giniValues) {
            if (!unique.contains(integer)) {
                unique.add(integer);
                count.put(integer,1);
            }
            else{
                count.replace(integer,count.get(integer)+1);
            }
        }

        double sum = 0;
        double numberOfOccurances= 0;
        for (int uniqueValue:unique){
            numberOfOccurances =  count.get(uniqueValue);
            sum += pow(numberOfOccurances/giniValues.size(),2) ;

        }

        return 1-sum;
    }


}
