package comp1140.ass2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
public class isPriceExchangeValid {

    public void test(ArrayList<Resource> resources, ArrayList<ArrayList<String>> expected) {
        Assertions.assertEquals(expected, Prices.findBuilds(resources));
    }
    @Test
    public void testRoadAndKnight(){
        ArrayList<Resource> input = new ArrayList<>(
                Arrays.asList(
                       Resource.brick,
                       Resource.wood,
                       Resource.sheep,
                       Resource.stone,
                       Resource.stone,
                       Resource.wheat
                    )
                );

        ArrayList<ArrayList<String>> expected =
                new ArrayList<>(
                        Arrays.asList(
                                new ArrayList<> (Arrays.asList("Road")),
                                new ArrayList<>(Arrays.asList("Settlement")),
                                new ArrayList<>(Arrays.asList("Solider")),
                                new ArrayList<>(Arrays.asList("Road", "Solider"))
                        )
                );

        test(input, expected);
    }
    @Test
    public void testTwoRoads(){
        ArrayList<Resource> input = new ArrayList<>(
                Arrays.asList(
                        Resource.brick,
                        Resource.wood,
                        Resource.sheep,
                        Resource.brick,
                        Resource.stone,
                        Resource.wood
                )
        );

        ArrayList<ArrayList<String>> expected =
                new ArrayList<>(
                        Arrays.asList(
                                new ArrayList<> (Arrays.asList("Road")),
                                new ArrayList<>(Arrays.asList("Road", "Road"))
                        )
                );

        test(input, expected);
    }

    @Test
    public void testThreeRoads(){
        ArrayList<Resource> input = new ArrayList<>(
                Arrays.asList(
                        Resource.brick,
                        Resource.wood,
                        Resource.brick,
                        Resource.brick,
                        Resource.wood,
                        Resource.wood
                )
        );

        ArrayList<ArrayList<String>> expected =
                new ArrayList<>(
                        Arrays.asList(
                                new ArrayList<>(Arrays.asList("Road")),
                                new ArrayList<>(Arrays.asList("Road", "Road")),
                                new ArrayList<>(Arrays.asList("Road", "Road", "Road"))
                        )
                );

        test(input, expected);
    }

    @Test
    public void testTwoSoldiers() {
        ArrayList<Resource> input = new ArrayList<>(
                Arrays.asList(
                        Resource.stone,
                        Resource.sheep,
                        Resource.wheat,
                        Resource.stone,
                        Resource.sheep,
                        Resource.wheat
                )
        );

        ArrayList<ArrayList<String>> expected =
                new ArrayList<>(
                        Arrays.asList(
                                new ArrayList<>(Arrays.asList("Solider")),
                                new ArrayList<>(Arrays.asList("Solider", "Solider"))
                        )
                );

        test(input, expected);
    }
    @Test
    public void testSettlementAndRoad(){
        ArrayList<Resource> input = new ArrayList<>(
                Arrays.asList(
                        Resource.brick,
                        Resource.wood,
                        Resource.sheep,
                        Resource.wood,
                        Resource.brick,
                        Resource.wheat
                )
        );

        ArrayList<ArrayList<String>> expected =
                new ArrayList<>(
                        Arrays.asList(
                                new ArrayList<> (Arrays.asList("Road")),
                                new ArrayList<>(Arrays.asList("Settlement")),
                                new ArrayList<>(Arrays.asList("Road, Road")),
                                new ArrayList<>(Arrays.asList("Road", "Settlement"))
                        )
                );

        test(input, expected);
    }

    @Test
    public void testCastles(){
        ArrayList<Resource> input = new ArrayList<>(
                Arrays.asList(
                        Resource.brick,
                        Resource.brick,
                        Resource.brick,
                        Resource.brick,
                        Resource.brick,
                        Resource.brick
                )
        );

        ArrayList<ArrayList<String>> expected =
                new ArrayList<>(
                        Arrays.asList(
                                new ArrayList<> (Arrays.asList("Castle"))
                        )
                );

        test(input, expected);
    }

    @Test
    public void goldExchange() {
        ArrayList<Resource> input = new ArrayList<>(
                Arrays.asList(
                        Resource.wood,
                        Resource.stone,
                        Resource.sheep,
                        Resource.gold,
                        Resource.gold,
                        Resource.gold
                )
        );

        ArrayList<ArrayList<String>> expected =
                new ArrayList<>(
                        Arrays.asList(
                                new ArrayList<> (Arrays.asList("Road")),
                                new ArrayList<> (Arrays.asList("Solider"))
                        )
                );

        test(input, expected);
    }
}
