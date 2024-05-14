package com.dj.idx.st.service;

import com.dj.idx.st.model.BulkInsertResponse;
import com.dj.idx.st.model.NewRecordResponse;
import com.dj.idx.st.model.StockData;
import com.dj.idx.st.parse.RecordParser;
import com.dj.idx.st.repo.StockDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockDataServiceImpl implements StockDataService {

    private final StockDataRepository sdr;

    @Override
    public BulkInsertResponse process(final MultipartFile file) {
        final BulkInsertResponse response = new BulkInsertResponse();
        try {
            final List<StockData> rows = sdr.saveAll(RecordParser.parse(file.getInputStream()));
            response.setRecords(rows.size());
            response.setSuccess(true);
        } catch (final Exception e) {
            log.error("Error while processing file ", e);
        }
        response.setTimestamp(LocalDateTime.now());
        return response;
    }

    @Override
    public List<StockData> getByTicker(final String ticker) {
        return sdr.findByStock(ticker);
    }

    @Override
    public NewRecordResponse persist(final StockData stockData) {
        sdr.save(stockData);
        return new NewRecordResponse(true, LocalDateTime.now());
    }

}
