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
package me.zhengjie.modules.system.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import java.sql.Timestamp;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
* @description /
* @author fjw
* @date 2024-05-30
**/
@Data
@TableName("sys_activity")
public class SysActivity implements Serializable {

    @TableId(value = "activity_id", type = IdType.AUTO)
    @ApiModelProperty(value = "活动编号")
    private Integer activityId;

    @NotBlank
    @ApiModelProperty(value = "活动名称")
    private String activityName;

    @NotBlank
    @ApiModelProperty(value = "活动类型")
    private String activityProgress;

    @NotNull
    @ApiModelProperty(value = "创建者")
    private Integer createBy;

    @ApiModelProperty(value = "更新者")
    private Integer updateBy;

    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    @NotBlank
    @ApiModelProperty(value = "开始日期")
    private String startTime;

    @NotBlank
    @ApiModelProperty(value = "开始时刻")
    private String startHour;

    @NotBlank
    @ApiModelProperty(value = "结束日期")
    private String endTime;

    @NotBlank
    @ApiModelProperty(value = "结束时刻")
    private String endHour;

    @ApiModelProperty(value = "参与者列表")
    private String participants;

    @ApiModelProperty(value = "资源需求列表")
    private String resourceRequirements;

    public void copy(SysActivity source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
