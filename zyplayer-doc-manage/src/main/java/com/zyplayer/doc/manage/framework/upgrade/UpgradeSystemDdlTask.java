package com.zyplayer.doc.manage.framework.upgrade;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.zyplayer.doc.core.enums.SystemConfigEnum;
import com.zyplayer.doc.core.util.UpgradeInfo;
import com.zyplayer.doc.core.util.ZyplayerDocVersion;
import com.zyplayer.doc.data.repository.manage.mapper.UserInfoMapper;
import com.zyplayer.doc.data.service.manage.SystemConfigService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 系统升级自动执行SQL，拷贝请写好源代码出处，保留原作者信息
 *
 * @author 暮光：城中城
 * @since 2022-12-03
 */
@Service
public class UpgradeSystemDdlTask {
	private static final Logger logger = LoggerFactory.getLogger(UpgradeSystemDdlTask.class);
	
	@Resource
	UserInfoMapper userInfoMapper;
	@Resource
	SystemConfigService systemConfigService;
	
	/**
	 * 升级SQL检测
	 *
	 * @author 暮光：城中城
	 * @since 2022-12-03
	 */
	@PostConstruct
	public void init() {
		try {
			String nowVersion = systemConfigService.getConfigValue(SystemConfigEnum.DOC_SYSTEM_VERSION);
			if (Objects.equals(nowVersion, ZyplayerDocVersion.version)) {
				logger.info("当前数据库DDL已是最新版本：" + nowVersion);
				return;
			}
			List<String> tableList = userInfoMapper.getTableList();
			if (StringUtils.isBlank(nowVersion)) {
				if (CollectionUtils.isEmpty(tableList)) {
					// 新部署，执行全量建表语句
					initDatabase();
				} else {
					// 执行一次最新的脚本
					upgradeByStart();
				}
			} else {
				// 依次执行高于此版本的脚本
				upgradeByNowVersion(nowVersion, tableList);
			}
			// 更新当前版本
			systemConfigService.setConfigValue(SystemConfigEnum.DOC_SYSTEM_VERSION, ZyplayerDocVersion.version);
		} catch (Exception e) {
			logger.error("执行升级SQL失败，请手动执行升级SQL或修改：system_config表中的system_version记录行的版本为：{}，异常信息：{}", ZyplayerDocVersion.version, e.getMessage());
		}
	}
	
	/**
	 * 初始化数据库
	 *
	 * @author 暮光：城中城
	 * @since 2022-12-03
	 */
	public void initDatabase() {
		String sql = loadDDLFile("sql/full/full.sql");
		if (StringUtils.isBlank(sql)) {
			logger.error("初始化数据库DDL失败，未找到当前版本的DDL脚本");
			return;
		}
		MySqlStatementParser parser = new MySqlStatementParser(sql);
		List<SQLStatement> sqlStatements = parser.parseStatementList();
		for (SQLStatement sqlStatement : sqlStatements) {
			// 执行SQL
			try {
				userInfoMapper.executeSql(sqlStatement.toString());
			} catch (Exception e) {
				logger.info("执行升级SQL异常：" + e.getMessage());
			}
		}
		logger.info("初始化数据库DDL完成");
	}
	
	/**
	 * 从指定版本升级到最新版本
	 *
	 * @author 暮光：城中城
	 * @since 2022-12-03
	 */
	public void upgradeByNowVersion(String nowVersion, List<String> tableList) {
		logger.info("升级数据库DDL脚本：{} --> {}", nowVersion, ZyplayerDocVersion.version);
		boolean isStart = false;
		for (int i = ZyplayerDocVersion.versionUpgrade.size() - 1; i >= 0; i--) {
			UpgradeInfo upgradeInfo = ZyplayerDocVersion.versionUpgrade.get(i);
			if (!isStart) {
				if (Objects.equals(upgradeInfo.getVersion(), nowVersion)) {
					isStart = true;
				}
			} else if (!upgradeInfo.isHaveUpgradeSql()) {
				logger.info("该版本无DDL脚本，跳过此版本：" + upgradeInfo.getVersion());
			} else {
				upgradeByVersion(upgradeInfo.getVersion(), tableList);
			}
		}
	}
	
	/**
	 * SQL脚本升级到指定版本
	 *
	 * @author 暮光：城中城
	 * @since 2022-12-03
	 */
	public void upgradeByVersion(String version, List<String> tableList) {
		logger.info("升级数据库DDL开始：" + version);
		String sql = loadDDLFile("sql/upgrade/" + version + ".sql");
		if (StringUtils.isBlank(sql)) {
			logger.info("未找到当前版本的DDL脚本：" + version);
			return;
		}
		MySqlStatementParser parser = new MySqlStatementParser(sql);
		List<SQLStatement> sqlStatements = parser.parseStatementList();
		for (SQLStatement sqlStatement : sqlStatements) {
			// 执行SQL
			try {
				handleAlterTable(sqlStatement);
				handleCreateIndex(sqlStatement);
				handleCreateTable(sqlStatement, tableList);
				userInfoMapper.executeSql(sqlStatement.toString());
			} catch (Exception e) {
				logger.info("执行升级SQL异常：" + e.getMessage());
			}
		}
		logger.info("升级数据库DDL完成：" + version);
	}
	
