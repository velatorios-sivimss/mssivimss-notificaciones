package com.imss.sivimss.notificaciones.configuration.mymapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

@Repository
public interface Consultas {

	static class PureSqlProvider {
		public String sql(String sql) {
			return sql;
		}
		
	}

	@SelectProvider(type = PureSqlProvider.class, method = "sql")
	public List<Map<String, Object>> selectHashMap(String sql);

	@UpdateProvider(type = PureSqlProvider.class, method = "sql")
	public Boolean actualizar(String sql);
	
}
