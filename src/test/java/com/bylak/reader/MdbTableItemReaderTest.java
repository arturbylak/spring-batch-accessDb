package com.bylak.reader;


import com.bylak.resource.spec.ResourceManager;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class MdbTableItemReaderTest {
    private ResourceManager<Table> resourceManager;
    private Table currentTable;;

    @Before
    public void setUp(){
        resourceManager = mock(ResourceManager.class);

        currentTable = mock(Table.class);

        final Column firstColumn = mock(Column.class);
        when(firstColumn.getName()).thenReturn("firstColumn");

        final List<? extends Column> columns = ImmutableList.of(firstColumn);
        Mockito.<List<? extends Column>>when(currentTable.getColumns()).thenReturn(columns);

        final Row row = mock(Row.class);
        when(row.get("firstColumn")).thenReturn("firstValue");
        final List<Row> rows = ImmutableList.of(row);

        when(currentTable.iterator()).thenReturn(rows.iterator());

        when(resourceManager.getResource()).thenReturn(currentTable);
    }

    @Test
    public void shouldOpenResource() throws Exception {
        //given
        final MdbTableItemReader reader = new MdbTableItemReader(resourceManager);

        //when
        reader.doOpen();

        //when
        verify(resourceManager, times(1)).open();
    }

    @Test
    public void shouldCloseResource() throws Exception {
        //given
        final MdbTableItemReader reader = new MdbTableItemReader(resourceManager);

        //when
        reader.doClose();

        //when
        verify(resourceManager, times(1)).close();
    }

    @Test
    public void shouldGetResource() throws Exception {
        //given
        final MdbTableItemReader reader = new MdbTableItemReader(resourceManager);
        reader.doOpen();

        //when
        reader.doRead();

        //when
        verify(resourceManager, times(1)).getResource();
    }

    @Test
    public void shouldReadOneRow() throws Exception {
        //given
        final MdbTableItemReader reader = new MdbTableItemReader(resourceManager);
        reader.doOpen();

        //when
        Map<String, Object> readedRow = reader.doRead();

        //when
        assertEquals(readedRow.size(), 1);
        assertEquals("firstValue", readedRow.get("firstColumn"));
    }
}