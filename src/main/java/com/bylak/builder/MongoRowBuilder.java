package com.bylak.builder;

import com.bylak.builder.spec.RowBuilder;
import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MongoRowBuilder implements RowBuilder<Map<String, Object>, Row> {

    private final List<String> columnsName;
    private final ElementTypeResolver elementTypeResolver;

    public static MongoRowBuilder createWithCachedColumnsName(final Table table){
        final List<? extends Column> columns = table.getColumns();
        final List<String> localColumnsName = new ArrayList<>();

        for(Column column : columns){
            final String columnName = column.getName();
            localColumnsName.add(columnName);
        }

        return new MongoRowBuilder(localColumnsName);
    }

    private MongoRowBuilder(final List<String> columnsName){
        this.columnsName = columnsName;

        this.elementTypeResolver = new ElementTypeResolver();
    }

    @Override
    public Map<String, Object> build(final Row row){
        final Map<String, Object> processedRow = new HashMap<>();

        for(int i=0; i < columnsName.size(); i++){
            final String columnName = columnsName.get(i);
            final Object value = row.get(columnName);
            final Object resolvedType = elementTypeResolver.resolveType(value);

            processedRow.put(columnName, resolvedType);
        }

        return processedRow;
    }
}
