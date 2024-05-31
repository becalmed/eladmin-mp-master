package me.zhengjie.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.domain.Menu;
import me.zhengjie.modules.system.domain.ResourceRequirements;
import me.zhengjie.modules.system.domain.SysActivity;
import me.zhengjie.modules.system.domain.vo.ActivityQueryCriteria;
import me.zhengjie.modules.system.mapper.MenuMapper;
import me.zhengjie.modules.system.mapper.SysActivityMapper;
import me.zhengjie.modules.system.service.MenuService;
import me.zhengjie.modules.system.service.ResourceRequirementsService;
import me.zhengjie.modules.system.service.SysActivityService;
import me.zhengjie.utils.PageResult;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "sysActivity")
public class SysActivityServicveImpl extends ServiceImpl<SysActivityMapper, SysActivity> implements SysActivityService {

    @Autowired
    private ResourceRequirementsService resourceRequirementsService;


    @Override
    public PageResult<SysActivity> queryAll(ActivityQueryCriteria criteria, Page<SysActivity> page) {
        LambdaQueryWrapper<SysActivity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!StringUtils.isEmpty(criteria.getActivityName()),SysActivity::getActivityName,criteria.getActivityName());
        queryWrapper.eq(!StringUtils.isEmpty(criteria.getActivityProgress()),SysActivity::getActivityProgress,criteria.getActivityProgress());
        queryWrapper.eq(criteria.getCreateBy()!=null,SysActivity::getActivityName,criteria.getActivityName());
        Page<SysActivity> ipage = this.baseMapper.selectPage(page,queryWrapper);

        return PageUtil.toPage(ipage.getRecords(), ipage.getTotal());
    }

    public boolean verification(Integer rid, Date startTime, Date endTime) {
        LambdaQueryWrapper<SysActivity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysActivity::getRid,rid);
        queryWrapper.ge(SysActivity::getEndTime,startTime);
        queryWrapper.le(SysActivity::getStartTime,endTime);
        List<SysActivity> sysActivities = this.baseMapper.selectList(queryWrapper);
        return !sysActivities.isEmpty();

    }

    @Override
    public Integer random(Integer rtype, Date startTime, Date endTime) {
        LambdaQueryWrapper<ResourceRequirements> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ResourceRequirements::getType,rtype);
        List<ResourceRequirements> rlist = resourceRequirementsService.list(queryWrapper);

        for(ResourceRequirements rr: rlist){
            if(!verification(rr.getId(),startTime,endTime)){
                return rr.getId();
            }
        }
        return -1;
    }
}
