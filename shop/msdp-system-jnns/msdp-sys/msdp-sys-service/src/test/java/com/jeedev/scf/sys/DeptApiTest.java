package com.jeedev.scf.sys;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.base.dict.utlis.DictUtil;
import com.jeedev.msdp.sys.dept.api.DeptApi;

public class DeptApiTest extends BaseTest {
	@Autowired
	private DeptApi deptApi;

	public void findDeptPage() throws Exception {
		Map<String, Object> params = new HashMap<>();
		// params.put("roleCode","RL0001");
		PageInfo<Map<String, Object>> page = deptApi.findDeptPage(params, null);
		for (Map<String, Object> o : page.getList()) {
			System.out.println("menu:" + o);
		}

	}

	public void getMap() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("deptCode", "111111111");
		Map<String, Object> page = deptApi.getDeptMap(params);
		System.out.println(page);

	}

	public void saveTrans() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("id", "39");// 修改必传
		params.put("deptCode", "1211");
		params.put("deptName", "test");
		params.put("parentDeptCode", "");
		params.put("deptLevelCd", "11");
		params.put("sort", "1");
		params.put("statusCd", "1");
		params.put("remark", "ssssss");
		params.put("delInd", "0");

		Map<String, Object> page = deptApi.saveDeptTrans(params);
		System.out.println(page.get("id"));

	}

	@Test
	public void deleteTrans() throws Exception {
		// Code.getValue("SER_CODE", "SER_CODE_7")
		String str = DictUtil.getDictValue("SER_CODE", "SER_CODE_8");
		System.out.println(str);

	}

}
