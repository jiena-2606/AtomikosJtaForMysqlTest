# AtomikosJtaForMysqlTest
##准备环境
* 测试需要lib库的依赖的pom文件
```xml
  <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
		<dependency>
		    <groupId>mysql</groupId>
		    <artifactId>mysql-connector-java</artifactId>
		    <version>5.1.40</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
		<dependency>
		    <groupId>com.alibaba</groupId>
		    <artifactId>druid</artifactId>
		    <version>1.0.26</version>
		</dependency>
		
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-jdbc</artifactId>
			<version>4.2.5.RELEASE</version>
		</dependency>
		<dependency>
			<groupId>javax.transaction</groupId>
			<artifactId>jta</artifactId>
			<version>1.1</version>
		</dependency>
		<dependency>
			<groupId>com.atomikos</groupId>
			<artifactId>atomikos-util</artifactId>
			<version>4.0.2</version>
		</dependency>
		<dependency>
			<groupId>com.atomikos</groupId>
			<artifactId>transactions</artifactId>
			<version>4.0.2</version>
		</dependency>
		<dependency>
			<groupId>com.atomikos</groupId>
			<artifactId>transactions-jta</artifactId>
			<version>4.0.2</version>
		</dependency>
		<dependency>
			<groupId>com.atomikos</groupId>
			<artifactId>transactions-jdbc</artifactId>
			<version>4.0.2</version>
		</dependency>
		<dependency>
			<groupId>com.atomikos</groupId>
			<artifactId>transactions-api</artifactId>
			<version>4.0.2</version>
		</dependency>
		<dependency>
			<groupId>cglib</groupId>
			<artifactId>cglib-nodep</artifactId>
			<version>3.2.2</version>
		</dependency>
		<!-- https://mvnrepository.com/artifact/org.perf4j/perf4j -->
		<dependency>
		    <groupId>org.perf4j</groupId>
		    <artifactId>perf4j</artifactId>
		    <version>0.9.16</version>
		</dependency>
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-aspects -->
		<dependency>
		    <groupId>org.springframework</groupId>
		    <artifactId>spring-aspects</artifactId>
		    <version>4.2.5.RELEASE</version>
		</dependency>
  
        <!-- https://mvnrepository.com/artifact/aspectj/aspectjrt -->
		<dependency>
		    <groupId>aspectj</groupId>
		    <artifactId>aspectjrt</artifactId>
		    <version>1.5.4</version>
		</dependency>
```
    
* DataSource配置

