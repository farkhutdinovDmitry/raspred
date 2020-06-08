import lombok.var;

public class HelloWorld {
    public static void main(String[] args) {
        var statCollector = new StatCollector();
        statCollector.collectStats();
    }
}
