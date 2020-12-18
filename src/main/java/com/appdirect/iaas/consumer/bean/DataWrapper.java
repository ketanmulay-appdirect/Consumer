package com.appdirect.iaas.consumer.bean;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DataWrapper implements Serializable {
    private List<DataBean> dataBeans;
    private int chunkId;
    private String checksum;
}
