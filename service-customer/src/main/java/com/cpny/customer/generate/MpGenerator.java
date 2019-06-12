package com.cpny.customer.generate;

import java.util.*;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @author huangzhongfa
 * @description 代码生成器
 * @date 2019/6/11
 */
public class MpGenerator {
    private static final String PACKAGE_NAME = "com.cpny.customer";
    private static final String OUT_PATH = "d:\\javaweb\\cpny-consumer\\service-customer\\src\\main\\java";
    private static final String AUTHOR = "huangzhongfa";

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://47.99.143.121:3306/pgy_platform_user?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "Hjn184736351";

    /**
     * MySQL 生成演示
     */
    public static void main(String[] args) {
        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig()
                .setOutputDir(OUT_PATH)// 输出目录
                .setFileOverride(true)// 是否覆盖文件
                .setActiveRecord(true)// 开启 activeRecord 模式
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(false)// XML ResultMap
                .setBaseColumnList(true)// XML columnList
                .setAuthor(AUTHOR)
                // .setServiceName("MP%sService")
                // .setServiceImplName("%sServiceDiy")
                // .setControllerName("%sAction")
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                .setXmlName("%sMapper")
                .setMapperName("%sDao");


        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setDbType(DbType.MYSQL)// 数据库类型
                .setDriverName(DRIVER)
                .setUrl(URL)
                .setUsername(USER_NAME)
                .setPassword(PASSWORD)
                .setTypeConvert(new MySqlTypeConvert() {
                    // 自定义数据库表字段类型转换【可选】
                });

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig()
                // .setCapitalMode(true)// 全局大写命名
                // .setTablePrefix(new String[]{"pgy_"})// 此处可以修改为您的表前缀
                .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                .setInclude( "pgy_user") // 需要生成的表
                // .setExclude(new String[]{"test"}) // 排除生成的表
                // 自定义实体，公共字段
                // .setSuperEntityColumns(new String[]{"test_id"})
                .setTableFillList(tableFillList)
                // 自定义实体父类
                // .setSuperEntityClass("com.baomidou.demo.base.BsBaseEntity")
                // // 自定义 mapper 父类
                // .setSuperMapperClass("com.baomidou.demo.base.BsBaseMapper")
                // // 自定义 service 父类
                // .setSuperServiceClass("com.baomidou.demo.base.BsBaseService")
                // // 自定义 service 实现类父类
                // .setSuperServiceImplClass("com.baomidou.demo.base.BsBaseServiceImpl")
                // 自定义 controller 父类
                // .setSuperControllerClass("com.baomidou.demo.TestController")
                // 【实体】是否生成字段常量（默认 false）
                // public static final String ID = "test_id";
                .setEntityColumnConstant(true)
                // 【实体】是否为构建者模型（默认 false）
                // public User setName(String name) {this.name = name; return this;}
                .setEntityBuilderModel(true)
                // Boolean类型字段是否移除is前缀处理
                // .setEntityBooleanColumnRemoveIsPrefix(true)
                // .setRestControllerStyle(true)
                // .setControllerMappingHyphenStyle(true)
                // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                .setEntityLombokModel(true);


        // 包配置
        PackageConfig packageConfig = new PackageConfig()
                .setParent(PACKAGE_NAME)// 自定义包路径
                .setController("controller")// 这里是控制器包名，默认 web
                .setEntity("entity")
                .setXml("mapper")
                .setMapper("dao");

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        }.setFileOutConfigList(
                Collections.singletonList(new FileOutConfig("/templates/mapper.xml.vm") {
                    // 自定义输出文件目录
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        return OUT_PATH + "/xml/" + tableInfo.getEntityName() + "Mapper.xml";
                    }
                }));


        // 关闭默认 xml 生成，调整生成 至 根目录
        TemplateConfig templateConfig = new TemplateConfig()
                // 自定义模板配置，模板可以参考源码 /base-plus/src/main/resources/template 使用 copy
                // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
                // .setController("...");
                // .setEntity("...");
                // .setMapper("...");
                // .setXml("...");
                // .setService("...");
                // .setServiceImpl("...");
                .setXml(null);

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator()
                .setGlobalConfig(globalConfig) //全局配置
                .setDataSource(dataSourceConfig)//数据源配置
                .setStrategy(strategyConfig)//策略配置
                .setPackageInfo(packageConfig)//包配置
                .setCfg(injectionConfig)//自定义配置
                .setTemplate(templateConfig);//模板配置
        // 执行生成
        mpg.execute();
    }
}
