package dataload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

    // converts CSV to DataFrame class
    public DataFrame convertToDataFrame() throws Exception {

        // if file does not exist
        if (!Files.exists(Paths.get(pathToCSV))) throw new RuntimeException("Place " + pathToCSV + " file in project root directory");

        BufferedReader br ;
        br = new BufferedReader(new FileReader(pathToCSV));

        ArrayList<String> ColumnsArray = getColnames();
        HashMap<String, ArrayList> hashMap = new HashMap<>();

        // colnames to DataFrame
        for (String columnName : ColumnsArray) {

            // For each column name
            ArrayList values = new ArrayList<>();
            hashMap.put(columnName, values);
        }

        String colnames = br.readLine();
        String line = br.readLine();

        while (line != null | line == ""){
            String[] values = line.split(separator);

            for (int i =0;i < ColumnsArray.size(); i++){
                // for each line put all the values in hashmap
                hashMap.get(ColumnsArray.get(i)).add(values[i]);
            }
            line = br.readLine();
        }

        // check whether columns have one type variables only
        for (String s : ColumnsArray) {
            ArrayList column = hashMap.get(s);
            boolean IsNumber = true;
            try {
                double d = Double.parseDouble((String) column.get(0));
            } catch (NumberFormatException nfe) {
                IsNumber = false;
            }
            if (IsNumber) {
                for (Object v : column) {
                    IsNumber = true;
                    try {
                        double d = Double.parseDouble((String) v);
                    } catch (NumberFormatException nfe) {
                        IsNumber = false;
                    }

                    if (!(IsNumber)) {
                        throw new CustomException("Strings mixed with numbers in column " + s + "!");
                    }
                }
            } else {
                for (Object v : column) {
                    IsNumber = true;
                    try {
                        double d = Double.parseDouble((String) v);
                    } catch (NumberFormatException nfe) {
                        IsNumber = false;
                    }

                    if (IsNumber) {
                        throw new CustomException("Strings mixed with numbers in column " + s + "!");
                    }
                }
            }
        }



        return new DataFrame(hashMap,ColumnsArray);
    }

    // returns columns from the file to the data frame
    public ArrayList<String> getColnames() throws Exception {

        if (!Files.exists(Paths.get(pathToCSV))) throw new RuntimeException("Place " + pathToCSV + " file in project root directory");

        // if file is empty raise exception
        File file1 = new File(pathToCSV);
        if (file1.length() == 0){
            throw new Exception("File " + file1.getName() +" is empty");
        }

        // if file is not csv
        File file2 = new File(pathToCSV);
        if (!(file2.getName().endsWith(".csv"))){
            throw new Exception("File " + file2.getName() +" is not csv");
        }


        BufferedReader br;
        br = new BufferedReader(new FileReader(pathToCSV));

        // first line as header
        String columns = br.readLine();

        // splitting by declared separator
        String[] Columns = columns.split(separator);

        return new ArrayList<>(Arrays.asList(Columns));
    }
}
