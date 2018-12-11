package com.neuedu.service;

import com.neuedu.commen.ServerResponse;

public interface CategoryService {

    // 获取品类子节点（平级）
    ServerResponse get_category(Integer id);
    // 增加节点
    ServerResponse add_category(Integer parentid,String name);
    // 修改品类名字
    ServerResponse set_category(Integer id,String name);
    // 获取当前分类id及递归子节点
    ServerResponse get_deep_category(Integer id);

}
