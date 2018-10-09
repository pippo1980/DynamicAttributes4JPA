package com.sirius.test.jpa;

import com.sirius.jpa.dynamic.attribute.DemoApplication;
import com.sirius.jpa.dynamic.attribute.DynamicAttributes;
import com.sirius.jpa.dynamic.attribute.demo.HouseResource;
import com.sirius.jpa.dynamic.attribute.demo.HouseResourceService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoApplication.class})
public class HouseResourceServiceTest {

    @Autowired
    HouseResourceService houseResourceService;

    static Long id = null;

    @Test
    public void test_1() {
        HouseResource resource = new HouseResource();
        resource.setTradeAttributes(new DynamicAttributes());
        resource.getTradeAttributes().putValue("t1", "v1");
        houseResourceService.save(resource);
        id = resource.getId();
        System.out.println(id);
    }

    @Test
    @Transactional
    public void test_2() {
        System.out.println(id);
        HouseResource resource = houseResourceService.getOne(id);
        System.out.println(resource);
        Assert.assertNotNull(resource.getTradeAttributes());
        System.out.println(resource.getTradeAttributes().getValue("t1"));
    }

    @Test
    @Transactional
    public void test_3() {
        // TODO 这里需要增强,才能实现列表加载的设置值

        houseResourceService.findAll().forEach(resource -> {
            System.out.println(resource);
            Assert.assertNotNull(resource.getTradeAttributes());
            System.out.println(resource.getTradeAttributes().getValue("t1"));
        });
    }
}
