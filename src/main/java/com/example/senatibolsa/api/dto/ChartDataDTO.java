package com.example.senatibolsa.api.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class ChartDataDTO {
    private String name;
    private int value;
}
