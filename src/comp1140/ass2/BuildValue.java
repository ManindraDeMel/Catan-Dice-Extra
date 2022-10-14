package comp1140.ass2;
import java.util.HashMap;

public class BuildValue {
    public static final HashMap<String, Integer> values = new HashMap<>() {{
        put("Road", 0);
        put("Solider", 1); // oh whoops, they're called knights T_T. // TODO please rename all instances of Soliders to Knights (in the whole project) for consistency
        put("Settlement", 1);
        put("City", 1); // Adds 1 to the already existing settlement
        put("Longest Road", 2);
        put("Largest Army", 2);
    }};
}
