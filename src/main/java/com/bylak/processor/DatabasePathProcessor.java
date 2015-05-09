package com.bylak.processor;

public final class DatabasePathProcessor {

    private final String databasePath;

    public DatabasePathProcessor(final String pathTemplate, final String folderName){
        databasePath = String.format(pathTemplate, folderName);
    }

    public String getDatabasePath() {
        return databasePath;
    }
}
