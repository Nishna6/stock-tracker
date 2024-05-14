package com.dj.idx.st.controller;

import com.dj.idx.st.model.BulkInsertResponse;
import com.dj.idx.st.model.NewRecordResponse;
import com.dj.idx.st.model.StockData;
import com.dj.idx.st.service.StockDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stock-data")
public class StockDataController {

    private final StockDataService sds;

    private static final Set<String> X_CLIENT_IDS = Set.of("abc123", "def456", "ghi789");

    @PostMapping("/bulk-insert")
    public ResponseEntity<BulkInsertResponse> uploadDataSet(@RequestHeader("X-Client_Id") final String xClientId,
                                                            @RequestParam final MultipartFile file) {
        validateXClientId(xClientId);
        return ResponseEntity.ok(sds.process(file));
    }

    @GetMapping("/{ticker}")
    public ResponseEntity<List<StockData>> queryDataByStockTicker(@RequestHeader("X-Client_Id") final String xClientId,
                                                                  @PathVariable final String ticker) {
        validateXClientId(xClientId);
        return ResponseEntity.ok(sds.getByTicker(ticker));
    }

    @PostMapping("/")
    public ResponseEntity<NewRecordResponse> addNewRecord(@RequestHeader("X-Client_Id") final String xClientId,
                                                          @RequestBody final StockData stockData) {
        validateXClientId(xClientId);
        return ResponseEntity.ok(sds.persist(stockData));
    }

    private void validateXClientId(final String xClientId) {
        if (!X_CLIENT_IDS.contains(xClientId)) {
            throw new IllegalArgumentException("Invalid `X-Client_Id` : " + xClientId);
        }
    }

}
