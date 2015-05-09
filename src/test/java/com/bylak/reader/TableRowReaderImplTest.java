package com.bylak.reader;

import com.bylak.builder.MongoRowBuilder;
import com.bylak.builder.spec.RowBuilder;
import com.bylak.resource.spec.RowReader;
import com.google.common.collect.ImmutableList;
import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;


public class TableRowReaderImplTest {

    private Table currentTable;

    @Before
    public void setUp(){
        currentTable = mock(Table.class);

        final Column firstColumn = mock(Column.class);
        when(firstColumn.getName()).thenReturn("firstColumn");

        final List<? extends Column> columns = ImmutableList.of(firstColumn);
        Mockito.<List<? extends Column>>when(currentTable.getColumns()).thenReturn(columns);

        final Row row = mock(Row.class);
        when(row.get("firstColumn")).thenReturn("firstValue");
        final List<Row> rows = ImmutableList.of(row);

        when(currentTable.iterator()).thenReturn(rows.iterator());
    }

    @Test
    public void shouldReadOneRow(){
        //given
        final RowBuilder<Map<String, Object>, Row>
                rowBuilder = MongoRowBuilder.createWithCachedColumnsName(currentTable);
        final RowReader<Map<String, Object>> rowReader = new TableRowReaderImpl(currentTable,
                rowBuilder);

        //when
        final Map<String, Object> readedRow = rowReader.read();

        //then
        assertEquals(1, readedRow.size());
        assertEquals("firstValue", readedRow.get("firstColumn"));
    }
}
