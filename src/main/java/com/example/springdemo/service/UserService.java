package com.example.springdemo.service;

import com.example.springdemo.entity.Department;
import com.example.springdemo.entity.User;
import com.example.springdemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @created: 2021/05/25 17:29
 * @description:
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    DepartmentService departmentService;

    /**
     * 获取所有用户信息
     * @return 所有用户信息
     */
    public List<User> getAllUser() {
        Map<String, Department> marketMap = departmentService.getDepartmentMap(DepartmentService.MARKET);
        Map<String, Department> warehouseMap = departmentService.getDepartmentMap(DepartmentService.WAREHOUSE);
        List<User> list = userMapper.getAllUser();

        for (User user : list) {
            if (user.getType().equals("超市")) {
                user.setDepartment(marketMap.get(user.getId()).getName());
            } else if (user.getType().equals("仓库")) {
                user.setDepartment(warehouseMap.get(user.getId()).getName());
            }
        }
        return list;
    }

    /**
     * 从数据库查询对应ID的用户信息
     * @param id 所查寻用户信息的ID
     * @return 用户信息
     */
    public User getUserByID(String id) {
        return userMapper.getUserByID(id);
    }

    /**
     * 向数据库中插入用户信息
     * @param user 用户信息
     */
    public void insertUser(User user){
        userMapper.insertUser(user);
    }

    /**
     * 从数据库中删除一个用户信息
     * @param id 用户ID
     */
    public void delUser(String id){
        userMapper.delUser(id);
    }

    /**
     * 更新数据库中某个用户的信息
     * 如果新信息password为空，则默认为不修改密码
     * @param user 新的用户信息，其中ID应与之前保持一致
     */
    public void updateUser(User user){
        User older = userMapper.getUserByID(user.getId());
        if(user.getPassword() == null || user.getPassword().isEmpty())
            user.setPassword(older.getPassword());
        userMapper.updateUser(user);
    }

}
