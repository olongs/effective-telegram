package com.test.util.mq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.EntityWriter;

/**
 * @ProjectName: EC5_Proj
 * @ClassName: MqConfiguration
 * @Describe: TODD 描述
 * @Author: 王子宾
 * @Email: 1339743019@qq.com
 * @CreateDate: 2019/1/2 10:02
 */
@Configuration
public class MqConfiguration {



    @Bean
    public Queue myQueue(){
        return new Queue("myqueue");
    }

    @Bean
    public  FanoutExchange myFanoutExchange() {

        return new FanoutExchange("myexchage");
    }

    @Bean
    public Binding getBind(FanoutExchange myFanoutExchange,Queue myQueue){
        Binding to = BindingBuilder.bind(myQueue).to(myFanoutExchange);
        return to;
    }

}
