package ru.t1.java.demo.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.t1.java.demo.dto.response.DataSourceErrorLogDto;

import static java.util.Objects.requireNonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionUtil {

    public static String getStackTraceAsString(Throwable ex) {
        requireNonNull(ex, "ex is null");
        StringBuilder result = new StringBuilder();
        for (StackTraceElement element : ex.getStackTrace()) {
            result.append(element.toString()).append("\n");
        }
        return result.toString();
    }

    public static DataSourceErrorLogDto createDataSourceErrorLog(Throwable throwable, String methodSignature){
        return  new DataSourceErrorLogDto(
                getStackTraceAsString(throwable),
                throwable.getMessage(),
                methodSignature
        );
    }
}
