package com.zyplayer.doc.data.repository.support.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {

	public static void main(String[] args) {

		final String moduleName = "manage";
//		final String[] tableName = { "zyplayer_storage", "auth_info", "user_auth", "user_info" };
//		final String[] tableName = { "wiki_space", "wiki_page", "wiki_page_content", "wiki_page_file", "wiki_page_comment", "wiki_page_zan" };
		final String[] tableName = { "wiki_page", "wiki_page_comment" };

		// 代码生成器
		AutoGenerator mpg = new AutoGenerator();
		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		final String projectPath = System.getProperty("user.dir") + "/zyplayer-doc-data";
		gc.setOutputDir(projectPath + "/src/main/java");
		gc.setAuthor("暮光：城中城");
		gc.setOpen(false);
		gc.setFileOverride(true);// 是否覆盖
		gc.setDateType(DateType.ONLY_DATE);
		gc.setServiceName("%sService");
		gc.setControllerName("Generator%sController");
		mpg.setGlobalConfig(gc);

		// 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setUrl("jdbc:mysql://127.0.0.1:3306/zyplayer_doc_manage?useUnicode=true&useSSL=false&characterEncoding=utf8");
		// dsc.setSchemaName("public");
		dsc.setDriverName("com.mysql.jdbc.Driver");
		dsc.setUsername("root");
		dsc.setPassword("root");
		mpg.setDataSource(dsc);

		// 包配置
		final PackageConfig pc = new PackageConfig();
		pc.setModuleName(null);
		pc.setParent("com.zyplayer.doc.data");
		pc.setController("web.generator");
		pc.setEntity("repository.manage.entity");
		pc.setMapper("repository.manage.mapper");
		pc.setService("service.manage");
		pc.setServiceImpl("service.manage.impl");
		mpg.setPackageInfo(pc);

		// 自定义配置
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				// to do nothing
			}
		};
		List<FileOutConfig> focList = new ArrayList<>();
		focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				return projectPath + "/src/main/resources/mapper/" + moduleName + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
			}
		});
		cfg.setFileOutConfigList(focList);
		mpg.setCfg(cfg);
		mpg.setTemplate(new TemplateConfig().setXml(null));

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		// strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
		strategy.setEntityLombokModel(false);
		strategy.setRestControllerStyle(true);
		// strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
		// strategy.setSuperEntityColumns("id");
		strategy.setInclude(tableName);// 表名
		strategy.setControllerMappingHyphenStyle(true);
		strategy.setTablePrefix(pc.getModuleName() + "_");
		mpg.setStrategy(strategy);
		mpg.setTemplateEngine(new FreemarkerTemplateEngine());
		mpg.execute();
	}

}
