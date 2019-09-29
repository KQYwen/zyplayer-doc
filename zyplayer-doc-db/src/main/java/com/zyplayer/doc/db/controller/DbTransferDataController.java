package com.zyplayer.doc.db.controller;

import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.db.framework.db.transfer.TransferDataServer;
import com.zyplayer.doc.db.framework.json.DocDbResponseJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 数据互导工具
 *
 * @author 暮光：城中城
 * @since 2019年9月28日
 */
@AuthMan
@RestController
@RequestMapping("/zyplayer-doc-db/transfer")
public class DbTransferDataController {
	@Resource
	TransferDataServer transferDataServer;
	
	@GetMapping(value = "/start")
	public ResponseJson doTransfer() {
		Long querySourceId = 5L;
		Long storageSourceId = 5L;
		String querySql = "select * from zyplayer_doc_manage.wiki_space where id > 0";
		String storageSql = "insert into zyplayer_doc_manage._temp_wiki_space_20190928(`id`, `name`, `type`, `space_explain`, `edit_type`, `tree_lazy_load`, `open_doc`, `uuid`, `create_user_id`, `create_user_name`, `create_time`, `del_flag`) VALUES (#{id}, #{name}, #{type}, #{space_explain}, #{edit_type}, #{tree_lazy_load}, #{open_doc}, #{uuid}, #{create_user_id}, #{create_user_name}, #{create_time}, #{del_flag})";
		transferDataServer.transferData(querySourceId, storageSourceId, querySql, storageSql);
		return DocDbResponseJson.ok();
	}
}
