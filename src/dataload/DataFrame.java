package dataload;

import java.util.ArrayList;
import java.util.HashMap;

public class DataFrame {

    private HashMap<String,ArrayList> dataFrame;
    private ArrayList<String> colnames ;
    private String colnameToPredict;
    private ArrayList<Integer> valuesToPredict;

    public DataFrame(HashMap<String, ArrayList> dataFrame , ArrayList<String> colnames) {
        this.dataFrame = dataFrame;
        this.colnames = colnames;
        this.colnameToPredict = null;
        this.valuesToPredict = null;
    }

    public ArrayList<String> getColnames() {
        return colnames;
    }

    public String getColnameToPredict(){ return this.colnameToPredict; }

    public void setColnames(ArrayList<String> colnamesToChange) throws Exception {

        if (colnames.size() != colnamesToChange.size()){
            throw new Exception("Wrong size list: " + colnames.size() + " and " + colnamesToChange.size() + " are not equal");
        }

        for (int i=0 ; i<colnamesToChange.size(); i++){
            setColname(colnames.get(i), colnamesToChange.get(i));
        }
    }

    private void setColname(String oldName, String newName) throws Exception {

        if (!colnames.contains(oldName)){
            throw new Exception("In DataFrame there is no column named: " + oldName);
        }
        if (colnames.contains(newName)){
            throw new Exception("There is already a column in the DataFrame named: " + newName);
        }
        colnames.set(colnames.indexOf(oldName),newName);
        ArrayList tmp = dataFrame.get(oldName);
        dataFrame.remove(oldName);
        dataFrame.put(newName, tmp);
    }

    public HashMap<String, ArrayList> getDataFrame() {
        return dataFrame;
    }

    public ArrayList<Double> getColumn(String name) throws Exception {

        if (!colnames.contains(name)){
            throw new Exception("In DataFrame there is no column named: " + name);
        }
        return dataFrame.get(name);
    }

    public void setToPredict(String colname) throws Exception {

            if (!colnames.contains(colname)){
                throw new Exception("In DataFrame there is no column named: " + colname);
            }

            // set as predicted one
            this.colnameToPredict = colname;

            // set predicted array to integers
            ArrayList<Double> doubles = dataFrame.get(colname);
            ArrayList<Integer> integers = new ArrayList<>();

            for (Double aDouble : doubles) {
                integers.add((int) Math.round(aDouble));
            }

            this.valuesToPredict = integers;
        }

    public ArrayList<Integer> getValuesToPredict(){
        return valuesToPredict;
    }

    // converts columns to numeric
    public void convertToNumeric(){

        for (String colname : colnames){
            ArrayList<String> colvals = dataFrame.get(colname);

            // If first value of array is not instance of number, than change each string/boolean to number
            try {
                //  Block of code to try
                // New array of doubles
                ArrayList<Double> doubles = new ArrayList<>();

                for (String colval : colvals) {
                    double tmp = Double.parseDouble(colval);
                    doubles.add(tmp);
                }
                dataFrame.replace(colname,doubles);
            }
            catch(Exception e) {
                //  Block of code to handle errors

                    ArrayList<String> strings = new ArrayList<>();
                    ArrayList<Integer> integers  = new ArrayList<>();
                    // first if there is no value like that in our strings add one, if there is do nothing
                for (String colval : colvals) {
                    if (!strings.contains(colval)) {
                        strings.add(String.valueOf(colval));
                    }
                }
                    // next for every identical string set the same integer

                for (String colval : colvals) {
                    int index = strings.indexOf(colval);
                    integers.add(index);
                }
                    // set values of df to numeric ones
                    dataFrame.replace(colname,integers);
            }
        }

    }

}
