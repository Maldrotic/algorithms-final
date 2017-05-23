import java.io.IOException;
import java.util.*;

public class Pathfinder {

    private static Grid grid;

    private static Node start = null;
    private static Node end = null;

    public static void main(String[] args) {
        String pathToFile = args[0];
        try {
            grid = Grid.getGridFromFile(pathToFile);
//            grid = Grid.createTestGrid(10);
            start = grid.getStartNode();
            end = grid.getEndNode();
            long startT = System.currentTimeMillis();
            boolean foundPath = runSearch(start, end);
            long stopT = System.currentTimeMillis();
            if (foundPath) {
                System.out.println("Found a path in "+((stopT-startT)/1000.0)+" second(s)");
                setPath();
                grid.printGrid();
            } else {
                System.out.println("No valid path was found");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean runSearch(Node start, Node end) {
        Queue<Node> open = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node a, Node b) {
                return Float.compare(a.getF(), b.getF());
            }
        });
        open.add(start);
        start.setG(0);
        start.setF(Node.getDistance(start, end));

        while (!open.isEmpty()) {
            Node current = open.poll();

            if (current.getType() == Node.Type.END) {
                return true;
            }
            current.close();

            List<Node> neighbors = grid.getNeighborsForNode(current);
            for (Node neighbor : neighbors) {
                if (neighbor.isClosed() || neighbor.getType()==Node.Type.BLOCK) {
                    continue;
                }
                float tentativeG = current.getG() + Node.getDistance(current, neighbor);
                if (!open.contains(neighbor)) {
                    open.add(neighbor);
                } else if (tentativeG >= neighbor.getG()) {
                    continue;
                }

                neighbor.setParent(current);
                neighbor.setG(tentativeG);
                neighbor.setF(tentativeG+Node.getDistance(neighbor, end));
            }
        }
        return false;
    }

    private static void setPath() {
        List<Node> finalPath = new ArrayList<>();
        Node current = end;
        while (current.getParent() != null) {
            finalPath.add(current);
            current = current.getParent();
        }
        finalPath.add(current);

        Collections.reverse(finalPath);
        finalPath.forEach(Node::setPath);
    }
}
