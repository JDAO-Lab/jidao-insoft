package com.inSoft;

import com.inSoft.utils.DeBugUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement
public class InSoftApplication {

    public static void main(String[] args) {
        SpringApplication.run(InSoftApplication.class, args);
        DeBugUtils.print("Project is runing~~");
    }

}
