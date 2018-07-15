package ${packageName}.service;

import java.util.Collection;
import java.util.List;

import com.sinoservices.framework.core.service.BaseManager;
import ${packageName}.model.${tableName}Model;

public interface ${tableName}Manager extends BaseManager {

	${tableName}Model get(Long id);

	List<${tableName}Model> getAll();

	List<${tableName}Model> findByExample(${tableName}Model example);

	${tableName}Model save(${tableName}Model model);

	List<${tableName}Model> saveAll(Collection<${tableName}Model> models);

	void remove(${tableName}Model model);

	void removeAll(Collection<${tableName}Model> models);

	void removeByPk(Long id);

	void removeAllByPk(Collection<Long> ids);

}
