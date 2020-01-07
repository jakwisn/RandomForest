package decisiontree;

import java.util.ArrayList;

public class Node {

    ArrayList<Integer> Indexes;

    private Node() {
    }

    public class Decision extends Node {

        Node Left = null;
        Node Right = null;
        ArrayList<String> columns;
        String colname;
        double val;

        public Decision(ArrayList<Integer> Indexes, ArrayList<String> columns, String colname, double val) {
            this.Indexes = Indexes;
            this.colname = colname;
            this.columns = columns;
            this.val = val;
            this.Left = Left;
            this.Right = Right;
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

        public double getVal() {
            return val;
        }
    }

    public class Leaf extends Node {

        int answer;

        public Leaf(ArrayList<Integer> Indexes, int answer) {
            this.Indexes = Indexes;
            this.answer = answer;
        }

        public int getAnswer() {
            return answer;
        }
    }

}
