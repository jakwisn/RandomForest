package dataload;

import java.util.ArrayList;
import java.util.HashMap;

public class DataFrame {

    private HashMap<String,ArrayList> dataFrame;
    private ArrayList<String> colnames ;
    private String colnameToPredict = null;
    private ArrayList<Integer> valuesToPredict;

    public DataFrame(HashMap dataFrame , ArrayList<String> colnames) {
        this.dataFrame = dataFrame;
        this.colnames = colnames;
        this.colnameToPredict = colnameToPredict;
        this.valuesToPredict = valuesToPredict;
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

    public void setToPredict(String colname) throws Exception {
            if (!colnames.contains(colname)){
                throw new Exception("In DataFrame there is no column named " + colname);
            }

            // set as predicted one
            this.colnameToPredict = colname;

            // set prediced array to integers

            ArrayList<Integer> integers = dataFrame.get(colname);

            this.valuesToPredict = integers;
    }

    public ArrayList<Integer> getValuesToPredict(){
        return valuesToPredict;
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
