package com.securitymagick.domain.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Component;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.securitymagick.domain.LogMessage;


@Component
@ComponentScan({"com.securitymagick"})
public class LogDao {

	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public LogDao() {
		EmbeddedDatabase dataSource = new EmbeddedDatabaseBuilder()
    		.setType(EmbeddedDatabaseType.H2)
    		.build();
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<LogMessage> getLogMessages() {
		String sql = "SELECT * FROM logtable";
		Map<String, Object> params = new HashMap<>();
		return namedParameterJdbcTemplate.query(sql, params, new LogMessageMapper()  );
	
	}
	
	
	public Integer getNextLogId() {
		String sql = "SELECT MAX(id) FROM logtable";
		Map<String, Object> params = new HashMap<>();
		Integer maxId = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
		if (maxId == null ) {
			return 1;
		}
		return maxId + 1;
	}	
	
	public void addLog(LogMessage lm) {
		Integer logId = this.getNextLogId();		
		String sql = "INSERT INTO logtable VALUES (" +  logId + ", '" + lm.getUseragent() + "',:username,:message)";
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("username", lm.getUsername());
		mapSqlParameterSource.addValue("message", lm.getMessage());
		namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
	}	
	
	
	private static final class LogMessageMapper implements RowMapper<LogMessage> {

		public LogMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
			LogMessage logMessage = new LogMessage();
			logMessage.setId(rs.getInt("id"));
			logMessage.setUsername(rs.getString("username"));
			logMessage.setUseragent(rs.getString("useragent"));
			logMessage.setMessage(rs.getString("logmessage"));
			return logMessage;
		}
	}

}