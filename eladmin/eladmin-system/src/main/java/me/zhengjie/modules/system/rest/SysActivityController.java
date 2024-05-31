/*
 *  Copyright 2019-2020 Zheng Jie
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
package me.zhengjie.modules.system.rest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.system.domain.SysActivity;
import me.zhengjie.modules.system.domain.vo.ActivityQueryCriteria;
import me.zhengjie.modules.system.service.ResourceRequirementsService;
import me.zhengjie.modules.system.service.SysActivityService;
import me.zhengjie.utils.PageResult;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Set;

/**
* @author Zheng Jie
* @date 2019-03-29
*/
@RestController
@RequiredArgsConstructor
@Api(tags = "系统：活动管理")
@RequestMapping("/api/activity")
public class SysActivityController {


    private final SysActivityService sysActivityService;
    private final ResourceRequirementsService resourceRequirementsService;
    private static final String ENTITY_NAME = "activity";

   

    @ApiOperation("查询活动")
    @GetMapping
    public ResponseEntity<PageResult<SysActivity>> querySysActivity(ActivityQueryCriteria criteria, Page<SysActivity> page){
        return new ResponseEntity<>(sysActivityService.queryAll(criteria, page),HttpStatus.OK);
    }

    @Log("新增活动")
    @ApiOperation("新增活动")
    @PostMapping
    public ResponseEntity<Object> createSysActivity(@Validated @RequestBody SysActivity resources){
        int rid = sysActivityService.random(resources.getRtypeTemp(),resources.getStartTime(),resources.getEndTime());
        if (rid==-1) {
            throw new BadRequestException("该时间段该类所有资源已被使用！");
        }
        resources.setRid(rid);
        resources.setCreateTime(new Date());
        sysActivityService.save(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改活动")
    @ApiOperation("修改活动")
    @PutMapping
    public ResponseEntity<Object> updateSysActivity(@RequestBody SysActivity resources){
        resources.setUpdateTime(new Date());
        SysActivity old =sysActivityService.getById(resources.getActivityId());
        int oldRid = -1;
        if(old.getRid()!=null){
            oldRid = old.getRid();
            old.setRid(-1);
            sysActivityService.updateById(old);
        }
        int rid = sysActivityService.random(resources.getRtypeTemp(),resources.getStartTime(),resources.getEndTime());
        if (rid==-1) {
            if(oldRid!=-1){
                old.setRid(oldRid);
                sysActivityService.updateById(old);
            }
            throw new BadRequestException("该时间段该类所有资源已被使用！");
                    }
                    resources.setRid(rid);
                    sysActivityService.updateById(resources);
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

@Log("删除活动")
@ApiOperation("删除活动")
@DeleteMapping
public ResponseEntity<Object> deleteSysActivity(@RequestBody Set<Long> ids){
        for(Long id : ids){
        sysActivityService.removeById(id);
        }
        return new ResponseEntity<>(HttpStatus.OK);
        }
}