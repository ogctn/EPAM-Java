package edu.epam.fop.lambdas;

import java.util.function.Function;

@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Throwable> {

    R apply(T t) throws E;

    public static <T, R, E extends Throwable> Function<T, R> quiet(ThrowingFunction<T, R, E> f) {
        return (f == null ? null : t -> {
            try {
                return (f.apply(t));
            } catch (Throwable e) {
                throw (new RuntimeException(e));
            }
        }
        );
    }

}
