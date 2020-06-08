import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class StatInfo {
    private Map<String, Integer> userEdits;
    private Map<String, Integer> uniqueNames;
    private int numberOfNodes;

    public StatInfo() {
        userEdits = new HashMap<>();
        uniqueNames = new HashMap<>();
        numberOfNodes = 0;
    }

    public void incNumberOfNodes() {
        numberOfNodes++;
    }

    public void addEdit(String key) {
        if (userEdits.containsKey(key)) {
            userEdits.compute(key, (k, v) -> v + 1);
        } else {
            userEdits.put(key, 0);
        }
    }

    public void addName(String key) {
        if (uniqueNames.containsKey(key)) {
            uniqueNames.compute(key, (k, v) -> v + 1);
        } else {
            uniqueNames.put(key, 0);
        }
    }

    public Map<String, Integer> getUserEdits() {
        LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
        userEdits.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        return reverseSortedMap;
    }

    public Map<String, Integer> getUniqueNames() {
        LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
        uniqueNames.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        return reverseSortedMap;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }
}
