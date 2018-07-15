package com.eason.wx.goods.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eason.wx.goods.dao.ILjContactUsInfoDao;
import com.eason.wx.goods.entity.LjContactUsInfoEntity;
import com.eason.wx.goods.service.ILjContactUsInfoService;
import com.jeedev.msdp.standard.exception.BizException;

/**
 * 联系我们信息表实现类
 *
 * @类名称 LjContactUsInfoServiceImpl
 * @类描述 <pre> 联系我们信息表实现类 </pre>
 * @作者 chenyuxin
 * @创建时间 2018年03月14日
 * @版本 v1.00
 * @修改记录 <pre>
 * 版本     		修改人 	修改时间    	 	修改内容	描述
 * ----------------------------------------------
 * 1.00 	chenyuxin     	2018年03月14日 	新建
 * ----------------------------------------------
 * </pre>
 */
@Service("ljContactUsInfoService")
public class LjContactUsInfoServiceImpl implements ILjContactUsInfoService{

	@Autowired
	private ILjContactUsInfoDao ljContactUsInfoDao;

	@Override
	public List<Map<String, Object>> findLjContactUsInfoList(Map<String, Object> params) throws BizException {
		return ljContactUsInfoDao.findLjContactUsInfoList(params);
	}

	@Override
	public Map<String, Object> getLjContactUsInfo(Map<String, Object> params) throws BizException {
		List<Map<String, Object>> list = findLjContactUsInfoList(params);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public Map<String, Object> saveOrUpdateLjContactUsInfo(Map<String, Object> params) throws BizException {
		Integer id = MapUtils.getInteger(params, "id");
		if(id == null){
			LjContactUsInfoEntity ljContactUsInfo = ljContactUsInfoDao.save(new LjContactUsInfoEntity().coverToEntity(params));
			params.put("id", ljContactUsInfo.getId());
		}else{
			LjContactUsInfoEntity findOne = ljContactUsInfoDao.findOne(id);
			ljContactUsInfoDao.update(findOne.coverToEntity(params));
//			params = findOne.coverToMap();
		}
		return params;
	}

	@Override
	public void deleteLjContactUsInfo(Map<String,Object> params) throws BizException {
		Integer id = MapUtils.getInteger(params, "id");
		LjContactUsInfoEntity findOne = ljContactUsInfoDao.findOne(id);
		findOne.setDelInd("1");
		ljContactUsInfoDao.update(findOne);
	}
}

