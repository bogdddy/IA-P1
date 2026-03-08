import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        try {
            System.out.println("P1 - CERCA INFORMADA");

            //System.out.print("input file:");
            //String filePath = scan.nextLine().trim();

            String filePath = "src/input.txt";

            char[][] map = MapLoader.loadMap(filePath);

            System.out.println("\nmap loaded (" + map.length + "x" + map[0].length + ")");
            printMap(map);

            // INPUTS
            
            int startX = readInt(scan, "\nx0: ", 0, map.length - 1);
            int startY = readInt(scan, "y0: ", 0, map[0].length - 1);
            int goalX = readInt(scan, "xF: ", 0, map.length - 1);
            int goalY = readInt(scan, "yF: ", 0, map[0].length - 1);

            validateRoadCell(map, startX, startY, "inicial");
            validateRoadCell(map, goalX, goalY, "final");

            int algorithm = readInt(scan, "\nAlgoritmo (1 = Best-first, 2 = A*): ", 1, 2);
            int heuristic = readInt(scan, "Heuristica (1 = h1, 2 = h2, 3 = h3): ", 1, 3);

            // RESULTS

            SearchResult result;

            if (algorithm == 1) {
                result = BestFirst.search(map, startX, startY, goalX, goalY, heuristic);
            } else {
                result = AStar.search(map, startX, startY, goalX, goalY, heuristic);
            }

            if (result == null || result.path == null || result.path.isEmpty()) {
                System.out.println("\nsolution not found");
                return;
            }

            System.out.println("\nRESULT");
            System.out.println("ALGORITHM: " + (algorithm == 1 ? "Best-first" : "A*"));
            System.out.println("Heuristic: h" + heuristic);
            System.out.printf("Cost: %.3f%n", result.totalCost);
            System.out.println("states: " + result.statesExpanded);
            System.out.println("length: " + result.path.size());

            System.out.println("\nfound path:");
            printPath(result.path);

            System.out.println("\nmap + path:");
            printMapWithPath(map, result.path, startX, startY, goalX, goalY);

        } catch (Exception e) {
            System.out.println("\nerr: " + e.getMessage());
        } finally {
            scan.close();
        }
    }

    private static int readInt(Scanner scan, String message, int min, int max) {
        while (true) {
            System.out.print(message);

            if (scan.hasNextInt()) {
                int value = scan.nextInt();
                if (value >= min && value <= max) {
                    return value;
                }
            } else {
                scan.next();
            }

            System.out.println("not valid value");
        }
    }

    private static void validateRoadCell(char[][] map, int x, int y, String label) {
        if (!SearchUtils.isRoad(map[x][y])) {
            throw new IllegalArgumentException(
                label + " (" + x + "," + y + ") not valid."
            );
        }
    }

    private static void printMap(char[][] map) {
        System.out.println();
        for (char[] row : map) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    private static void printPath(List<Node> path) {
        for (int i = 0; i < path.size(); i++) {
            Node n = path.get(i);
            System.out.print("(" + n.x + "," + n.y + "," + n.type + ")");
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    private static void printMapWithPath(char[][] map, List<Node> path, int startX, int startY, int goalX, int goalY) {
        char[][] copy = new char[map.length][map[0].length];

        for (int i = 0; i < map.length; i++) {
            System.arraycopy(map[i], 0, copy[i], 0, map[i].length);
        }

        for (Node n : path) {
            if (!(n.x == startX && n.y == startY) && !(n.x == goalX && n.y == goalY)) {
                copy[n.x][n.y] = '*';
            }
        }

        copy[startX][startY] = 'S';
        copy[goalX][goalY] = 'G';

        for (char[] row : copy) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}