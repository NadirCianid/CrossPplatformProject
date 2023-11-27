package client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Point {
    private final long xValue;
    private final long yValue;
    private final double zValue;
}
