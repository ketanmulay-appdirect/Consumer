package com.appdirect.iaas.consumer;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.appdirect.hackathon.hashlib.Hash;
import com.appdirect.iaas.consumer.bean.DataBean;
import com.appdirect.iaas.consumer.bean.DataWrapper;
import com.appdirect.iaas.consumer.queue.MessageReceiver;
import com.appdirect.iaas.consumer.queue.MessagingAdapter;

@Component
public class Runner implements CommandLineRunner {
    private final RabbitTemplate rabbitTemplate;
    private final MessageReceiver receiver;

    public Runner(MessageReceiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(MessagingAdapter.topicExchangeName, "app.key.test", firstBatch());
        Thread.sleep(1000L);
        rabbitTemplate.convertAndSend(MessagingAdapter.topicExchangeName, "app.key.test", secondBatch());
        Thread.sleep(1000L);
        rabbitTemplate.convertAndSend(MessagingAdapter.topicExchangeName, "app.key.test", thirdBatch());
    }
    
    private DataWrapper firstBatch() {
        DataBean bean1 = new DataBean("1", "asdas", "qweq", 10.0, "454", new Date());
        DataBean bean2 = new DataBean("2", "saasd", "dsdfds", 25.0, "4234", new Date());
        DataBean bean3 = new DataBean("3", "zxczc", "xcvxcv", 21.0, "34534", new Date());
        DataBean bean4 = new DataBean("4", "dasda", "zxczxc", 22.0, "435345", new Date());
        DataBean bean5 = new DataBean("5", "zxczc", "zxczhhh", 320.0, "45654", new Date());
        
        List<DataBean> list = Arrays.asList(new DataBean[]{bean1, bean2, bean3, bean4, bean5});
        String hash = "";
        try {
            hash = Hash.calculate(list.stream().map(DataBean::getId).collect(Collectors.toList()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return new DataWrapper(list, 1, hash);
    }

    private DataWrapper secondBatch() {
        DataBean bean1 = new DataBean("6", "pqr", "erwe", 20.0, "2312", new Date());
        DataBean bean2 = new DataBean("7", "zczxcx", "dfgdfg", 2787.0, "67567", new Date());
        DataBean bean3 = new DataBean("8", "zxcgg", "yiyuiyu", 25550.0, "56756", new Date());
        DataBean bean4 = new DataBean("9", "zXZX", "fghfgh", 2650.0, "2366512", new Date());
        DataBean bean5 = new DataBean("10", "fsdfsd", "ghnghn", 2440.0, "2664312", new Date());

        List<DataBean> list = Arrays.asList(new DataBean[]{bean1, bean2, bean3, bean4, bean5});
        String hash = "";
        try {
            hash = Hash.calculate(list.stream().map(DataBean::getId).collect(Collectors.toList()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new DataWrapper(Arrays.asList(new DataBean[]{bean1, bean2, bean3, bean4, bean5}), 2, hash);
    }

    private DataWrapper thirdBatch() {
        DataBean bean1 = new DataBean("11", "fsdfds", "xcvcxv", 25530.0, "67676", new Date());
        DataBean bean2 = new DataBean("12", "dsfsdf", "xcvxcv", 25550.0, "233312", new Date());
        DataBean bean3 = new DataBean("13", "czxczxc", "dgdfg", 2450.0, "4542312", new Date());
        DataBean bean4 = new DataBean("14", "xvxcv", "jghj", 25530.0, "67657", new Date());
        DataBean bean5 = new DataBean("15", "sdfsdf", "vbnvb", 6520.0, "66767", new Date());
        
        List<DataBean> list = Arrays.asList(new DataBean[]{bean1, bean2, bean3, bean4, bean5});
        String hash = "";
        try {
            hash = Hash.calculate(list.stream().map(DataBean::getId).collect(Collectors.toList()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new DataWrapper(Arrays.asList(new DataBean[]{bean1, bean2, bean3, bean4, bean5}), 3, hash);
    }
}
