package cn.uetec.light;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**   
 * Copyright: Copyright (c) 2019 Corporation,All Right Reserved
 * 
 * @ClassName: IntelligentLightApplication.java
 * @Description: 程序的入口
 *
 * @version: v1.0.0
 * @author: ykyh
 * @date: 2019年9月24日 上午11:26:51 
 *
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
public class IntelligentLightApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(IntelligentLightApplication.class)
                .run(args);
    }
}
