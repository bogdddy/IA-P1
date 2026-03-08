import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapLoader {

    public static char[][] loadMap(String filePath) throws IOException {
        List<char[]> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            
            String line;
            int expectedCols = -1;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                // check row length
                char[] row = parseLine(line);
                if (expectedCols == -1) {
                    expectedCols = row.length;
                } else if (row.length != expectedCols) {
                    throw new IllegalArgumentException("row length missmatch");
                }

                rows.add(row);
            }
        }

        if (rows.isEmpty()) 
            throw new IllegalArgumentException("map empty");

        // save map
        char[][] map = new char[rows.size()][rows.get(0).length];
        for (int i = 0; i < rows.size(); i++) 
            map[i] = rows.get(i);
        
        return map;
    }

    private static char[] parseLine(String line) {
        
        List<Character> values = new ArrayList<>();

        // check cell
        for (int i = 0; i < line.length(); i++) {
            char c = Character.toUpperCase(line.charAt(i));
            if (Character.isWhitespace(c)) continue;
            validateCell(c);
            values.add(c);
        }

        if (values.isEmpty()) {
            throw new IllegalArgumentException("line empty");
        }

        // copy row
        char[] row = new char[values.size()];
        for (int i = 0; i < values.size(); i++) {
            row[i] = values.get(i);
        }

        return row;
    }

    private static void validateCell(char c) {
        if (c != 'A' && c != 'N' && c != 'C' && c != 'X') {
            throw new IllegalArgumentException(
                "char not valid " + c
            );
        }
    }
}