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
package me.zhengjie.modules.system.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.SysActivity;
import me.zhengjie.modules.system.service.SysActivityService;
import me.zhengjie.modules.system.domain.vo.SysActivityQueryCriteria;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.zhengjie.utils.PageResult;

/**
* @author fjw
* @date 2024-05-30
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "api.activity管理")
@RequestMapping("/api/sysActivity")
public class SysActivityController {

    private final SysActivityService sysActivityService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('sysActivity:list')")
    public void exportSysActivity(HttpServletResponse response, SysActivityQueryCriteria criteria) throws IOException {
        sysActivityService.download(sysActivityService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api.activity")
    @ApiOperation("查询api.activity")
    @PreAuthorize("@el.check('sysActivity:list')")
    public ResponseEntity<PageResult<SysActivity>> querySysActivity(SysActivityQueryCriteria criteria, Page<Object> page){
        return new ResponseEntity<>(sysActivityService.queryAll(criteria,page),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api.activity")
    @ApiOperation("新增api.activity")
    @PreAuthorize("@el.check('sysActivity:add')")
    public ResponseEntity<Object> createSysActivity(@Validated @RequestBody SysActivity resources){
        sysActivityService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api.activity")
    @ApiOperation("修改api.activity")
    @PreAuthorize("@el.check('sysActivity:edit')")
    public ResponseEntity<Object> updateSysActivity(@Validated @RequestBody SysActivity resources){
        sysActivityService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除api.activity")
    @ApiOperation("删除api.activity")
    @PreAuthorize("@el.check('sysActivity:del')")
    public ResponseEntity<Object> deleteSysActivity(@RequestBody List<Integer> ids) {
        sysActivityService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}