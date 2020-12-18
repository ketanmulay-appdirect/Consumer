package com.appdirect.iaas.consumer.bean;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataBean implements Serializable {
    private static final long serialVersionUID = -5277682139745665586L;
    private String id;
    private String partner;
    private String companyId;
    private Double total;
    private String invoiceNumber;
    private Date date;
}
