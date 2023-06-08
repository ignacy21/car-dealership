package pl.zajavka.buisness.management;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class DataManipulationUtil {

    public static List<String> dataAsList(final String line) {
        return Arrays.asList(line.substring(line.indexOf("->") + 2).trim().split(";"));
    }
}
