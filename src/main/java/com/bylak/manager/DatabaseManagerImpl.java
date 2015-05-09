package com.bylak.manager;

import com.bylak.resource.exception.ResourceException;
import com.bylak.resource.spec.ResourceManager;
import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;
import java.io.File;
import java.io.IOException;
import java.util.List;

public final class DatabaseManagerImpl implements ResourceManager<Table> {
    private final String tableName;
    private final File databaseLocalization;

    private Table tableToProcess;
    private Database databaseToProcess;
    private List<? extends Column> selectedTableColumns;

    public static DatabaseManagerImpl createFromPath(final String tableName, final String
            databaseLocalization) {
        final File databaseLocalizationFile = new File(databaseLocalization);

        return new DatabaseManagerImpl(tableName, databaseLocalizationFile);
    }

    private DatabaseManagerImpl(final String tableName, final File databaseLocalization) {
        this.tableName = tableName;
        this.databaseLocalization = databaseLocalization;
    }

    @Override
    public void open() throws ResourceException {
        try {
            doOpenUnsafe();
        } catch (Exception e) {
            throw new ResourceException("Error while opening database", e);
        }
    }

    private void doOpenUnsafe() throws IOException {
        databaseToProcess = DatabaseBuilder.open(databaseLocalization);
        tableToProcess = databaseToProcess.getTable(tableName);
        selectedTableColumns = tableToProcess.getColumns();
    }

    @Override
    public void close() throws ResourceException {
        try {
            doCloseUnsafe();
        } catch (Exception e) {
            throw new ResourceException("Error while closing database", e);
        }
    }

    @Override
    public Table getResource() {
        return tableToProcess;
    }

    private void doCloseUnsafe() throws IOException {
        if (databaseToProcess != null) {
            databaseToProcess.close();
        }
    }
}
