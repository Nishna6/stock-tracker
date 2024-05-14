package com.dj.idx.st.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BulkInsertResponse extends NewRecordResponse {

    private int records;

}
