package com.securitymagick.domain.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Component;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.securitymagick.domain.RegistrationForm;
import com.securitymagick.domain.User;


@Component
@ComponentScan({"com.securitymagick"})
public class UserDao {

	private static final String ANSWER = "answer";
	private static final String HABIT = "habit";
	private static final String QUESTION = "question";
	private static final String PASSWORD = "password";
	private static final String USERNAME_NAME = "username";
	/* Would be used for a mysql database
	 * private DataSource mySqlDataSource;	
	 */
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public UserDao() {
			EmbeddedDatabase dataSource = new EmbeddedDatabaseBuilder()
    		.setType(EmbeddedDatabaseType.H2)
    		.build();	
			namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/* Saving this function as how to switch to MySQL instead of embedded 
	@Autowired 
    public void setDataSource(@Qualifier("mySqlDataSource") final DataSource mySqlDataSource) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(mySqlDataSource);
        this.mySqlDataSource = mySqlDataSource;
    }
    */
	
	public List<User> getUsers() {
		String sql = "SELECT * FROM users";
		Map<String, Object> params = new HashMap<>();
		return namedParameterJdbcTemplate.query(sql, params, new UserMapper()  );

		
	}
	
	public List<User> getUser(String username) {
		String sql = "SELECT * FROM users WHERE username=:" + USERNAME_NAME;
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(USERNAME_NAME, username);
		return namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, new UserMapper()  );
	}
	
	
	public Integer getNextUserId() {
		String sql = "SELECT MAX(id) FROM users";
		Map<String, Object> params = new HashMap<>();
		Integer maxId = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
		return maxId + 1;
	}	
	
	public void addUser(RegistrationForm u) {
		Integer userId = this.getNextUserId();
		String sql = "INSERT INTO users VALUES (" +  userId + ",:" + USERNAME_NAME + ",:" + PASSWORD + ",:" + QUESTION + ",:" + ANSWER + ",:" + HABIT + ", 1, 0)";
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(USERNAME_NAME, u.getUsername());
		mapSqlParameterSource.addValue(PASSWORD, u.getPassword());
		mapSqlParameterSource.addValue(HABIT, u.getHabit());
		mapSqlParameterSource.addValue(QUESTION, u.getQuestion());
		mapSqlParameterSource.addValue(ANSWER, u.getAnswer());		
		namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
	}	
	
	public void updateUser(User u) {
		String sql = "UPDATE users SET habit=:" + HABIT + ", password=:" + PASSWORD + ", question=:" + QUESTION + ", answer=:" + ANSWER + ", isUser=" + u.getIsUser() + ", isAdmin=" + u.getIsAdmin() + " WHERE id =" +  u.getId().toString();
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(QUESTION, u.getQuestion());
		mapSqlParameterSource.addValue(PASSWORD, u.getPassword());
		mapSqlParameterSource.addValue(HABIT, u.getHabit());
		mapSqlParameterSource.addValue(ANSWER, u.getAnswer());		
		namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
	}
	
	public void updatePassword(String username, String password) {
		String sql = "UPDATE users SET password=:" + PASSWORD + " WHERE username =:" + USERNAME_NAME;
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue(USERNAME_NAME, username);
		mapSqlParameterSource.addValue(PASSWORD, password);
		namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
	}
	
	public void deleteUser(Integer userId) {
		String sql = "DELETE FROM users WHERE id =:id";
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", userId);		
		namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
	}
	
	private static final class UserMapper implements RowMapper<User> {

		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setUsername(rs.getString(USERNAME_NAME));
			user.setPassword(rs.getString(PASSWORD));
			user.setQuestion(rs.getString(QUESTION));
			user.setAnswer(rs.getString(ANSWER));
			user.setHabit(rs.getString(HABIT));
			user.setIsUser(rs.getInt("isUser"));
			user.setIsAdmin(rs.getInt("isAdmin"));
			return user;
		}
	}

}