package com.securitymagick.domain.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Component;

import com.securitymagick.domain.AdminDBItem;


@Component
@ComponentScan({"com.securitymagick"})
public class AdminDao {

	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public AdminDao() {
		EmbeddedDatabase dataSource = new EmbeddedDatabaseBuilder()
    		.setType(EmbeddedDatabaseType.H2)
    		.build();
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<AdminDBItem> getAdminDB() {
		String sql = "SELECT * FROM admin";
		Map<String, Object> params = new HashMap<>();
		return namedParameterJdbcTemplate.query(sql, params, new AdminDBItemMapper()  );
	}	
	
	public void updateAdminDBItem(AdminDBItem adminDBItem) {
		String sql = "UPDATE admin SET adminSettingValue=:settingValue WHERE id =:id";
		
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("settingValue", adminDBItem.getSettingValue());
		mapSqlParameterSource.addValue("id", adminDBItem.getId());
		
		namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
	}

	private static final class AdminDBItemMapper implements RowMapper<AdminDBItem> {

		public AdminDBItem mapRow(ResultSet rs, int rowNum) throws SQLException {
			AdminDBItem adminDBItem = new AdminDBItem();
			adminDBItem.setId(rs.getInt("id"));
			adminDBItem.setSettingName(rs.getString("adminSetting"));
			adminDBItem.setSettingValue(rs.getString("adminSettingValue"));
			return adminDBItem;
		}
	}
}