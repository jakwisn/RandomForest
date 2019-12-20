package dataload;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, CustomException {
       csvToDataFrame csv2df = new csvToDataFrame("test.csv",",");
       DataFrame test = csv2df.convertToDataFrame();
       System.out.println(test.getColumn("Pclass"));
       test.convertToNumeric();
       System.out.println(test.getColumn("Pclass"));

    }

}
