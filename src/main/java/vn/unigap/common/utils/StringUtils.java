package vn.unigap.common.utils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StringUtils {
    static public String convertIdsToString(List<Integer> ids) {
        return "-" + String.join("-", ids.stream().map(Object::toString).collect(Collectors.toList())) + "-";
    }

    static public <T> List<T> mapIdsToEntities(String ids, Function<Integer, T> entityMapper) {
        return Arrays.stream(ids.split("-")).filter(s -> !s.isEmpty()).map(Integer::parseInt).map(entityMapper)
                .collect(Collectors.toList());
    }
}