```xml
<beans xmlns="http://www.springframework.org/schema/beans"  
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"  
    xmlns:mvc="http://www.springframework.org/schema/mvc" xmlns:tx="http://www.springframework.org/schema/tx"  
    xmlns:aop="http://www.springframework.org/schema/aop"  
    xsi:schemaLocation="http://www.springframework.org/schema/mvc   
    http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd  
    http://www.springframework.org/schema/beans   
    http://www.springframework.org/schema/beans/spring-beans-4.0.xsd  
    http://www.springframework.org/schema/context   
    http://www.springframework.org/schema/context/spring-context-4.0.xsd  
    http://www.springframework.org/schema/tx   
    http://www.springframework.org/schema/tx/spring-tx-4.0.xsd  
    http://www.springframework.org/schema/aop   
    http://www.springframework.org/schema/aop/spring-aop-4.0.xsd" default-lazy-init="true">  
    <description>数据源信息</description>  
    <!-- com.atomikos.jdbc.nonxa.AtomikosNonXADataSourceBean -->  
    <bean id="abstractXADataSource" class="com.atomikos.jdbc.AtomikosDataSourceBean" init-method="init" destroy-method="close" abstract="true">    
       <property name="xaDataSourceClassName" value="${jdbc.xaDataSourceClassName}"/>  <!-- SQLErrorCodes loaded: [DB2, Derby, H2, HSQL, Informix, MS-SQL, MySQL, Oracle, PostgreSQL, Sybase, Hana]  -->  
       <property name="poolSize" value="10" />    
       <property name="minPoolSize" value="10"/>    
       <property name="maxPoolSize" value="30"/>    
       <property name="borrowConnectionTimeout" value="60"/>    
       <property name="reapTimeout" value="20"/>    
       <property name="maxIdleTime" value="60"/>    
       <property name="maintenanceInterval" value="60"/>    
       <property name="loginTimeout" value="60"/>    
       <property name="testQuery" value="${validationQuery}"/>    
    </bean>    
    <bean id="dataSource1" parent="abstractXADataSource">    
        <property name="uniqueResourceName" value="dsDB1" />    
        <property name="xaProperties">  
            <props>  
                <prop key="url">${master.jdbc.url}</prop>  
                <prop key="password">${jdbc.password}</prop>  
                <prop key="user">${jdbc.username}</prop> <!-- mysql -->  
                <!--durid config begin  -->
                <!-- <prop key="driverClassName">${jdbc.driverClassName}</prop>   -->
                <!-- <prop key="username">${jdbc.username}</prop> -->   <!-- durid -->  
                <!-- <prop key="initialSize">0</prop>  --> 
                <!-- <prop key="maxActive">20</prop> --> <!-- 若不配置则代码执行"{dataSource-1} inited"此处停止  -->  
               <!--  <prop key="minIdle">0</prop>  
                <prop key="maxWait">60000</prop> -->  
                <!-- <prop key="validationQuery">${validationQuery}</prop> -->  
                <!-- <prop key="testOnBorrow">false</prop>  
                <prop key="testOnReturn">false</prop>  
                <prop key="testWhileIdle">true</prop>  
                <prop key="removeAbandoned">true</prop>  
                <prop key="removeAbandonedTimeout">1800</prop>  
                <prop key="logAbandoned">true</prop> -->  
                <!-- <prop key="filters">mergeStat</prop>  --> 
                <!--durid config end  -->
            </props>  
        </property>  
    </bean>    
    <bean id="dataSource2" parent="abstractXADataSource">    
        <property name="uniqueResourceName" value="dsDB2" />    
        <property name="xaProperties">  
            <props>  
                <prop key="url">${slave.jdbc.url}</prop>  
                <prop key="password">${jdbc.password}</prop>  
                 <prop key="user">${jdbc.username}</prop>  
                 <!--durid config begin  -->
                <!-- <prop key="driverClassName">${jdbc.driverClassName}</prop>  --> 
                <!-- <prop key="username">${jdbc.username}</prop>  --> 
                <!-- <prop key="initialSize">0</prop>  
                <prop key="maxActive">20</prop>  
                <prop key="minIdle">0</prop>  
                <prop key="maxWait">60000</prop>   -->
                <!-- <prop key="validationQuery">${validationQuery}</prop>  --> 
                <!-- <prop key="testOnBorrow">false</prop>  
                <prop key="testOnReturn">false</prop>  
                <prop key="testWhileIdle">true</prop>  
                <prop key="removeAbandoned">true</prop>  
                <prop key="removeAbandonedTimeout">1800</prop>  
                <prop key="logAbandoned">true</prop>  --> 
                <!-- <prop key="filters">mergeStat</prop> -->  
            </props>  
        </property>  
    </bean>    
</beans>
```

* database.properties
```properties
#mysql-Used to verify the effectiveness of the database connection   
validationQuery=SELECT 1  
jdbc.initialSize=5  
jdbc.maxActive=20  
jdbc.maxWait=60000  
jdbc.poolPreparedStatements=false  
jdbc.poolMaximumIdleConnections=0  
#jdbc.driverClassName=org.gjt.mm.mysql.Driver
jdbc.driverClassName=com.mysql.jdbc.Driver
#jdbc.xaDataSourceClassName=com.alibaba.druid.pool.xa.DruidXADataSource
#jdbc.xaDataSourceClassName=com.mysql.cj.jdbc.MysqlXADataSource
jdbc.xaDataSourceClassName=com.mysql.jdbc.jdbc2.optional.MysqlXADataSource
#1.tms business.  2.The db level optimization,data concurrency,desirable.  
master.jdbc.url=jdbc:mysql://localhost:3306/f4sch
#?useSSL=false&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull  
slave.jdbc.url=jdbc:mysql://localhost:3306/f4sch2
#?useSSL=false&useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull  
jdbc.username=yourUserName
jdbc.password=yourPassword
```
* Jta 配置
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"  
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"  
    xmlns:mvc="http://www.springframework.org/schema/mvc" xmlns:tx="http://www.springframework.org/schema/tx"  
    xmlns:aop="http://www.springframework.org/schema/aop"  
    xsi:schemaLocation="http://www.springframework.org/schema/mvc   
    http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd  
    http://www.springframework.org/schema/beans   
    http://www.springframework.org/schema/beans/spring-beans-4.0.xsd  
    http://www.springframework.org/schema/context   
    http://www.springframework.org/schema/context/spring-context-4.0.xsd  
    http://www.springframework.org/schema/tx   
    http://www.springframework.org/schema/tx/spring-tx-4.0.xsd  
    http://www.springframework.org/schema/aop   
    http://www.springframework.org/schema/aop/spring-aop-4.0.xsd" default-lazy-init="true">  
    <description>配置事物</description>  
    <!-- atomikos事务管理器 -->  
    <bean id="atomikosTransactionManager" class="com.atomikos.icatch.jta.UserTransactionManager" init-method="init" destroy-method="close">  
        <property name="forceShutdown">  
            <value>true</value>  
        </property>  
    </bean>  
   
    <bean id="atomikosUserTransaction" class="com.atomikos.icatch.jta.UserTransactionImp">  
        <property name="transactionTimeout" value="300" />  
    </bean>  
    <!-- spring 事务管理器 -->    
    <bean id="springTransactionManager" class="org.springframework.transaction.jta.JtaTransactionManager">  
        <property name="transactionManager" ref="atomikosTransactionManager" />  
        <property name="userTransaction" ref="atomikosUserTransaction" />  
        <!-- 必须设置，否则程序出现异常 JtaTransactionManager does not support custom isolation levels by default -->  
        <property name="allowCustomIsolationLevels" value="true"/>   
    </bean>  
  
    <aop:config  proxy-target-class="true">  
        <aop:advisor pointcut="(execution(* com.your.package.ex.*.* (..)))" advice-ref="txAdvice" />  
    </aop:config>  
   
    <tx:advice id="txAdvice" transaction-manager="springTransactionManager">  
        <tx:attributes>  
            <tx:method name="insert*"  propagation="REQUIRED"  read-only="true" />  
            <tx:method name="get*"  propagation="REQUIRED"  read-only="true" />  
            <tx:method name="find*"  propagation="REQUIRED"  read-only="true" />  
            <tx:method name="has*"  propagation="REQUIRED"  read-only="true" />  
            <tx:method name="locate*"  propagation="REQUIRED"  read-only="true" />  
            <tx:method name="register*" propagation="REQUIRED" rollback-for="java.lang.Exception" />  
        </tx:attributes>  
    </tx:advice>  
