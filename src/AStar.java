import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {

    public static SearchResult search(char[][] map, int startX, int startY, int goalX, int goalY, int heuristicId) {
        if (!SearchUtils.isRoad(map[startX][startY])) {
            throw new IllegalArgumentException("start not valid");
        }
        if (!SearchUtils.isRoad(map[goalX][goalY])) {
            throw new IllegalArgumentException("end not valid");
        }

        char goalType = map[goalX][goalY];

        PriorityQueue<Node> open = new PriorityQueue<>(
            Comparator.comparingDouble((Node n) -> n.f)
                      .thenComparingDouble(n -> n.h)
        );

        double[][] bestG = SearchUtils.createMatrix(map.length, map[0].length, Double.POSITIVE_INFINITY);

        Node start = new Node(startX, startY, map[startX][startY]);
        start.g = 0.0;
        start.h = Heuristics.getHeuristicValue(heuristicId, start, goalX, goalY, goalType);
        start.f = start.g + start.h;

        bestG[startX][startY] = 0.0;
        open.add(start);

        int statesExpanded = 0;

        // A*
        while (!open.isEmpty()) {
            Node current = open.poll();

            if (current.g > bestG[current.x][current.y]) {
                continue;
            }

            statesExpanded++;

            if (current.x == goalX && current.y == goalY) {
                List<Node> path = SearchUtils.buildPath(current);
                return new SearchResult(path, current.g, statesExpanded);
            }

            List<Node> neighbors = SearchUtils.getNeighbors(map, current);

            for (Node neighbor : neighbors) {
                double newG = current.g + SearchUtils.moveCost(current.type, neighbor.type);

                if (newG < bestG[neighbor.x][neighbor.y]) {
                    bestG[neighbor.x][neighbor.y] = newG;
                    neighbor.g = newG;
                    neighbor.h = Heuristics.getHeuristicValue(heuristicId, neighbor, goalX, goalY, goalType);
                    neighbor.f = neighbor.g + neighbor.h;
                    neighbor.parent = current;

                    open.add(neighbor);
                }
            }
        }

        return null;
    }
}