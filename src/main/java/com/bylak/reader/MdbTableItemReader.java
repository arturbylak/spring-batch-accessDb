package com.bylak.reader;

import com.bylak.builder.MongoRowBuilder;
import com.bylak.builder.spec.RowBuilder;
import com.bylak.manager.DatabaseManagerImpl;
import com.bylak.processor.DatabasePathProcessor;
import com.bylak.resource.exception.ResourceException;
import com.bylak.resource.spec.ResourceManager;
import com.bylak.resource.spec.RowReader;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import java.util.Map;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.util.ClassUtils;

public final class MdbTableItemReader extends
        AbstractItemCountingItemStreamItemReader<Map<String, Object>> {

    private final ResourceManager<Table> databaseManager;

    private RowReader<Map<String, Object>> tableRowReader;

    public MdbTableItemReader(final String tableName, final String folderName, final String
            pathTemplate) {
        setName(ClassUtils.getShortName(MdbTableItemReader.class));
        databaseManager = createDatabaseManager(tableName, folderName, pathTemplate);
    }

    MdbTableItemReader(final ResourceManager<Table> databaseManager){
        this.databaseManager = databaseManager;
    }

    private ResourceManager createDatabaseManager(final String tableName, final String
            folderName, final String pathTemplate) {
        final DatabasePathProcessor pathProcessor = new DatabasePathProcessor(pathTemplate,
                folderName);
        final String databasePath = pathProcessor.getDatabasePath();

        return DatabaseManagerImpl.createFromPath(tableName, databasePath);
    }

    @Override
    protected Map<String, Object> doRead() throws Exception {
        return tableRowReader.read();
    }

    @Override
    protected void doOpen() throws Exception {
        openDatabase();
        createReader();
    }

    private void openDatabase() throws ResourceException {
        databaseManager.open();
    }

    private void createReader() {
        final Table currentTable = databaseManager.getResource();
        final RowBuilder<Map<String, Object>, Row> rowBuilder = MongoRowBuilder
                .createWithCachedColumnsName(currentTable);
        tableRowReader = new TableRowReaderImpl(currentTable, rowBuilder);
    }

    @Override
    protected void doClose() throws Exception {
        if (databaseManager != null) {
            databaseManager.close();
        }
    }
}