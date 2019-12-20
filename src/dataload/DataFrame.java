package dataload;

import java.util.ArrayList;
import java.util.HashMap;

public class DataFrame {

    private HashMap<String,ArrayList> dataFrame;
    private ArrayList<String> colnames ;

    public DataFrame(HashMap dataFrame , ArrayList<String> colnames) {
        this.dataFrame = dataFrame;
        this.colnames = colnames;
    }

    public ArrayList<String> getColnames() {
        return colnames;
    }

    public void setColnames(ArrayList<String> colnames) {
        this.colnames = colnames;
    }

    public HashMap<String, ArrayList> getDataFrame() {
        return dataFrame;
    }

    public ArrayList<Double> getColumn(String name) {
        return dataFrame.get(name);
    }

    public void convertToNumeric(){
        // converts columns to numeric

        for (String colname:colnames){
            ArrayList colvals = dataFrame.get(colname);

            // If first value of array is not instance of number, than change each string/boolean to number
            if ( !(colvals.get(0) instanceof Number) ){

                    ArrayList<String> strings = new ArrayList<>();

                    // first if there is no value like that in our strings add one, if there is do nothing
                    for (int i = 0; i < colvals.size() ; i++){
                        if ( ! strings.contains(colvals.get(i))) {
                            strings.add(String.valueOf(colvals.get(i)));
                        }
                    }
                    // next for every identical string set the same integer
                    for (int i = 0; i < colvals.size() ; i++){
                        int index =  strings.indexOf(colvals.get(i));
                        colvals.set(i,index);
                    }
                    // set values of df to numeric ones
                    dataFrame.replace(colname,colvals);

                    // reset strings after iteration
                    strings = new ArrayList<>();
            }
        }

    }

}
