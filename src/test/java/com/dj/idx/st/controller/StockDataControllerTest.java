package com.dj.idx.st.controller;

import com.dj.idx.st.Application;
import com.dj.idx.st.factory.TestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@ContextConfiguration(classes = Application.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StockDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Test
    void testUploadDataSetAndQueryDataByStockTicker() throws Exception {
        final byte[] fileContent = Files.readAllBytes(Paths.get(new ClassPathResource("dow_jones_index.data").getURI()));
        final MockMultipartFile multipartFile = new MockMultipartFile("file", "dow_jones_index.data",
                "text/plain", fileContent);
        mockMvc.perform(multipart("/api/stock-data/bulk-insert")
                        .file(multipartFile)
                        .header("x-client_id", "abc123"))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/stock-data/AA")
                        .header("x-client_id", "abc123"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testAddNewRecordAndQueryDataByStockTicker() throws Exception {
        mockMvc.perform(post("/api/stock-data/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(TestData.getStockData()))
                        .header("x-client_id", "abc123"))
                .andDo(print())
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/stock-data/ZZ")
                        .header("x-client_id", "abc123"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testQueryDataByStockTicker_errors() throws Exception {
        mockMvc.perform(get("/api/stock-data/ZZ"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
        mockMvc.perform(get("/api/stock-data/ZZ")
                        .header("x-client_id", "098zyx"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

}
