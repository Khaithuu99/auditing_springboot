package com.example.ProjectSpringBoot.Services;

public class UniqueIdGeneratorHelper {
    private static Long nextId = 1L;

    public synchronized static Long getNextId() {
        return nextId++;
    }
}
