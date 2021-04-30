package pl.compprog.serializers;

import pl.compprog.exceptions.InputOutputException;
import pl.compprog.exceptions.NoClassException;

public interface Dao<T> extends AutoCloseable {
    T read() throws InputOutputException, NoClassException;

    void write(T obj) throws InputOutputException;

    @Override
    void close();
}
