public class Heuristics {

    // Computes the Manhattan distance between two positions in the grid.
    public static double manhattan(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    // Estimates the remaining cost as the Manhattan distance multiplied
    // by the minimum possible movement cost (0.5)
    // It assumes that the remaining path uses the cheapest possible road 
    public static double h1(Node n, int goalX, int goalY) {
        return 0.5 * manhattan(n.x, n.y, goalX, goalY);
    }

    // Estimates the remaining cost by multiplying the Manhattan distance
    // by the base cost of the road type of the current node.
    public static double h2(Node n, int goalX, int goalY) {
        return SearchUtils.baseCost(n.type) * manhattan(n.x, n.y, goalX, goalY);
    }

    // Starts with the same estimation as h1 but adds a penalty if the road type of the 
    // current node is different from the road type of the goal node.
    public static double h3(Node n, int goalX, int goalY, char goalType) {
        double value = 0.5 * manhattan(n.x, n.y, goalX, goalY);

        if (n.type != goalType) {
            value += 3.0;
        }

        return value;
    }

    public static double getHeuristicValue(int heuristicId, Node n, int goalX, int goalY, char goalType) {
        switch (heuristicId) {
            case 1:
                return h1(n, goalX, goalY);
            case 2:
                return h2(n, goalX, goalY);
            case 3:
                return h3(n, goalX, goalY, goalType);
            default:
                throw new IllegalArgumentException("Heuristica no valida: " + heuristicId);
        }
    }
}