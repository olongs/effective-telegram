package com.test.util.mq;

import com.google.gson.Gson;
import com.test.model.GoodInfo;
import com.test.util.IMSGpresccer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: EC5_Proj
 * @ClassName: Myreceiver
 * @Describe: TODD 描述
 * @Author: 王子宾
 * @Email: 1339743019@qq.com
 * @CreateDate: 2019/1/2 11:14
 */
@Component
public class MyReceiver {
    @Autowired
    private IMSGpresccer imsGpresccer;

    @RabbitListener(queues = "#{myQueue.name}")
    public void receiveMsg(String msg){
        Gson gson = new Gson();
        Object o = gson.fromJson(msg, GoodInfo.class);
        imsGpresccer.msgPresccer(o);
    }
}