	/**
	 * 从开始实行此方案后的第一个版本开始执行SQL脚本
	 *
	 * @author 暮光：城中城
	 * @since 2022-12-03
	 */
	public void upgradeByStart() {
		logger.info("初始升级数据库DDL脚本：{} --> {}", "1.1.1", ZyplayerDocVersion.version);
		for (int i = ZyplayerDocVersion.versionUpgrade.size() - 1; i >= 0; i--) {
			UpgradeInfo upgradeInfo = ZyplayerDocVersion.versionUpgrade.get(i);
			if (!upgradeInfo.isHaveUpgradeSql()) {
				logger.info("该版本无DDL脚本，跳过此版本：" + upgradeInfo.getVersion());
				continue;
			}
			String sql = loadDDLFile("sql/upgrade/" + upgradeInfo.getVersion() + ".sql");
			if (StringUtils.isBlank(sql)) {
				return;
			}
			MySqlStatementParser parser = new MySqlStatementParser(sql);
			List<SQLStatement> sqlStatements = parser.parseStatementList();
			for (SQLStatement sqlStatement : sqlStatements) {
				// 执行SQL
				try {
					userInfoMapper.executeSql(sqlStatement.toString());
				} catch (Exception e) {
					logger.info("执行升级SQL异常：" + e.getMessage());
				}
			}
		}
		logger.info("升级数据库DDL完成：" + ZyplayerDocVersion.version);
	}
	
	/**
	 * 处理创建表检查
	 *
	 * @author 暮光：城中城
	 * @since 2022-12-03
	 */
	public void handleCreateTable(SQLStatement sqlStatement, List<String> tableList) {
		if (!(sqlStatement instanceof SQLCreateTableStatement)) {
			return;
		}
		SQLCreateTableStatement createTableStatement = (SQLCreateTableStatement) sqlStatement;
		String tableName = StringUtils.remove(createTableStatement.getTableName(), "`");
		if (tableList.contains(tableName)) {
			throw new RuntimeException("表已存在：" + tableName);
		}
	}
	
	/**
	 * 处理创建索引检查
	 *
	 * @author 暮光：城中城
	 * @since 2022-12-03
	 */
	public void handleCreateIndex(SQLStatement sqlStatement) {
		if (!(sqlStatement instanceof SQLCreateIndexStatement)) {
			return;
		}
		SQLCreateIndexStatement createIndexStatement = (SQLCreateIndexStatement) sqlStatement;
		String tableName = StringUtils.remove(createIndexStatement.getTableName(), "`");
		String indexName = StringUtils.remove(createIndexStatement.getIndexDefinition().getName().getSimpleName(), "`");
		List<String> tableIndexList = userInfoMapper.getTableIndexList(tableName).stream().map(map -> String.valueOf(map.get("Key_name"))).collect(Collectors.toList());
		if (tableIndexList.contains(indexName)) {
			throw new RuntimeException("表索引已存在：" + tableName + "." + indexName);
		}
	}
	
	/**
	 * 处理修改表检查
	 *
	 * @author 暮光：城中城
	 * @since 2022-12-03
	 */
	public void handleAlterTable(SQLStatement sqlStatement) {
		if (!(sqlStatement instanceof SQLAlterTableStatement)) {
			return;
		}
		SQLAlterTableStatement alterTableStatement = (SQLAlterTableStatement) sqlStatement;
		String tableName = StringUtils.remove(alterTableStatement.getTableName(), "`");
		List<String> tableColumnList = userInfoMapper.getTableColumnList(tableName).stream().map(map -> String.valueOf(map.get("Field"))).collect(Collectors.toList());
		List<String> tableIndexList = userInfoMapper.getTableIndexList(tableName).stream().map(map -> String.valueOf(map.get("Key_name"))).collect(Collectors.toList());
		for (SQLAlterTableItem tableItem : alterTableStatement.getItems()) {
			if (tableItem instanceof SQLAlterTableAddColumn) {
				SQLAlterTableAddColumn addColumn = (SQLAlterTableAddColumn) tableItem;
				for (SQLColumnDefinition column : addColumn.getColumns()) {
					String columnName = StringUtils.remove(column.getColumnName(), "`");
					if (tableColumnList.contains(columnName)) {
						throw new RuntimeException("字段已存在：" + tableName + "." + columnName);
					}
				}
			} else if (tableItem instanceof SQLAlterTableAddIndex) {
				SQLAlterTableAddIndex addColumn = (SQLAlterTableAddIndex) tableItem;
				String indexName = StringUtils.remove(addColumn.getIndexDefinition().getName().getSimpleName(), "`");
				if (tableIndexList.contains(indexName)) {
					throw new RuntimeException("表索引已存在：" + tableName + "." + indexName);
				}
			}
		}
	}
	
	/**
	 * 加载ddl脚本文件内容
	 *
	 * @author 暮光：城中城
	 * @since 2022-12-03
	 */
	private String loadDDLFile(String fileName) {
		ClassPathResource resource = new ClassPathResource(fileName);
		if (!resource.exists()) {
			logger.error("读取DDL文件失败：" + fileName);
			return null;
		}
		try {
			byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
			return new String(bytes, StandardCharsets.UTF_8);
		} catch (IOException e) {
			logger.error("读取DDL文件失败：" + fileName, e);
			return null;
		}
	}
}




