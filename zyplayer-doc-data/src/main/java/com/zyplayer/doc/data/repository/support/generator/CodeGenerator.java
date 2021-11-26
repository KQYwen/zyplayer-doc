package com.zyplayer.doc.data.repository.support.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * 代码生成器
 *
 * @author 暮光：城中城
 * @since 2019-02-26
 */
public class CodeGenerator {
	
	public static void main(String[] args) {
		final String[] tableName = {"api_doc", "api_request_param", "api_global_param"};
		String url = "jdbc:mysql://127.0.0.1:3306/zyplayer_doc_manage?useUnicode=true&useSSL=false&characterEncoding=utf8";
		String projectPath = System.getProperty("user.dir") + "/zyplayer-doc-data";
		String outputDir = projectPath + "/src/main/java";
		String mapperDir = projectPath + "/src/main/resources/mapper/manage/";
		
		FastAutoGenerator.create(url, "root", "root")
				.globalConfig(builder -> {
					builder.author("暮光：城中城") // 设置作者
							.outputDir(outputDir)
							.dateType(DateType.ONLY_DATE)
							.fileOverride(); // 覆盖已生成文件
				})
				.packageConfig(builder -> {
					builder.parent("com.zyplayer.doc.data") // 设置父包名
							.moduleName("") // 设置父包模块名
							.controller("web.generator")
							.entity("repository.manage.entity")
							.mapper("repository.manage.mapper")
							.service("service.manage")
							.serviceImpl("service.manage.impl")
							.pathInfo(Collections.singletonMap(OutputFile.mapperXml, mapperDir));
				})
				.strategyConfig(builder -> {
					builder.addInclude(tableName) // 设置需要生成的表名
							.enableCapitalMode()
							.serviceBuilder()
							.formatServiceFileName("%sService")
							.formatServiceImplFileName("%sServiceImpl");
				})
				.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
				.execute();
	}
	
}
