package com.star;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * @author jiaozi<liaomin @ gvt861.com>
 * @since JDK8
 * Creation time：2019/4/9 16:25
 */
public class CodeGenerator {

   public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/star-app/src/main/java/");
        //设置生成注释作者
        gc.setAuthor("zhangrc <1538618608@qq.com>");
        //生成后是否用编辑器打开
        gc.setOpen(false);
        //是否生成swagger注释
        gc.setSwagger2(true);
        //设置设置主键使用数据主键规则
       // gc.setIdType(IdType.AUTO);
        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://123.207.120.31:3306/star?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&autoReconnect=true&failOverReadOnly=false&useSSL=false&serverTimezone=UTC");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);
        // 包配置
        PackageConfig pc = new PackageConfig();
        //设置功能名称
        //pc.setModuleName("report");
        //生成父包
        pc.setParent("com.star.module.front");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //表名是否使用波峰波谷规则
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //表名是否使用波峰波谷规则
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //字段是否添加注解
        strategy.entityTableFieldAnnotationEnable(true);
        //是否使用lombok生成get set方法
        strategy.setEntityLombokModel(true);
        //控制层是否使用RestController
        strategy.setRestControllerStyle(true);
        //生成代码的表
        strategy.setInclude("fens_join");
        strategy.setControllerMappingHyphenStyle(true);
        //设置表前缀，生成类将去掉前缀
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
