package ${packageName}.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sinoservices.framework.core.service.impl.BaseManagerImpl;
import ${packageName}.model.${tableName}Model;
import ${packageName}.service.${tableName}Manager;

@Service
public class ${tableName}ManagerImpl extends BaseManagerImpl implements ${tableName}Manager {

	public ${tableName}Model get(Long id) {
		return this.dao.get(${tableName}Model.class, id);
	}

	public List<${tableName}Model> getAll() {
		return this.dao.getAll(${tableName}Model.class);
	}

	public List<${tableName}Model> findByExample(${tableName}Model example) {
		return this.dao.findByExample(example);
	}

	public ${tableName}Model save(${tableName}Model model) {
		return this.dao.save(model);
	}

	public List<${tableName}Model> saveAll(Collection<${tableName}Model> models) {
		return this.dao.saveAll(models);
	}

	public void remove(${tableName}Model model) {
		this.dao.remove(model);
	}

	public void removeAll(Collection<${tableName}Model> models) {
		this.dao.removeAll(models);
	}

	public void removeByPk(Long id) {
		this.dao.removeByPk(${tableName}Model.class, id);
	}

	public void removeAllByPk(Collection<Long> ids) {
		this.dao.removeAllByPk(${tableName}Model.class, ids);
	}

}