</beans>
```
* atomikos jta 配置(jta.properties)
```properties
# SAMPLE PROPERTIES FILE FOR THE TRANSACTION SERVICE  
# THIS FILE ILLUSTRATES THE DIFFERENT SETTINGS FOR THE TRANSACTION MANAGER  
# UNCOMMENT THE ASSIGNMENTS TO OVERRIDE DEFAULT VALUES;  
  
# Required: factory implementation class of the transaction core.  
# NOTE: there is no default for this, so it MUST be specified!   
#   
com.atomikos.icatch.service=com.atomikos.icatch.standalone.UserTransactionServiceFactory  
  
          
# Set base name of file where messages are output   
# (also known as the 'console file').  
#  
com.atomikos.icatch.console_file_name = tm.out  
  
# Size limit (in bytes) for the console file;  
# negative means unlimited.  
#  
# com.atomikos.icatch.console_file_limit=-1  
  
# For size-limited console files, this option  
# specifies a number of rotating files to   
# maintain.  
#  
# com.atomikos.icatch.console_file_count=1  
  
# Set the number of log writes between checkpoints  
#  
# com.atomikos.icatch.checkpoint_interval=500  
  
# Set output directory where console file and other files are to be put  
# make sure this directory exists!  
#  
# com.atomikos.icatch.output_dir = ./  
  
# Set directory of log files; make sure this directory exists!  
#  
# com.atomikos.icatch.log_base_dir = ./  
  
# Set base name of log file  
# this name will be  used as the first part of   
# the system-generated log file name  
#  
com.atomikos.icatch.log_base_name = tmlog  
  
# Set the max number of active local transactions   
# or -1 for unlimited.  
#  
# com.atomikos.icatch.max_actives = 50  
  
# Set the default timeout (in milliseconds) for local transactions  
#  
# com.atomikos.icatch.default_jta_timeout = 10000  
  
# Set the max timeout (in milliseconds) for local transactions  
#  
# com.atomikos.icatch.max_timeout = 300000  
  
# The globally unique name of this transaction manager process  
# override this value with a globally unique name  
#  
com.atomikos.icatch.tm_unique_name = tm  
      
# Do we want to use parallel subtransactions? JTA's default  
# is NO for J2EE compatibility  
#  
# com.atomikos.icatch.serial_jta_transactions=true  
                      
# If you want to do explicit resource registration then  
# you need to set this value to false.  
#  
# com.atomikos.icatch.automatic_resource_registration=true    
      
# Set this to WARN, INFO or DEBUG to control the granularity  
# of output to the console file.  
#  
com.atomikos.icatch.console_log_level=INFO  
      
# Do you want transaction logging to be enabled or not?  
# If set to false, then no logging overhead will be done  
# at the risk of losing data after restart or crash.  
#  
# com.atomikos.icatch.enable_logging=true  
  
