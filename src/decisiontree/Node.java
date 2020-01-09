package decisiontree;

import java.util.ArrayList;

public class Node {

    ArrayList<Integer> Indexes;

    public Node() {
    }

    public static class Decision extends Node {

        int depth;
        Node Left = null;
        Node Right = null;
        ArrayList<String> columns;
        String colname;
        double val = 0;
        double gini1;
        double gini2;
        ArrayList<Integer> list1 ;
        ArrayList<Integer> list2;


        public Decision(ArrayList<Integer> Indexes, ArrayList<String> columns, int depth) {
            this.Indexes = Indexes;
            this.colname = colname;
            this.columns = columns;
            this.val = val;
            this.Left = Left;
            this.Right = Right;
            this.depth = depth;
            this.gini1 = gini1;
            this.gini2 = gini2;
            this.list1 = list1;
            this.list2 = list2;
        }

        public void setLeft(Node left) {
            Left = left;
        }

        public void setRight(Node right) {
            Right = right;
        }

        public Node getLeft() {
            return Left;
        }

        public Node getRight() {
            return Right;
        }

        public ArrayList<String> getColumns() {
            return columns;
        }

        public String getColname() {
            return colname;
        }

        public void setColname(String colname) { this.colname = colname; }

        public double getVal() {
            return val;
        }

        public ArrayList<Integer> getIndexes() {
            return Indexes;
        }

        public void setDepth(int depth) { this.depth = depth; }

        public void setVal(double val) {
            this.val = val;
        }
    }

    public static class Leaf extends Node {


        public Leaf(ArrayList<Integer> Indexes) {
            this.Indexes = Indexes;
        }
    }

}
