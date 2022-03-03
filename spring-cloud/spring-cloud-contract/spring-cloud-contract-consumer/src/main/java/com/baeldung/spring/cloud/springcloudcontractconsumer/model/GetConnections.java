package com.baeldung.spring.cloud.springcloudcontractconsumer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetConnections {
    private int offset;
    private int limit;
    private String[] sort;
}
