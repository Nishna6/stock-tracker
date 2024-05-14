package com.dj.idx.st.parse;

import com.dj.idx.st.model.FileHeaders;
import com.dj.idx.st.model.StockData;
import lombok.experimental.UtilityClass;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class RecordParser {

    public List<StockData> parse(final InputStream is) throws IOException {
        final List<StockData> sd = new ArrayList<>();
        final CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(FileHeaders.class)
                .setSkipHeaderRecord(true)
                .build();
        for (final CSVRecord csvRecord : csvFormat.parse(new InputStreamReader(is))) {
            sd.add(
                    StockData.builder()
                            .quarter(Integer.valueOf(csvRecord.get(FileHeaders.quarter)))
                            .stock(csvRecord.get(FileHeaders.stock))
                            .date(csvRecord.get(FileHeaders.date))
                            .open(csvRecord.get(FileHeaders.open))
                            .high(csvRecord.get(FileHeaders.high))
                            .low(csvRecord.get(FileHeaders.low))
                            .close(csvRecord.get(FileHeaders.close))
                            .volume(parseDouble(csvRecord.get(FileHeaders.volume)))
                            .percent_change_price(parseDouble(csvRecord.get(FileHeaders.percent_change_price)))
                            .percent_change_volume_over_last_wk(parseDouble(csvRecord.get(FileHeaders
                                    .percent_change_volume_over_last_wk)))
                            .previous_weeks_volume(parseDouble(csvRecord.get(FileHeaders.previous_weeks_volume)))
                            .next_weeks_open(csvRecord.get(FileHeaders.next_weeks_open))
                            .next_weeks_close(csvRecord.get(FileHeaders.next_weeks_close))
                            .percent_change_next_weeks_price(parseDouble(csvRecord.get(FileHeaders
                                    .percent_change_next_weeks_price)))
                            .days_to_next_dividend(parseDouble(csvRecord.get(FileHeaders.days_to_next_dividend)))
                            .percent_return_next_dividend(parseDouble(csvRecord.get(FileHeaders
                                    .percent_return_next_dividend)))
                            .build()
            );
        }
        return sd;
    }

    private static Double parseDouble(final String s) {
        if (s != null && !s.isBlank()) {
            return Double.valueOf(s);
        }
        return null;
    }

}
