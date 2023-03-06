package com.luxoft.springadvanced.springhibernatecache.secondlevel;

import com.luxoft.springadvanced.springhibernatecache.model.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

import java.util.concurrent.CountDownLatch;

@Controller
public class SecondTransactionController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Async("concurrent")
    public void select(CountDownLatch latch) throws InterruptedException {
        latch.await();
        System.out.println("Second Transaction Begin");
        departmentRepository.findById(1);
        departmentRepository.findById(1);
        System.out.println("Second Transaction End");
    }
}
