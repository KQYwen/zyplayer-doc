package org.dromara.zyplayer.wiki.controller.vo;

import org.dromara.zyplayer.data.repository.manage.entity.WikiPage;
import org.dromara.zyplayer.data.repository.manage.entity.WikiPageContent;
import org.dromara.zyplayer.data.repository.manage.entity.WikiPageFile;
import lombok.Data;

import java.util.List;

/**
 * wiki页面内容信息
 *
 * @author 暮光：城中城
 * @since 2019-02-28
 */
@Data
public class WikiPageContentVo {
	private WikiPage wikiPage;
	private WikiPageContent pageContent;
	private List<WikiPageFile> fileList;
	private Integer selfZan;
	private Long selfUserId;
	private Integer canEdit;
	private Integer canDelete;
	private Integer canUploadFile;
	private Integer canDeleteFile;
	private Integer canConfigAuth;

}
