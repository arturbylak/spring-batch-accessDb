package com.bylak.reader;

import com.bylak.builder.spec.RowBuilder;
import com.bylak.resource.spec.RowReader;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import java.util.Iterator;
import java.util.Map;

public final class TableRowReaderImpl implements RowReader<Map<String, Object>> {
    private final Iterator<Row> rowIterator;
    private final RowBuilder<Map<String, Object>, Row> rowBuilder;

    public TableRowReaderImpl(final Table currentTable, final RowBuilder<Map<String, Object>, Row>
            rowBuilder) {
        this.rowIterator = currentTable.iterator();
        this.rowBuilder = rowBuilder;
    }

    @Override
    public Map<String, Object> read() {
        return readRow();
    }

    private Map<String,Object> readRow() {
        if(!rowIterator.hasNext()){
            return null;
        }

        final Row row = rowIterator.next();

        return buildRow(row);
    }

    private Map<String,Object> buildRow(final Row row){
        return rowBuilder.build(row);
    }
}
