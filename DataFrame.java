import java.util.ArrayList;
import java.util.HashMap;

public class DataFrame {

    private HashMap<String,ArrayList<Double>> dataFrame;
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

    public HashMap<String, ArrayList<Double>> getDataFrame() {
        return dataFrame;
    }

    public ArrayList<Double> getColumn(String name) {
        return dataFrame.get(name);
    }

}
