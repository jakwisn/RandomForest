import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    public DataFrame convertToDataFrame() throws IOException {
        // Converts CSV to DataFrame class

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
        return new DataFrame(hashMap,ColumnsArray);
    }

    public ArrayList<String> getColnames() throws IOException {
        BufferedReader br;
        br = new BufferedReader(new FileReader(pathToCSV));

        // first line as header
        String columns = br.readLine();

        // splitting by declared separator
        String[] Columns = columns.split(separator);
        ArrayList<String> ColumnsArray = new ArrayList<String>(Arrays.asList(Columns));

        return ColumnsArray;
    }

}
