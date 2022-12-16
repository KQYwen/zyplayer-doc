package com.zyplayer.doc.data.repository.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 暮光：城中城
 * @since 2018-11-27
 */
@Data
@TableName("zyplayer_storage")
public class ZyplayerStorage implements Serializable {

	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	private String docKey;

	private String docValue;

	private Date creationTime;
}
