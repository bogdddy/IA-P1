import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchUtils {

    private static final int[][] DIRS = {
        {-1, 0}, // UP
        { 1, 0}, // DWON
        { 0,-1}, // LEFT
        { 0, 1}  // RIGHT
    };

    public static boolean isInside(char[][] map, int x, int y) {
        return x >= 0 && x < map.length && y >= 0 && y < map[0].length;
    }

    public static boolean isRoad(char c) {
        return c == 'A' || c == 'N' || c == 'C';
    }

    public static double baseCost(char type) {
        switch (type) {
            case 'A':
                return 0.5;
            case 'N':
                return 1.0;
            case 'C':
                return 2.0;
            default:
                throw new IllegalArgumentException("Tipo de carretera no valido: " + type);
        }
    }

    public static double moveCost(char currentType, char nextType) {
        double cost = baseCost(nextType);

        if (currentType != nextType) {
            cost += 3.0;
        }

        return cost;
    }

    public static List<Node> getNeighbors(char[][] map, Node current) {
        List<Node> neighbors = new ArrayList<>();

        for (int[] dir : DIRS) {
            int nx = current.x + dir[0];
            int ny = current.y + dir[1];

            if (!isInside(map, nx, ny)) {
                continue;
            }

            char nextType = map[nx][ny];
            if (!isRoad(nextType)) {
                continue;
            }

            neighbors.add(new Node(nx, ny, nextType));
        }

        return neighbors;
    }

    public static List<Node> buildPath(Node goalNode) {
        List<Node> path = new ArrayList<>();
        Node current = goalNode;

        while (current != null) {
            path.add(current);
            current = current.parent;
        }

        Collections.reverse(path);
        return path;
    }

    public static double[][] createMatrix(int rows, int cols, double initialValue) {
        double[][] matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = initialValue;
            }
        }
        return matrix;
    }
}