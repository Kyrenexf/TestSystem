package com.Dao.UserDaoImpl;

import com.Dao.UserDao;
import com.Entity.User;
import com.Util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class UserDaoimpl implements UserDao {
    //使用JDBCTemplate工具类，目的与数据库完成链接
    JdbcTemplate jdbcTemplate=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public User queryByUserName(String username) {
        //声明sql
        //调用sql，并返回数据
        String sql="SELECT * FROM user";
        BeanPropertyRowMapper<User> rowMapper=new BeanPropertyRowMapper<User>(User.class);
        List<User> users= jdbcTemplate.query(sql,rowMapper);
        for (User user : users) {
            System.out.println(user);
            System.out.println(username);
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    @Override
    public void insert(User user) {
        //声明sql
        //调用sql，并返回数据
      String sql="INSERT into user(username,password,name,telephone,sex,age,role ) VALUES(?,?,?,?,?,?,?);";
      jdbcTemplate.update(sql,user.getUsername(),user.getPassword(),user.getName(),user.getTelephone(),user.getSex(),user.getAge(),user.getRole());
    }
}

