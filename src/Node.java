public class Node{
    private int row;
    private int col;

    private float f;
    private float g;

    private Type type;

    public static final String EMPTY_CHAR = ".";
    public static final String BLOCK_CHAR = "X";
    public static final String START_CHAR = "S";
    public static final String END_CHAR = "E";
    public static final String PATH_CHAR = "#";

    private boolean isClosed;
    private Node parent;
    enum Type {
        EMPTY,
        BLOCK,
        START,
        PATH,
        END
    }

    public Node(int row, int col) {
        this.row = row;
        this.col = col;
        f = Float.MAX_VALUE;
        g = Float.MAX_VALUE;
        type = Type.EMPTY;
        isClosed = false;
        parent = null;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setTypeFromChar(String typeChar) {
        switch (typeChar.toUpperCase()) {
            case BLOCK_CHAR:
                type = Type.BLOCK;
                break;
            case START_CHAR:
                type = Type.START;
                break;
            case END_CHAR:
                type = Type.END;
                break;
            case EMPTY_CHAR:
            default:
                type = Type.EMPTY;
                break;
        }
    }

    public void setParent(Node node) {
        parent = node;
    }

    public Node getParent() {
        return parent;
    }

    public void close() {
        isClosed = true;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setPath() {
        if (type != Type.START && type != Type.END) {
            type = Type.PATH;
        }
    }

    public static float getDistance(Node a, Node b) {
        if (a.type == Type.BLOCK || b.type == Type.BLOCK) {
            return Float.MAX_VALUE;
        }
        return (float) Math.sqrt((Math.pow(b.row - a.row,2) + Math.pow(b.col - a.col, 2)));
    }

    @Override
    public String toString() {
        switch (type) {
            case START:
                return START_CHAR;
            case END:
                return END_CHAR;
            case PATH:
                return PATH_CHAR;
            case BLOCK:
                return BLOCK_CHAR;
            case EMPTY:
            default:
                return EMPTY_CHAR;
        }
    }
}
