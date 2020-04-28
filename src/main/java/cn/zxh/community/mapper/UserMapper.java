package cn.zxh.community.mapper;

import cn.zxh.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    @Insert("INSERT into user_com (name,accountID,token,gmt_creat,gmt_modified) values (#{name},#{accountID},#{token},#{gmtCreat},#{gmtModified})")
    void insert(User user);

}
