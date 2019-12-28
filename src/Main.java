package dataload;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws Exception {
       csvToDataFrame csv2df = new csvToDataFrame("test.csv",",");
       DataFrame test = csv2df.convertToDataFrame();
       System.out.println(test.getColumn("Pclass"));
       test.convertToNumeric();
       System.out.println(test.getColumn("Pclass"));

        csvToDataFrame konspekt = new csvToDataFrame("Konspekt.pdf",",");
        DataFrame konspektdf = konspekt.convertToDataFrame();

        System.out.println(konspektdf.getColnames());

    }

}
