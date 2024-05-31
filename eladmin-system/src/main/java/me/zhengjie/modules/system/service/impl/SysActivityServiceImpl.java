/*
*  Copyright 2019-2023 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.domain.SysActivity;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhengjie.modules.system.service.SysActivityService;
import me.zhengjie.modules.system.domain.vo.SysActivityQueryCriteria;
import me.zhengjie.modules.system.mapper.SysActivityMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import me.zhengjie.utils.PageUtil;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import me.zhengjie.utils.PageResult;

/**
* @description 服务实现
* @author fjw
* @date 2024-05-30
**/
@Service
@RequiredArgsConstructor
public class SysActivityServiceImpl extends ServiceImpl<SysActivityMapper, SysActivity> implements SysActivityService {

    private final SysActivityMapper sysActivityMapper;

    @Override
    public PageResult<SysActivity> queryAll(SysActivityQueryCriteria criteria, Page<Object> page){
        return PageUtil.toPage(sysActivityMapper.findAll(criteria, page));
    }

    @Override
    public List<SysActivity> queryAll(SysActivityQueryCriteria criteria){
        return sysActivityMapper.findAll(criteria);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SysActivity resources) {
        save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysActivity resources) {
        SysActivity sysActivity = getById(resources.getActivityId());
        sysActivity.copy(resources);
        saveOrUpdate(sysActivity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<Integer> ids) {
        removeBatchByIds(ids);
    }

    @Override
    public void download(List<SysActivity> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SysActivity sysActivity : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("活动名称", sysActivity.getActivityName());
            map.put("活动类型", sysActivity.getActivityProgress());
            map.put("创建者", sysActivity.getCreateBy());
            map.put("更新者", sysActivity.getUpdateBy());
            map.put("创建时间", sysActivity.getCreateTime());
            map.put("更新时间", sysActivity.getUpdateTime());
            map.put("开始日期", sysActivity.getStartTime());
            map.put("开始时刻", sysActivity.getStartHour());
            map.put("结束日期", sysActivity.getEndTime());
            map.put("结束时刻", sysActivity.getEndHour());
            map.put("参与者列表", sysActivity.getParticipants());
            map.put("资源需求列表", sysActivity.getResourceRequirements());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}