package net.lantrack.module.sys.service.impl;

import net.lantrack.framework.common.entity.MapEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.framework.common.utils.Query;

import net.lantrack.module.sys.dao.SysDictDao;
import net.lantrack.module.sys.entity.SysDictEntity;
import net.lantrack.module.sys.service.SysDictService;

/**
 *@Description
 *@Author ldahuzi
 *@Date 2019-11-17 19:30:34
 */
@Service("sysDictService")
public class SysDictServiceImpl extends ServiceImpl<SysDictDao, SysDictEntity> implements SysDictService {

    @Override
    public String getDictValue(String type, String key) {
        return this.baseMapper.getDictValue(type, key);
    }

    @Override
    public  List<MapEntity> getDictSelect(String type) {
        List<SysDictEntity> list = this.baseMapper.selectList(
                new QueryWrapper<SysDictEntity>()
                        .eq(StringUtils.isNoneBlank(type),"d_type",type)
                        .orderByAsc("d_sort")
        );
        List<MapEntity> typeList  = new ArrayList<>(list.size());
        for(SysDictEntity dict:list){
            MapEntity entity = new MapEntity(dict.getDKey(),dict.getDVal());
            typeList.add(entity);
        }

        return typeList;
    }



    @Override
    public  List<MapEntity> getDictKind() {
        List<SysDictEntity> list = this.baseMapper.selectList(new QueryWrapper<SysDictEntity>());
        Map<String,String> map = new HashMap<>();
        for(SysDictEntity dict:list){
            if(!map.containsKey(dict.getDType())){
                map.put(dict.getDType(),dict.getDLable());
            }
        }
        List<MapEntity> typeList  = new ArrayList<>(list.size());
        for(Map.Entry<String,String> entry:map.entrySet()){
            MapEntity entity = new MapEntity(entry.getKey(),entry.getValue());
            typeList.add(entity);
        }
        return typeList;
    }



    @Override
    public PageEntity queryPage(Map<String, Object> params) {
        Object dictType = params.get("dictType");
        IPage<SysDictEntity> page = this.page(
                new Query<SysDictEntity>().getPage(params),
                new QueryWrapper<SysDictEntity>()
                    .eq(dictType!=null&&!"d_type".equals(dictType),"",dictType)
        );
        return new PageEntity(page);
    }

}
