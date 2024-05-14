package com.dj.idx.st.repo;

import com.dj.idx.st.model.StockData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockDataRepository extends JpaRepository<StockData, Long> {

    List<StockData> findByStock(final String stock);

}
