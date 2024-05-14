package com.dj.idx.st.factory;

import com.dj.idx.st.model.StockData;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestData {

    public StockData getStockData() {
        return StockData.builder()
                .stock("ZZ")
                .build();
    }

}
