package kode;

public class Rope {

    private static final int MAX_STRING = 200;

    private Node root;

    public Rope() {
        root = new Node("");
    }

    public int size(){
        return root.size;
    }

    public void insert(String text, int i){
        root.insert(text, i, null);
    }

    public void insert(String text){
        insert(text, size());
    }

    public void remove(int start, int end){
        if(start < 0 || end > root.size)
            throw new IndexOutOfBoundsException();
        root.remove(start, end, null);
    }

    public String substring(int start, int end){
        if(start < 0 || end > root.size)
            throw new IndexOutOfBoundsException();
        StringBuilder result = new StringBuilder();
        root.substring(start, end, result);
        return result.toString();
    }

    @Override
    public String toString() {
        return substring(0, size());
    }

    public void print(){
        root.print(0);
    }
    private class Node {
        StringBuilder text;
        int size, depth;
        Node left, right;

        Node(String text){
            this.text = new StringBuilder(text);
            tryFork();
            recalculate();
        }

        void recalculate(){
            if(leaf()){
                size = text.length();
                depth = 0;
            } else {
                size = left.size + right.size;
                depth = Math.max(left.depth, right.depth) + 1;
            }
        }

        boolean leaf(){
            return text != null;
        }

        void tryFork(){
            if(text.length() <= MAX_STRING)
                return;

            int mid = text.length() / 2;
            left = new Node(text.substring(0, mid));
            right = new Node(text.substring(mid, text.length()));
            text = null;

            recalculate();
        }

        void tryUnfork(){
            if(leaf() || size > MAX_STRING || !(left.leaf() && right.leaf()))
                return;

            text = new StringBuilder();
            text.append(left.text);
            text.append(right.text);
            left = null;
            right = null;

            recalculate();
        }

        void promote(Node parent, Node child){
            if(root == this)
                root = child;

            if(parent != null){
                if(parent.left == this)
                    parent.left = child;
                else
                    parent.right = child;
            }
        }

        void rotateRight(Node parent){
            promote(parent, left);

            Node oldleft = left;
            left = left.right;
            oldleft.right = this;

            recalculate();
            oldleft.recalculate();
        }

        void rotateLeft(Node parent){
            promote(parent, right);

            Node oldright = right;
            right = right.left;
            oldright.left = this;

            recalculate();
            oldright.recalculate();
        }

        void balance(Node parent){
            if(leaf() || Math.abs(left.depth - right.depth) <= 1)
                return;

            if(left.depth > right.depth)
                rotateRight(parent);
            else
                rotateLeft(parent);
        }

        void insert(String addition, int i, Node parent){
            if(leaf()){
                text.insert(i, addition);
                tryFork();
            } else if(i > left.size){
                right.insert(addition, i - left.size, this);
            } else {
                left.insert(addition, i, this);
            }

            recalculate();

            balance(parent);
        }

        void remove(int start, int end, Node parent){
            start = Util.clamp(0, size, start);
            end = Util.clamp(0, size, end);

            if(end - start == 0)
                return;

            if(leaf()){
                text.delete(start, end);
                recalculate();
            } else {
                right.remove(start - left.size, end - left.size, this);
                left.remove(start, end, this);
                recalculate();
                tryUnfork();
            }

            balance(parent);
        }

        void substring(int start, int end, StringBuilder out){
            start = Util.clamp(0, size, start);
            end = Util.clamp(0, size, end);

            if(end - start == 0)
                return;

            if(leaf()){
                out.append(text.substring(start, end));
            } else {
                left.substring(start, end, out);
                right.substring(start - left.size, end - left.size, out);
            }
        }

        void print(int indent){
            for(int i = 0; i < indent; i++)
                System.out.print(' ');
            System.out.println(size + ", " + depth + ", " + (text != null ? text : ""));

            if(leaf())
                return;

            left.print(indent + 2);
            right.print(indent + 2);
        }
    }
}
