package com.example.writeo;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.datasource.url=jdbc:h2:mem:virtual", "spring.datasource.username=sa", "spring.datasource.password=password"})
class PropertySourceResolverTest {

    @Autowired
    private PropertySourceResolver propertySourceResolver;

    @Test
    void areThePropertiesOverridden() {
        Assert.assertEquals("jdbc:h2:mem:virtual", propertySourceResolver.getDbUrl());
        Assert.assertEquals("sa", propertySourceResolver.getDbUsername());
        Assert.assertEquals("password", propertySourceResolver.getDbPassword());
    }
}