package com.velsera.homework.domain.model.response;

import com.velsera.homework.domain.records.ErrorRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FacadeResponse<T> {
    private int totalCount;
    private List<T> data;
    private ErrorRecord error;
}
