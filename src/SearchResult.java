import java.util.List;

public class SearchResult {
    List<Node> path;
    double totalCost;
    int statesExpanded;

    public SearchResult(List<Node> path, double totalCost, int statesExpanded) {
        this.path = path;
        this.totalCost = totalCost;
        this.statesExpanded = statesExpanded;
    }
}