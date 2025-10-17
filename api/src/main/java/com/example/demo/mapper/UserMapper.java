package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.example.demo.entity.UserEntity;

@Mapper
public interface UserMapper {
    
    @Select("SELECT * FROM user WHERE username = #{username} AND password = #{password}")
    public UserEntity login(String username, String password);

    @SelectProvider(type = UserSQLProvider.class, method = "find")
	public List<UserEntity> find(Map<String, Object> condition);

	@Select({
		"SELECT * FROM `user`", 
		"WHERE `deletedAt` IS NULL and `id`=#{id}"
	})
	public UserEntity findById(Integer id);

	@Insert(
			"INSERT INTO `user`(`username`, `password`, `avatar`, `registeredAt`) " + 
			"VALUE(#{username}, #{password}, #{avatar}, #{registeredAt})"
	)
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn="id")
	public Integer create(UserEntity user);

	@Update({
		"UPDATE `user`",
		"SET `name`=#{name}, `password`=#{password}, `avatar`=#{avatar}",
		"WHERE `id`=#{id}"
	})
	public Integer update(UserEntity user);

	// @Delete({
	// 	"DELETE", 
	// 	"FROM `user`",
	// 	"WHERE `id`=#{id}"
	// })
	@Update({
		"UPDATE `user`",
		"SET `deletedAt`=#{deletedAt}",
		"WHERE `id`=#{id}"
	})
	public Integer remove(UserEntity user);

}
