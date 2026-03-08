import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class BestFirst {

    public static SearchResult search(char[][] map, int startX, int startY, int goalX, int goalY, int heuristicId) {
        if (!SearchUtils.isRoad(map[startX][startY])) {
            throw new IllegalArgumentException("La casilla inicial no es transitable.");
        }
        if (!SearchUtils.isRoad(map[goalX][goalY])) {
            throw new IllegalArgumentException("La casilla final no es transitable.");
        }

        char goalType = map[goalX][goalY];

        PriorityQueue<Node> open = new PriorityQueue<>(
            Comparator.comparingDouble((Node n) -> n.h)
                      .thenComparingDouble(n -> n.g)
        );

        double[][] bestG = SearchUtils.createMatrix(map.length, map[0].length, Double.POSITIVE_INFINITY);
        boolean[][] closed = new boolean[map.length][map[0].length];

        Node start = new Node(startX, startY, map[startX][startY]);
        start.g = 0.0;
        start.h = Heuristics.getHeuristicValue(heuristicId, start, goalX, goalY, goalType);
        start.f = start.h;

        bestG[startX][startY] = 0.0;
        open.add(start);

        int statesExpanded = 0;

        // Best First
        while (!open.isEmpty()) {
            Node current = open.poll();

            if (closed[current.x][current.y]) {
                continue;
            }

            closed[current.x][current.y] = true;
            statesExpanded++;

            if (current.x == goalX && current.y == goalY) {
                List<Node> path = SearchUtils.buildPath(current);
                return new SearchResult(path, current.g, statesExpanded);
            }

            List<Node> neighbors = SearchUtils.getNeighbors(map, current);

            for (Node neighbor : neighbors) {
                if (closed[neighbor.x][neighbor.y]) {
                    continue;
                }

                double newG = current.g + SearchUtils.moveCost(current.type, neighbor.type);

                if (newG < bestG[neighbor.x][neighbor.y]) {
                    bestG[neighbor.x][neighbor.y] = newG;
                    neighbor.g = newG;
                    neighbor.h = Heuristics.getHeuristicValue(heuristicId, neighbor, goalX, goalY, goalType);
                    neighbor.f = neighbor.h;
                    neighbor.parent = current;

                    open.add(neighbor);
                }
            }
        }

        return null;
    }
}