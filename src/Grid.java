import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Grid {

    private Node[][] grid;
    private int numRows;
    private int numCols;

    public void setSize(int rows, int cols) {
        numRows = rows;
        numCols = cols;
        grid = new Node[rows][cols];
    }

    public Node getNode(int row, int col) {
        return grid[row][col];
    }

    public void setNode(Node node, int row, int col) {
        grid[row][col] = node;
    }

    public Node getStartNode() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col].getType()==Node.Type.START) {
                    return grid[row][col];
                }
            }
        }
        return null;
    }

    public Node getEndNode() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col].getType()==Node.Type.END) {
                    return grid[row][col];
                }
            }
        }
        return null;
    }

    public static Grid getGridFromFile(String path) throws IOException {
        Grid grid = new Grid();
        List<String> lines = Files.readAllLines(Paths.get(path));
        for (int i = 0; i < lines.size(); i++) {
            int size;
            if (i == 0) {
                size = Integer.parseInt(lines.get(0));
                grid.setSize(size, size);
            } else {
                String[] parts = lines.get(i).split("\\s+");
                for (int j = 0; j < parts.length; j++) {
                    String nodeType = parts[j];
                    Node node = new Node(i-1, j);
                    node.setTypeFromChar(nodeType);
                    grid.setNode(node, i-1, j);
                }
            }
        }
        return grid;
    }

    public List<Node> getNeighborsForNode(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int row = node.getRow();
        int col = node.getCol();
        if (row > 0) {
            neighbors.add(grid[row-1][col]);
        }
        if (row > 0 && col < numCols-1) {
            neighbors.add(grid[row-1][col+1]);
        }
        if (col < numCols-1) {
            neighbors.add(grid[row][col+1]);
        }
        if (row < numRows-1 && col < numCols-1) {
            neighbors.add(grid[row+1][col+1]);
        }
        if (row < numRows-1) {
            neighbors.add(grid[row+1][col]);
        }
        if (row < numRows-1 && col > 0) {
            neighbors.add(grid[row+1][col-1]);
        }
        if (col > 0) {
            neighbors.add(grid[row][col-1]);
        }
        if (row > 0 && col > 0) {
            neighbors.add(grid[row-1][col-1]);
        }
        return neighbors;
    }

    public void setNodesToPaths(Collection<Node> nodes) {
        nodes.forEach(Node::setPath);
    }

    public void printGrid() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                System.out.print(grid[row][col] + " ");
            }
            System.out.println();
        }
    }

    public static Grid createTestGrid(int size) {
        Grid grid = new Grid();
        grid.setSize(size, size);
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Node node = new Node(row, col);
                if (row == 0 && col == 0) {
                    node.setType(Node.Type.START);
                } else if (row == size/2 && col < size-1) {
                    node.setType(Node.Type.BLOCK);
                } else if (row == size-1 && col == 0) {
                    node.setType(Node.Type.END);
                }
                grid.setNode(node, row, col);
            }
        }
        return grid;
    }
}
