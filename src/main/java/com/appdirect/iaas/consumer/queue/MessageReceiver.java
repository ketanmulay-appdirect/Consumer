package com.appdirect.iaas.consumer.queue;

import org.springframework.stereotype.Component;

import com.appdirect.iaas.consumer.bean.DataWrapper;
import com.appdirect.iaas.consumer.processor.DataProcessor;

@Component
public class MessageReceiver {

    public void receiveMessage(DataWrapper wrapper) {
        DataProcessor processor = new DataProcessor();
        processor.processData(wrapper);
    }
}
