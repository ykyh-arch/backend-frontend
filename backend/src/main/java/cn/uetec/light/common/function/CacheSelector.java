package cn.uetec.light.common.function;

@FunctionalInterface
public interface CacheSelector<T> {
    T select() throws Exception;
}
