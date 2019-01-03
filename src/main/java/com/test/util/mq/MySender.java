package com.test.util.mq;

import com.google.gson.Gson;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Queue;

/**
 * @ProjectName: EC5_Proj
 * @ClassName: MySender
 * @Describe: TODD 描述
 * @Author: 王子宾
 * @Email: 1339743019@qq.com
 * @CreateDate: 2019/1/2 10:05
 */
@Component
public class MySender {
    @Autowired
    private RabbitTemplate template;
    @Autowired
    private FanoutExchange exchange;


    public void sendMsg(Object object) {
        Gson gson = new Gson();
        String s = gson.toJson(object);
        template.convertAndSend(exchange.getName(),"",s);
        try{
            //给后台处理数据时间
            Thread.currentThread().sleep(2000);
        }catch (Exception e){e.printStackTrace();}
    }

}
