package com.star.module.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.star.common.CommonConstants;
import com.star.common.ErrorCodeEnum;
import com.star.common.ServiceException;
import com.star.module.operation.dao.TagsMapper;
import com.star.module.operation.entity.Tags;
import com.star.module.operation.service.ITagsService;
import com.star.module.operation.util.ListUtils;
import com.star.module.user.vo.TagsVo;
import com.star.util.SnowflakeId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangrc <1538618608@qq.com>
 * @since 2020-12-01
 */
@Service
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements ITagsService {

    @Autowired
    private TagsMapper tagsMapper;
    @Autowired
    private ListUtils listUtils;

    @Override
    public List<TagsVo> tagsList() {
        QueryWrapper<Tags> wrapper = new QueryWrapper<>();
        List<Tags> tagsList = tagsMapper.selectList(wrapper);
        if(tagsList.size()>0){
            List<TagsVo> tagsVoList = new ArrayList<>();
            listUtils.copyList(tagsList, tagsVoList, TagsVo.class);
            return tagsVoList;
        }
        return null;
    }

    @Override
    public void addTags(String name) {
        QueryWrapper<Tags> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Tags::getName, name);
        Tags tags = tagsMapper.selectOne(queryWrapper);
        if(tags != null){
            throw new ServiceException(ErrorCodeEnum.ERROR_200001.getCode(),"同名标签已存在");
        }
        Tags tagsNew = new Tags();
        tagsNew.setName(name);
        tagsNew.setId(SnowflakeId.getInstance().nextId());
        LocalDateTime localDateTimeOfNow = LocalDateTime.now(ZoneId.of(CommonConstants.ZONEID_SHANGHAI));
        tagsNew.setAddTime(localDateTimeOfNow);
        tagsMapper.insert(tagsNew);
    }
}
