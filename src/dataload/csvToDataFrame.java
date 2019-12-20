package dataload;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class csvToDataFrame {

    private String pathToCSV;
    private String separator;

    public csvToDataFrame(String pathToCSV, String separator) {
        this.pathToCSV = pathToCSV;
        this.separator = separator;
    }

    public DataFrame convertToDataFrame() throws IOException, CustomException {
        // Converts CSV to DataFrame class

        if (!Files.exists(Paths.get(pathToCSV))) throw new RuntimeException("Place " + pathToCSV + " file in project root directory");

        BufferedReader br ;
        br = new BufferedReader(new FileReader(pathToCSV));

        ArrayList<String> ColumnsArray = getColnames();
        HashMap<String, ArrayList> hashMap = new HashMap<>();

        // Colnames to DataFrame
        for (int i = 0; i < ColumnsArray.size(); i ++){

            // For each column name
            String columnName =  ColumnsArray.get(i);
            ArrayList values = new ArrayList<>();
            hashMap.put(columnName,values);
        }

        String colnames = br.readLine();
        String line = br.readLine();

        while (line != null){
            String[] values = line.split(separator);

            for (int i =0;i < ColumnsArray.size(); i++){
                // for each line put all the values in hashmap

                hashMap.get(ColumnsArray.get(i)).add(values[i]);
            }
            line = br.readLine();
        }

        // check whether columuns have one type variables only
        for (int i=0; i<ColumnsArray.size(); i++){
            ArrayList column = hashMap.get(ColumnsArray.get(i));
            boolean IsNumber = true;
            try {
                double d = Double.parseDouble((String) column.get(0));
            } catch (NumberFormatException nfe) {
                IsNumber = false;
            }
            if (IsNumber){
                for (Object v:column){
                    IsNumber = true;
                    try {
                        double d = Double.parseDouble((String) v);
                    } catch (NumberFormatException nfe) {
                        IsNumber = false;
                    }

                    if (! (IsNumber)){
                        throw new CustomException("Strings mixed with numbers in column " + ColumnsArray.get(i) + "!");
                    }
                }
            }
            else{
                for (Object v:column){
                    IsNumber = true;
                    try {
                        double d = Double.parseDouble((String) v);
                    } catch (NumberFormatException nfe) {
                        IsNumber = false;
                    }

                    if (IsNumber){
                        throw new CustomException("Strings mixed with numbers in column "  + ColumnsArray.get(i) + "!");
                    }
                }
            }
        }



        return new DataFrame(hashMap,ColumnsArray);
    }

    public ArrayList<String> getColnames() throws IOException {

        if (!Files.exists(Paths.get(pathToCSV))) throw new RuntimeException("Place " + pathToCSV + " file in project root directory");


        BufferedReader br;
        br = new BufferedReader(new FileReader(pathToCSV));

        // first line as header
        String columns = br.readLine();

        // splitting by declared separator
        String[] Columns = columns.split(separator);
        ArrayList<String> ColumnsArray = new ArrayList<>(Arrays.asList(Columns));

        return ColumnsArray;
    }

    private boolean isNumber(String myString){
        try {
            double d = Double.parseDouble(myString);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
