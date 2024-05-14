package com.dj.idx.st.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StockData {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer quarter;
    private String stock;
    private String date;
    private String open;
    private String high;
    private String low;
    private String close;
    private Double volume;
    private Double percent_change_price;
    private Double percent_change_volume_over_last_wk;
    private Double previous_weeks_volume;
    private String next_weeks_open;
    private String next_weeks_close;
    private Double percent_change_next_weeks_price;
    private Double days_to_next_dividend;
    private Double percent_return_next_dividend;

}
