package com.dj.idx.st.service;

import com.dj.idx.st.model.BulkInsertResponse;
import com.dj.idx.st.model.NewRecordResponse;
import com.dj.idx.st.model.StockData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StockDataService {

    BulkInsertResponse process(final MultipartFile file);

    List<StockData> getByTicker(final String ticker);

    NewRecordResponse persist(final StockData stockData);

}
