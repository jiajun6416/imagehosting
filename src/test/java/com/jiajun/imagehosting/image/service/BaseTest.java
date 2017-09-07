package com.jiajun.imagehosting.image.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationcontext-dataSource.xml","classpath:spring/applicationcontext-main.xml"})
/**
 * @描述：测试类如果支持事物必须集成AbstractTransactionalJUnit4SpringContextTests
 * 		子类需要什么配置文件可以自行加载
 * @author jiajun
 * @date 2017年6月17日下午7:55:30
 */
public class BaseTest extends AbstractTransactionalJUnit4SpringContextTests{
	
}
