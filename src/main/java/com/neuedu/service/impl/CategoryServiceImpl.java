package com.neuedu.service.impl;

import com.google.common.collect.Sets;
import com.neuedu.commen.ServerResponse;
import com.neuedu.dao.TypeMapper;
import com.neuedu.entity.Type;
import com.neuedu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    TypeMapper typeMapper;

    @Override
    public ServerResponse get_category(Integer id) {

        // step1:非空校验
        if (id==null){
            return ServerResponse.createServerResponseByError("参数不能为空");
        }
        // step2:根据id查询类别

        Type type=typeMapper.selectByPrimaryKey(id);
        if (type==null){
            return ServerResponse.createServerResponseByError("类别不存在");
        }
        // step3:查询子类别
        List<Type> typeList=typeMapper.findChildCategory(id);

        // step4:返回结果
        return ServerResponse.createServerResponseBySucess(null,typeList);
    }

    @Override
    public ServerResponse add_category(Integer parentid, String name) {

        // step1:非空校验
        if(parentid==null||parentid.equals("")){
            return ServerResponse.createServerResponseByError("参数不能为空");
        }
        // step2:添加节点
        Type type=new Type();
        type.setName(name);
        type.setParentid(parentid);
        type.setStatus(1);
        int result=typeMapper.insert(type);
        if (result>0){
            return ServerResponse.createServerResponseBySucess();
        }
        // step3:返回结果
        return ServerResponse.createServerResponseByError("添加失败");
    }

    @Override
    public ServerResponse set_category(Integer id, String name) {

        // step1:非空校验
        if(id==null||id.equals("")){
            return ServerResponse.createServerResponseByError("id不能为空");
        }
        if(name==null||name.equals("")){
            return ServerResponse.createServerResponseByError("名称不能为空");
        }
        // step2:根据id查询
        Type type=typeMapper.selectByPrimaryKey(id);
        if (id==null){
            return ServerResponse.createServerResponseByError("类别不存在");
        }
        // step3:修改
        type.setName(name);
        int result=typeMapper.updateByPrimaryKey(type);
        if (result>0){
            return ServerResponse.createServerResponseBySucess();
        }
        // step4:返回结果
        return ServerResponse.createServerResponseByError("修改失败");
    }

    @Override
    public ServerResponse get_deep_category(Integer id) {

        // step1:参数的非空校验
        if (id==null){
            return ServerResponse.createServerResponseByError("id不能为空");
        }
        // step2:查询
        Set<Type> typeSet= Sets.newHashSet();
        typeSet=findAllChildCategory(typeSet,id);
        Set<Integer> integerSet=Sets.newHashSet();
        Iterator<Type> typeIterator=typeSet.iterator();
        while (typeIterator.hasNext()){
            Type type=typeIterator.next();
            integerSet.add(type.getId());
        }
        return ServerResponse.createServerResponseBySucess(null,integerSet);
    }

    private Set<Type> findAllChildCategory(Set<Type> typeSet,Integer id){
        Type type=typeMapper.selectByPrimaryKey(id);
        if (id!=null){
            typeSet.add(type);
        }
        //查找id下的子节点
        List<Type> typelist=typeMapper.findChildCategory(id);
        if (typelist!=null&&typelist.size()>0){
            for (Type type1:typelist){
                findAllChildCategory(typeSet,type1.getId());
            }
        }
        return typeSet;
    }
}
