package com.zyplayer.doc.data.service.manage.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.*;
import com.zyplayer.doc.data.repository.manage.mapper.ApiCustomNodeMapper;
import com.zyplayer.doc.data.repository.manage.vo.ApiCustomDocVo;
import com.zyplayer.doc.data.repository.manage.vo.ApiCustomVo;
import com.zyplayer.doc.data.service.common.ApiDocAuthJudgeService;
import com.zyplayer.doc.data.service.manage.ApiCustomNodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyplayer.doc.data.service.manage.ApiCustomParamsService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 自建接口文档节点 服务实现类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2022-01-29
 */
@Service
public class ApiCustomNodeServiceImpl extends ServiceImpl<ApiCustomNodeMapper, ApiCustomNode> implements ApiCustomNodeService {
	
	@Resource
	ApiDocAuthJudgeService apiDocAuthJudgeService;
	@Resource
	ApiCustomNodeMapper apiCustomNodeMapper;
	@Resource
	ApiCustomParamsService apiCustomParamsService;
	
	@Override
	public void addNode(ApiCustomNode apiCustomFolder, ApiCustomParams apiCustomParams) {
		apiDocAuthJudgeService.judgeDevelopAndThrow(apiCustomFolder.getDocId());
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		Long parentId = Optional.ofNullable(apiCustomFolder.getParentId()).orElse(0L);
		apiCustomFolder.setParentId(parentId);
		if (apiCustomFolder.getId() == null) {
			apiCustomFolder.setYn(1);
			apiCustomFolder.setCreateTime(new Date());
			apiCustomFolder.setCreateUserId(currentUser.getUserId());
			apiCustomFolder.setCreateUserName(currentUser.getUsername());
			// 修改顺序值，新增放最后
			Integer lastSeq = apiCustomNodeMapper.getLastSeq(parentId);
			lastSeq = Optional.ofNullable(lastSeq).orElse(0);
			apiCustomFolder.setSeqNo(lastSeq + 1);
		} else {
			apiCustomFolder.setCreateTime(null);
			apiCustomFolder.setCreateUserId(null);
			apiCustomFolder.setCreateUserName(null);
		}
		this.saveOrUpdate(apiCustomFolder);
		// 保存参数
		if (Objects.equals(apiCustomFolder.getNodeType(), 1)) {
			apiCustomParams.setNodeId(apiCustomFolder.getId());
			QueryWrapper<ApiCustomParams> nodeParamsWrapper = new QueryWrapper<>();
			nodeParamsWrapper.eq("node_id", apiCustomFolder.getId());
			ApiCustomParams customParams = apiCustomParamsService.getOne(nodeParamsWrapper);
			if (customParams != null) {
				apiCustomParams.setId(customParams.getId());
			}
			apiCustomParams.setYn(1);
			apiCustomParamsService.saveOrUpdate(apiCustomParams);
		}
	}
	
	@Override
	public void deleteNode(Long id) {
		Set<Long> deleteNodeIds = new HashSet<>();
		deleteNodeIds.add(id);
		this.getAllChildNodeId(deleteNodeIds, id);
		// 删除所有节点
		ApiCustomNode nodeDel = new ApiCustomNode();
		nodeDel.setYn(0);
		QueryWrapper<ApiCustomNode> nodeDelWrapper = new QueryWrapper<>();
		nodeDelWrapper.in("id", deleteNodeIds);
		this.update(nodeDel, nodeDelWrapper);
		// 删除所有参数
		ApiCustomParams nodeParamsDel = new ApiCustomParams();
		nodeParamsDel.setYn(0);
		QueryWrapper<ApiCustomParams> nodeParamsDelWrapper = new QueryWrapper<>();
		nodeParamsDelWrapper.in("node_id", deleteNodeIds);
		apiCustomParamsService.update(nodeParamsDel, nodeParamsDelWrapper);
	}
	
	@Override
	public void changeParent(Long id, Long parentId, Integer targetType, Integer targetSeq) {
		parentId = Optional.ofNullable(parentId).orElse(0L);
		ApiCustomNode apiCustomNode = new ApiCustomNode();
		apiCustomNode.setId(id);
		if (Objects.equals(targetType, 1)) {
			// 放入选中元素的后面
			if (parentId > 0) {
				ApiCustomNode customNodeSel = this.getById(parentId);
				apiCustomNode.setSeqNo(customNodeSel.getSeqNo() + 1);
				Long targetParentId = Optional.ofNullable(customNodeSel.getParentId()).orElse(0L);
				apiCustomNode.setParentId(targetParentId);
				apiCustomNodeMapper.updateAfterSeq(targetParentId, apiCustomNode.getSeqNo());
			} else {
				Integer lastSeq = apiCustomNodeMapper.getLastSeq(0L);
				lastSeq = Optional.ofNullable(lastSeq).orElse(0);
				apiCustomNode.setParentId(0L);
				apiCustomNode.setSeqNo(lastSeq + 1);
			}
		} else {
			// 放入选中元素的最前面
			ApiCustomNode customNodeSel = this.getById(parentId);
			// 目录才能移到里面去
			if (customNodeSel == null || Objects.equals(customNodeSel.getNodeType(), 0)) {
				apiCustomNode.setSeqNo(1);
				apiCustomNode.setParentId(parentId);
				apiCustomNodeMapper.updateAfterSeq(parentId, 1);
			} else {
				// 否则移动到目标元素后
				apiCustomNode.setSeqNo(customNodeSel.getSeqNo() + 1);
				apiCustomNode.setParentId(customNodeSel.getParentId());
				apiCustomNodeMapper.updateAfterSeq(customNodeSel.getParentId(), apiCustomNode.getSeqNo());
			}
		}
		this.updateById(apiCustomNode);
	}
	