# Should two-phase commit be done in (multi-)threaded mode or not?  
# Set this to false if you want commits to be ordered according  
# to the order in which resources are added to the transaction.  
#  
# NOTE: threads are reused on JDK 1.5 or higher.   
# For JDK 1.4, thread reuse is enabled as soon as the   
# concurrent backport is in the classpath - see   
# http://mirrors.ibiblio.org/pub/mirrors/maven2/backport-util-concurrent/backport-util-concurrent/  
#  
# com.atomikos.icatch.threaded_2pc=false  
  
# Should shutdown of the VM trigger shutdown of the transaction core too?  
#  
# com.atomikos.icatch.force_shutdown_on_vm_exit=false  
com.atomikos.icatch.service=com.atomikos.icatch.standalone.UserTransactionServiceFactory  
com.atomikos.icatch.console_file_name = /home/logs/tx/tx.out.log
com.atomikos.icatch.log_base_name = txlog
com.atomikos.icatch.tm_unique_name = com.atomikos.spring.jdbc.tm  
com.atomikos.icatch.console_log_level=DEBUG
```
* spring context 配置
```xml
<?xml version="1.0" encoding="UTF-8"?>    
<beans xmlns="http://www.springframework.org/schema/beans"    
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"    
    xmlns:context="http://www.springframework.org/schema/context"    
    xmlns:aop="http://www.springframework.org/schema/aop"  
    xmlns:mvc="http://www.springframework.org/schema/mvc"    
    xsi:schemaLocation="http://www.springframework.org/schema/beans      
                        http://www.springframework.org/schema/beans/spring-beans-3.2.xsd      
                        http://www.springframework.org/schema/context      
                        http://www.springframework.org/schema/context/spring-context-3.2.xsd  
                        http://www.springframework.org/schema/aop   
                        http://www.springframework.org/schema/aop/spring-aop-3.2.xsd      
                        http://www.springframework.org/schema/mvc      
                        http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd">    
                          
    <!-- 使用annotation 自动注册bean,并检查@Required,@Autowired的属性已被注入 -->  
    <context:component-scan base-package="com.your.package.ex" />  
      
    <!-- 使用AspectJ方式配置AOP -->    
    <aop:aspectj-autoproxy />  
      
    <!-- 引入属性配置文件 -->  
    <bean id="propertyConfigurer" class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">   
        <property name="location" value="classpath:database.properties" />          
    </bean>    
      
    <!--或 <context:property-placeholder location="classpath*:*.properties" /> -->  
</beans>
```
* JDBC-TEMPLATE 配置
```xml
<?xml version="1.0" encoding="UTF-8"?>  
<beans xmlns="http://www.springframework.org/schema/beans"  
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"   
    xmlns:context="http://www.springframework.org/schema/context"  
    xmlns:mvc="http://www.springframework.org/schema/mvc"   
    xmlns:tx="http://www.springframework.org/schema/tx"  
    xmlns:aop="http://www.springframework.org/schema/aop"   
    xsi:schemaLocation="http://www.springframework.org/schema/mvc   
    http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd  
    http://www.springframework.org/schema/beans   
    http://www.springframework.org/schema/beans/spring-beans-4.0.xsd  
    http://www.springframework.org/schema/context   
    http://www.springframework.org/schema/context/spring-context-4.0.xsd  
    http://www.springframework.org/schema/tx   
    http://www.springframework.org/schema/tx/spring-tx-4.0.xsd  
    http://www.springframework.org/schema/aop   
    http://www.springframework.org/schema/aop/spring-aop-4.0.xsd" default-lazy-init="true">  
    <description>JDBC-TEMPLATE的数据库持久层配置/配置主-从数据源</description>  
    <bean id="mJdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">  
        <property name="dataSource" ref="dataSource1" />  
    </bean>  
   
    <bean id="sJdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">  
        <property name="dataSource" ref="dataSource2" />  
    </bean>  
</beans>
```
##测试代码
```java
package com.your.package.ex

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JTATest {
	
	private Logger log = LoggerFactory.getLogger("JTATest");
	@Autowired
	private JdbcTemplate mJdbcTemplate;
	@Autowired
	private JdbcTemplate sJdbcTemplate;
	
	@Transactional
	public void insert()throws Throwable{
		mJdbcTemplate.update("insert into users (name) values(?)", new Object[]{"userName3"});
		sJdbcTemplate.update("insert into users (id,name) values(?)", new Object[]{999999999999999999l,"userName3"});
//		throw new RuntimeException("ssssssssssss");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext appCtx = new ClassPathXmlApplicationContext("classpath:*.xml");
		JTATest jtat = appCtx.getBean(JTATest.class); 
		try {
			jtat.insert();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
```
