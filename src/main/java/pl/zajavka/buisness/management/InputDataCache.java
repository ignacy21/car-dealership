package pl.zajavka.buisness.management;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class InputDataCache {

    private static final String FILE_PATH = "src/main/resources/car_dealership-traffic-simulation.md";

    private static final Map<String, List<String>> inputData;

    static {
        try {
            inputData = readFileContent();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Map<String, List<String>> readFileContent() throws IOException {
        List<String> list = Files.readAllLines(Paths.get(FILE_PATH)).stream()
                .filter(line -> !line.startsWith("[//]: #"))
                .filter(line -> !line.isBlank())
                .toList();

        return list.stream()
                .collect(Collectors.groupingBy(
                        line -> line.split("->")[0].trim(),
                        Collectors.mapping(
                                line -> line.substring(line.indexOf("->") + 3).trim(),
                                Collectors.toList()
                        )
                ));
    }

    public static<T> List<T> getInputData(
            final Keys.InputDataGroup inputDataGroup,
            final Keys.Entity entity,
            final Function<String, T> mapper
            ) {

        return Optional.ofNullable(inputData.get(inputDataGroup.toString()))
                .orElse(List.of())
                .stream()
                .filter(line -> line.startsWith(entity.name()))
                .map(mapper)
                .toList();
    }

}
