package ru.kek.memehouse.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.kek.memehouse.dao.interfaces.MemesDao;
import ru.kek.memehouse.models.Meme;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * gordeevnm@gmail.com
 * 14.01.18
 */
@Repository
public class MemesDaoJdbcImpl implements MemesDao {
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	private Map<String, Object> getParams(Meme meme) {
		Map<String, Object> params = new HashMap<>();
		params.put("created_by", meme.getCreatedBy());
		params.put("description", meme.getDescription());
		params.put("name", meme.getName());
		params.put("create_time", meme.getCreateTime());
		params.put("is_public", meme.isPublic());
		params.put("tags_array", meme.getTags());
		
		return params;
	}
	
	@Override
	public void create(Meme meme) {
		final String sql =
			  "select * from insert_meme(created_by := :created_by :: bigint," +
					 "                     description := :description :: varchar," +
					 "                     name := :name :: varchar," +
					 "                     create_time := :create_time :: timestamp," +
					 "                     is_public := :is_public :: boolean," +
					 "                     tags_array := :tags_array :: varchar [])";
		
		final Meme savedMeme = namedJdbcTemplate.queryForObject(sql, getParams(meme), Meme.DEF_ROW_MAPPER);
		meme.setId(savedMeme.getId());
		meme.setTags(savedMeme.getTags());
	}
	
	@Override
	public Optional<Meme> findById(int memeId) {
		final String sql = "select * from meme where id = :meme_id";
		try {
			Map<String, Object> params = Collections.singletonMap("meme_id", memeId);
			return Optional.of(namedJdbcTemplate.queryForObject(sql, params, Meme.DEF_ROW_MAPPER));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
}
