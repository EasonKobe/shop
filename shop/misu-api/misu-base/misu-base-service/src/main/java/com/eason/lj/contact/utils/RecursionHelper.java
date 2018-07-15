package com.eason.lj.contact.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecursionHelper {

	public static Map<String,Map<String,Object>> createMapByListForIndex( List<Map<String,Object>>  list,String keyName){
		if (list == null || list.size() <= 0) {
			return null;
		}
		
		 Map<String,Map<String,Object>> rtMap = new HashMap<>();
		
		for (Map<String, Object> unit : list) {
			rtMap.put((String) unit.get(keyName),unit);
		}
		return rtMap;
	}
	
	/**
	 * 
	 * @param list
	 * @param catalogKey
	 * @return
	 */
	public static  Map<String,List<Map<String,Object>>> createMapByListForCatalog(List<Map<String,Object>>  list,String catalogKey){
		
		if (list == null || list.size() == 0) {
			return null;
		}
		 Map<String,List<Map<String,Object>>> rt = new HashMap<>();
		for (Map<String,Object> unit : list) {
			
			String key = (String) unit.get(catalogKey);
			
			if ( key != null && !"".equals(key)) {
				if (rt.containsKey(key)) {
					rt.get(key).add(unit);
					
				}else {
					rt.put(key, new ArrayList<Map<String,Object>>());
					rt.get(key).add(unit);
				}
			}
		}
		return rt;
	}
	
	/**
	 * 向上递归
	 * @param allUnit    所有待递归的对象
	 * @param startUnit 递归起点对象
	 * @param resursionKey 递归字段
	 * @param result  返回结果
	 */
	public static  void findAllParent( Map<String,Map<String,Object>> allUnit,Map<String,Object> startUnit,  String resursionKey, List<Map<String,Object>>  result) {
		if (allUnit == null) {
			return;
		}
		if (startUnit == null) {
			return;
		}
		if (startUnit.get(resursionKey)!= null  && !"".equals(startUnit.get(resursionKey))) {
			Map<String,Object> parent = allUnit.get(startUnit.get(resursionKey));
			if (parent == null) {
				return;
			}
			findAllParent(allUnit,parent,resursionKey, result);
		}
		result.add(startUnit);
		
	}
	
	/**
	 * 向下递归
	 * @param allUnit 所有待递归的对象
	 * @param unit 递归起点对象
	 * @param resursionKey 递归字段
	 * @param result 返回结果
	 */
	public static void findAllChildren(Map<String,List<Map<String,Object>>> allUnit,Map<String,Object> unit, String resursionKey,List<Map<String,Object>>  result) {
		
		if (allUnit == null) {
			return;
		}
		if (unit == null) {
			return;
		}
		String code = (String) unit.get(resursionKey);
		
		List<Map<String,Object>> childrenDepts =  allUnit.get(code);
		if (childrenDepts != null) {
			for (Map<String, Object> childDept : childrenDepts) {
				findAllChildren(allUnit, childDept,resursionKey, result);
			}
		}
		result.add(unit);		
	}

	
}
