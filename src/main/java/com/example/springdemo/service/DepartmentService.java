package com.example.springdemo.service;

import com.example.springdemo.entity.Department;
import com.example.springdemo.mapper.MarketMapper;
import com.example.springdemo.mapper.WarehouseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 李鑫豪
 * @created: 2021/05/25 22:56
 * @description:
 */
@Service
public class DepartmentService {

    public static final int MARKET = 1;     //超市
    public static final int WAREHOUSE = 2;  //仓库

    @Autowired
    private MarketMapper marketMapper;

    @Autowired
    private WarehouseMapper warehouseMapper;

    public List<Department> getAll(int whom) {
        if (whom == MARKET)
            return marketMapper.getAllMarket();
        else if (whom == WAREHOUSE)
            return warehouseMapper.getAllWarehouse();
        return null;
    }


    /**
     * 将部门信息打包成map，并放进List，每个部门信息一个map
     *
     * @param whom 角色，是超市还是仓库
     * @return list 打包好的信息
     */
    public List<Map<String, String>> getDepartmentMapList(int whom) {
        List<Department> departmentList = null;
        if (whom == MARKET) {
            departmentList = marketMapper.getAllMarket();
        } else if (whom == WAREHOUSE) {
            departmentList = warehouseMapper.getAllWarehouse();
        }

        List<Map<String, String>> list = new ArrayList<>();

        if (departmentList != null) {
            for (Department x : departmentList) {
                Map<String, String> mp = new HashMap<>();
                mp.put("id", x.getId());
                mp.put("name", x.getName());
                mp.put("address", x.getAddress());
                mp.put("tel", x.getTel());
                mp.put("contacts", x.getContacts());
                list.add(mp);
            }
        }
        return list;
    }


    /**
     * 制作超市ID 映射 部门信息的Map
     *
     * @param whom 角色，是超市还是仓库
     * @return 映射信息
     */
    public Map<String, Department> getDepartmentMap(int whom) {
        List<Department> departmentList = null;
        if (whom == MARKET) {
            departmentList = marketMapper.getAllMarket();
        } else if (whom == WAREHOUSE) {
            departmentList = warehouseMapper.getAllWarehouse();
        }

        Map<String, Department> mp = new HashMap<>();
        if (departmentList != null) {
            for (Department x : departmentList) {
                mp.put(x.getId(), x);
            }
        }
        return mp;
    }

    /**
     * 从数据路查新某个部门的信息
     *
     * @param id   部门ID
     * @param whom 角色，是超市还是仓库
     * @return 信息
     */
    public Department getDepartmentByID(String id, int whom) {
        if (whom == MARKET)
            return marketMapper.getMarketByID(id);
        else if (whom == WAREHOUSE)
            return warehouseMapper.getWarehouseByID(id);
        return null;
    }

    /**
     * 返回有多少部门提交了信息
     *
     * @param whom 什么部门，仓库/超市
     * @return int
     */
    public int getSubmitCount(int whom) {
        if (whom == MARKET)
            return marketMapper.getApplyCount();
        else if (whom == WAREHOUSE)
            return warehouseMapper.getSupplyCount();
        return 0;
    }

    /**
     * 返回某种类型的部门数目
     *
     * @param whom 角色，是超市还是仓库
     * @return 数量
     */
    public int getDepartmentCount(int whom) {
        if (whom == MARKET) {
            return marketMapper.getMarketCount();
        } else if (whom == WAREHOUSE) {
            return warehouseMapper.getWarehouseCount();
        }
        return 0;
    }

    /**
     * 更新数据库中已经存在的部门信息
     *
     * @param department 新的部门信息，id应不变
     * @param whom       是超市还是仓库
     */
    public void updateDepartment(Department department, int whom) {
        if (whom == MARKET) {
            marketMapper.updateMarket(department);
        } else if (whom == WAREHOUSE) {
            warehouseMapper.updateWarehouse(department);
        }
    }

    /**
     * 向数据库中插入新的部门信息
     *
     * @param department 新的部门信息，id应不变
     * @param whom       是超市还是仓库
     */
    public void insertDepartment(Department department, int whom) {
        if (whom == MARKET) {
            marketMapper.insertMarket(department);
        } else if (whom == WAREHOUSE) {
            warehouseMapper.insertWarehouse(department);
        }
    }

    /**
     * 从出数据库中删除某个部门信息
     *
     * @param id   仓库/超市ID
     * @param whom 角色，仓库还是超市
     */
    public void delDepartment(String id, int whom) {
        if (whom == MARKET) {
            marketMapper.delMarket(id);
        } else if (whom == WAREHOUSE) {
            warehouseMapper.delWarehouse(id);
        }
    }

    /**
     * 获取已经提交商品申请的超市的信息
     *
     * @return List
     */
    public List<Department> getApplyMarketList() {
        List<Department> ans = new ArrayList<>();
        List<Department> marketList = marketMapper.getAllMarket();
        for (Department d : marketList) {
            if (d.getRelativeList() != null && !d.getRelativeList().isEmpty()) {
                ans.add(d);
            }
        }
        return ans;
    }

    /**
     * 获取已经提交库存信息的超市的信息
     *
     * @return List
     */
    public List<Department> getSupplyWarehouseList() {
        List<Department> ans = new ArrayList<>();
        List<Department> warehouseList = warehouseMapper.getAllWarehouse();
        for (Department d : warehouseList) {
            if (d.getRelativeList() != null && !d.getRelativeList().isEmpty()) {
                ans.add(d);
            }
        }
        return ans;
    }


    /**
     * 替换某个超市或仓库的提交信息
     *
     * @param list 新的信息
     * @param id   超市或者仓库的ID
     * @param whom 是超市还是仓库
     */
    public void replaceSubmitList(List<Map<String, String>> list, String id, int whom) {
        if (whom == MARKET) {
            marketMapper.deleteApplyOfMarket(id);
            marketMapper.insertApplyList(list);
        } else if (whom == WAREHOUSE) {
            warehouseMapper.deleteSupplyOfWarehouse(id);
            warehouseMapper.insertSupplyList(list);
        }
    }
}
