package com.example.projektjava.repository;

import com.example.projektjava.entity.Device;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public abstract class DeviceRepository<T extends Device> implements CRUDRepository<T> {

    protected String filename;
    protected List<T> database;

    protected DeviceRepository() {
        this.database = null;
        this.filename = null;
    }

    abstract T fromLine(String line);

    @Override
    public int create(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity is null");
        }
        database.add(entity);
        return database.size() - 1;
    }

    @Override
    public T read(int index) {
        if (index < 0 || index >= database.size()) {
            throw new IllegalArgumentException("Index out of bounds.");
        }
        return database.get(index);
    }

    @Override
    public void update(int index, T entity) {
        if (index < 0 || index >= database.size()) {
            throw new IllegalArgumentException("Index out of bounds.");
        }
        if (entity == null) {
            throw new IllegalArgumentException("Entity is null");
        }
        database.set(index, entity);
    }

    @Override
    public void delete(int index) {
        if (index < 0 || index >= database.size()) {
            throw new IllegalArgumentException("Index out of bounds.");
        }
        database.remove(index);
    }

    public void writeToFile() throws URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(filename)).toURI());
        try (BufferedWriter fileWriter = Files.newBufferedWriter(path)) {
            for (T entity: database) {
                fileWriter.write(entity.toTableString() + "\n");
            }
        } catch (IOException ignored) {
            System.out.println("Failed to save entities to a file.");
        }
    }

    public void readFromFile() throws URISyntaxException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(filename)).toURI());
        try {
            List<String> lines = Files.readAllLines(path);
            List<T> entities = lines.stream().map(line -> fromLine(line.strip())).toList();
            database.clear();
            database.addAll(entities);
        } catch (IOException ignored) {
            System.out.println("Failed to read entities from a file.");
        }
    }

}
