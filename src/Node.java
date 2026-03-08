import java.util.Objects;

public class Node {
    int x;
    int y;
    char type;

    double g;
    double h;
    double f;

    Node parent;

    public Node(int x, int y, char type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.g = Double.POSITIVE_INFINITY;
        this.h = 0.0;
        this.f = Double.POSITIVE_INFINITY;
        this.parent = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Node)) return false;
        Node other = (Node) obj;
        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}