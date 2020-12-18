package com.appdirect.iaas.consumer.processor;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.appdirect.hackathon.hashlib.Hash;
import com.appdirect.iaas.consumer.bean.DataBean;
import com.appdirect.iaas.consumer.bean.DataWrapper;

@Component
@Slf4j
public class DataProcessor {
    public boolean validateRetrievedData(DataWrapper dataWrapper) {
        List<String> ids = getListOfIds(dataWrapper);

        try {
            return Hash.validateAndNotify(ids, dataWrapper.getChecksum(), String.valueOf(dataWrapper.getChunkId()), "ketan.mulay@appdirect.com");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateProcessedData(DataWrapper dataWrapper) {
        List<String> ids = getListOfIds(dataWrapper);

        try {
            return Hash.validateAndNotify(ids, dataWrapper.getChecksum(), String.valueOf(dataWrapper.getChunkId()), "ketan.mulay@appdirect.com");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void processData(DataWrapper dataWrapper) {
        if(validateRetrievedData(dataWrapper)) {

            switch (dataWrapper.getChunkId()) {
                case 1:
                    retainAllData(dataWrapper);
                    break;
                case 2:
                    filterSomeData(dataWrapper);
                    break;
                case 3:
                    handleErrorDuringParsing(dataWrapper);
                    break;
            }
            log.info("-----------------------------------------------------------");
            log.info("Processed Data -> ");
            log.info(dataWrapper.toString());
            log.info("-----------------------------------------------------------");

            if(!validateProcessedData(dataWrapper)) {
                log.error("############################################################");
                log.error("Error while validating processed chunk data with id -> {}  - skipped!!!", dataWrapper.getChunkId());
                log.error(dataWrapper.toString());
                log.error("############################################################");
            }
        } else {
            log.error("############################################################");
            log.error("Error while validating retrieved chunk data with id -> {}  - skipped!!!", dataWrapper.getChunkId());
            log.error(dataWrapper.toString());
            log.error("############################################################");
        }
    }
    
    private List<String> getListOfIds(DataWrapper dataWrapper) {
        return dataWrapper.getDataBeans().stream().map(DataBean::getId).collect(Collectors.toList());
    }
    
    private void retainAllData(DataWrapper dataWrapper) {
        List<DataBean> dataBeanList = dataWrapper.getDataBeans().stream().peek(d -> d.setTotal(d.getTotal() + 3)).collect(Collectors.toList());
        dataWrapper.setDataBeans(dataBeanList);
    }

    private void filterSomeData(DataWrapper dataWrapper) {
        List<DataBean> dataBeanList = dataWrapper.getDataBeans().stream().filter(d -> Integer.parseInt(d.getId())%2==0).collect(Collectors.toList());
        dataWrapper.setDataBeans(dataBeanList);
    }

    private void handleErrorDuringParsing(DataWrapper dataWrapper) {
        List<DataBean> dataBeanList = dataWrapper.getDataBeans().stream().peek(d -> processData(d)).collect(Collectors.toList());
        dataWrapper.setDataBeans(dataBeanList);
    }
    
    private void processData(DataBean bean) {
        if (Integer.parseInt(bean.getId())%2==1) {
            bean.setTotal(0d);
            bean.setCompanyId("");
            bean.setInvoiceNumber("");
            bean.setPartner("");
        }
    }
}
