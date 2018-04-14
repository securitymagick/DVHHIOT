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

import com.securitymagick.domain.Post;
import com.securitymagick.domain.PostComment;


@Component
@ComponentScan({"com.securitymagick"})
public class PostDao {

	
	private static final String TITLE = "title";
	private static final String AUTHOR = "author";
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	public PostDao() {
		EmbeddedDatabase dataSource = new EmbeddedDatabaseBuilder()
    		.setType(EmbeddedDatabaseType.H2)
    		.build();
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<Post> getPosts() {
		String sql = "SELECT * FROM posts";
		Map<String, Object> params = new HashMap<>();
		return namedParameterJdbcTemplate.query(sql, params, new PostMapper()  );
	}
	
	public List<Post> getPostsWithComments() {
		List<Post> posts = this.getPosts();
		List<PostComment> comments = this.getComments();
		for (PostComment c1 : comments) {
			for (Post p1: posts) {
				if (p1.getId().toString().equals(c1.getPostid())) {
					p1.addComment(c1.getUsername() + " said: " + c1.getComment());
				}
			}
		}
		return posts;
	}
	
	public List<PostComment> getComments() {
		String sql = "SELECT * FROM comments";
		Map<String, Object> params = new HashMap<>();
		return namedParameterJdbcTemplate.query(sql, params, new PostCommentMapper()  );
	}
	
	public Integer getNextPostId() {
		String sql = "SELECT MAX(id) FROM posts";
		Map<String, Object> params = new HashMap<>();
		Integer maxId = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
		return maxId + 1;
	}
	
	public Integer getNextCommentId() {
		String sql = "SELECT MAX(id) FROM comments";
		Map<String, Object> params = new HashMap<>();
		Integer maxId = namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);
		return maxId + 1;
	}
	
	public void addPost(Post p) {
		String sql = "INSERT INTO posts VALUES (:id,:" + TITLE + ",:imageName,:thePost,:"+ AUTHOR + ")";

		Integer postId = this.getNextPostId();
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", postId);
		mapSqlParameterSource.addValue(TITLE, p.getTitle());
		mapSqlParameterSource.addValue("imageName", p.getImageName());
		mapSqlParameterSource.addValue("thePost", p.getThePost());
		mapSqlParameterSource.addValue(AUTHOR, p.getAuthor());		
		namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
	}
	
		public void updatePost(Post p) {
		String sql = "UPDATE posts SET title=:" + TITLE + " WHERE id =:id";
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", p.getId());
		mapSqlParameterSource.addValue(TITLE, p.getTitle());				
		namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
	}
	
	public void addComment(PostComment pc) {
		String sql = "INSERT INTO comments VALUES (:id,:postId,:comment,:username)";
		Integer commentId = this.getNextCommentId();
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", commentId);
		mapSqlParameterSource.addValue("postId", pc.getPostid());
		mapSqlParameterSource.addValue("comment", pc.getComment());
		mapSqlParameterSource.addValue("username", pc.getUsername());
		namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
	}
	
	public void deletePost(Integer postId) {
		String sql = "DELETE FROM posts WHERE id =:id ";
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", postId);
		namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
	}
	
	public void deleteComment(String commentId) {
		String sql = "DELETE FROM comments WHERE id = :id";
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", commentId);
		namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
	}
	
	private static final class PostMapper implements RowMapper<Post> {

		public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
			Post post = new Post();
			post.setId(rs.getInt("id"));
			post.setTitle(rs.getString(TITLE));
			post.setImageName(rs.getString("imageName"));
			post.setAuthor(rs.getString(AUTHOR));
			post.setThePost(rs.getString("thePost"));
			return post;
		}
	}
	
	private static final class PostCommentMapper implements RowMapper<PostComment> {

		public PostComment mapRow(ResultSet rs, int rowNum) throws SQLException {
			PostComment postComment = new PostComment();
			postComment.setId(rs.getInt("id"));
			postComment.setPostid(rs.getString("postid"));
			postComment.setComment(rs.getString("theComment"));
			postComment.setUsername(rs.getString(AUTHOR));
			return postComment;
		}
	}
}