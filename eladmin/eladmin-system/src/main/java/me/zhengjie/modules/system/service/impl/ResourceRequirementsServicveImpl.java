package me.zhengjie.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.domain.ResourceRequirements;
import me.zhengjie.modules.system.domain.SysActivity;
import me.zhengjie.modules.system.domain.vo.ActivityQueryCriteria;
import me.zhengjie.modules.system.mapper.ResourceRequirementsMapper;
import me.zhengjie.modules.system.mapper.SysActivityMapper;
import me.zhengjie.modules.system.service.ResourceRequirementsService;
import me.zhengjie.modules.system.service.SysActivityService;
import me.zhengjie.utils.PageResult;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "resourceRequirements")
public class ResourceRequirementsServicveImpl extends ServiceImpl<ResourceRequirementsMapper, ResourceRequirements> implements ResourceRequirementsService {


}