	@Override
	public List<ApiCustomDocVo> buildCustomApiList(ApiDoc apiDoc) {
		QueryWrapper<ApiCustomNode> nodeQueryWrapper = new QueryWrapper<>();
		nodeQueryWrapper.eq("yn", 1);
		nodeQueryWrapper.eq("doc_id", apiDoc.getId());
		List<ApiCustomNode> customNodeList = this.list(nodeQueryWrapper);
		
		QueryWrapper<ApiCustomParams> paramsWrapper = new QueryWrapper<>();
		paramsWrapper.eq("yn", 1);
		paramsWrapper.eq("doc_id", apiDoc.getId());
		paramsWrapper.select("node_id", "method", "api_url");
		List<ApiCustomParams> customParamsList = apiCustomParamsService.list(paramsWrapper);
		Map<Long, ApiCustomParams> customParamsMap = customParamsList.stream().collect(Collectors.toMap(ApiCustomParams::getNodeId, val -> val));
		
		Map<Long, List<ApiCustomNode>> nodeMap = customNodeList.stream()
				.peek(item -> item.setParentId(Optional.ofNullable(item.getParentId()).orElse(0L)))
				.collect(Collectors.groupingBy(ApiCustomNode::getParentId));
		
		List<ApiCustomDocVo> customGroupChildren = this.getCustomGroupChildren(nodeMap.get(0L), nodeMap, customParamsMap);
		// 组装结果对象
		ApiCustomDocVo apiCustomVo = new ApiCustomDocVo();
		apiCustomVo.setChildren(customGroupChildren);
		apiCustomVo.setNodeName(apiDoc.getName());
		List<ApiCustomDocVo> apiCustomVoList = new LinkedList<>();
		apiCustomVoList.add(apiCustomVo);
		return apiCustomVoList;
	}
	
	/**
	 * 递归删除下级
	 *
	 * @author 暮光：城中城
	 * @since 2021-12-22
	 */
	public void getAllChildNodeId(Set<Long> deleteNodeIds, Long id) {
		QueryWrapper<ApiCustomNode> wrapper = new QueryWrapper<>();
		wrapper.eq("yn", 1);
		wrapper.eq("parent_id", id);
		List<ApiCustomNode> childrenList = this.list(wrapper);
		if (CollectionUtils.isNotEmpty(childrenList)) {
			for (ApiCustomNode folder : childrenList) {
				// 递归删除下级
				this.getAllChildNodeId(deleteNodeIds, folder.getId());
			}
			deleteNodeIds.addAll(childrenList.stream().map(ApiCustomNode::getId).collect(Collectors.toSet()));
		}
	}
	
	/**
	 * 递归获取目录树
	 *
	 * @author 暮光：城中城
	 * @since 2021-12-22
	 */
	private List<ApiCustomDocVo> getCustomGroupChildren(List<ApiCustomNode> apiFolderList, Map<Long, List<ApiCustomNode>> nodeMap, Map<Long, ApiCustomParams> customParamsMap) {
		if (CollectionUtils.isEmpty(apiFolderList)) {
			return Collections.emptyList();
		}
		List<ApiCustomDocVo> apiCustomVoList = new LinkedList<>();
		apiFolderList.sort(Comparator.comparingInt(ApiCustomNode::getSeqNo));
		for (ApiCustomNode apiCustomNode : apiFolderList) {
			List<ApiCustomNode> children = nodeMap.get(apiCustomNode.getId());
			List<ApiCustomDocVo> customGroupChildren = this.getCustomGroupChildren(children, nodeMap, customParamsMap);
			ApiCustomDocVo apiCustomVo = new ApiCustomDocVo();
			apiCustomVo.setNodeId(apiCustomNode.getId());
			apiCustomVo.setNodeType(apiCustomNode.getNodeType());
			apiCustomVo.setNodeName(apiCustomNode.getNodeName());
			apiCustomVo.setNodeDesc(apiCustomNode.getNodeDesc());
			apiCustomVo.setChildren(customGroupChildren);
			if (Objects.equals(apiCustomNode.getNodeType(), 1)) {
				ApiCustomParams apiCustomParams = customParamsMap.get(apiCustomNode.getId());
				if (apiCustomParams != null) {
					apiCustomVo.setMethod(apiCustomParams.getMethod());
					apiCustomVo.setApiUrl(apiCustomParams.getApiUrl());
				}
			}
			apiCustomVoList.add(apiCustomVo);
		}
		return apiCustomVoList;
	}
	
}
