package com.dj.idx.st.service;

import com.dj.idx.st.factory.TestData;
import com.dj.idx.st.model.BulkInsertResponse;
import com.dj.idx.st.model.NewRecordResponse;
import com.dj.idx.st.model.StockData;
import com.dj.idx.st.repo.StockDataRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class StockDataServiceImplTest {

    @InjectMocks
    private StockDataServiceImpl sds;

    @Mock
    private StockDataRepository sdr;

    @Test
    void testProcess() throws IOException {
        when(sdr.saveAll(anyList())).thenReturn(List.of(TestData.getStockData()));
        final byte[] fileContent = Files.readAllBytes(Paths.get(new ClassPathResource("dow_jones_index.data").getURI()));
        final MockMultipartFile multipartFile = new MockMultipartFile("file", "dow_jones_index.data",
                "text/plain", fileContent);
        final BulkInsertResponse res = sds.process(multipartFile);
        assertTrue(res.isSuccess());
        assertEquals(1, res.getRecords());
    }

    @Test
    void testProcess_error() throws IOException {
        when(sdr.saveAll(anyList())).thenThrow(new RuntimeException());
        final byte[] fileContent = Files.readAllBytes(Paths.get(new ClassPathResource("dow_jones_index.data").getURI()));
        final MockMultipartFile multipartFile = new MockMultipartFile("file", "dow_jones_index.data",
                "text/plain", fileContent);
        final BulkInsertResponse res = sds.process(multipartFile);
        assertFalse(res.isSuccess());
        assertEquals(0, res.getRecords());
    }

    @Test
    void testGetByTicker() {
        when(sdr.findByStock(anyString())).thenReturn(List.of(TestData.getStockData(), TestData.getStockData()));
        assertEquals(2, sds.getByTicker("ZZ").size());
    }

    @Test
    void testPersist() {
        final StockData req = TestData.getStockData();
        when(sdr.save(any(StockData.class))).thenReturn(req);
        final NewRecordResponse res = sds.persist(req);
        assertTrue(res.isSuccess());
    }

}
