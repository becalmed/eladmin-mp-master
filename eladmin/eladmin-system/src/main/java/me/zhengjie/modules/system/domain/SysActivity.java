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
package me.zhengjie.modules.system.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
* @website https://eladmin.vip
* @description /
* @author fjw
* @date 2024-05-24
**/
@Getter
@Setter
@Data
@TableName("sys_activity")
public class SysActivity implements Serializable {

    @TableId(value = "activity_id" ,type = IdType.AUTO)
    @ApiModelProperty(value = "活动编号")
    private Integer activityId;

    @TableField(exist = false)
    @ApiModelProperty(value = "临时资源类型")
    private Integer rtypeTemp;

    @TableField(value = "activity_name")
    @NotBlank
    @ApiModelProperty(value = "活动名称")
    private String activityName;

    @TableField(value = "activity_progress")
    @NotBlank
    @ApiModelProperty(value = "活动类型")
    private String activityProgress;

    @TableField(value = "create_by")
    @NotNull
    @ApiModelProperty(value = "创建者")
    private Integer createBy;

    @TableField(value = "update_by")
    @ApiModelProperty(value = "更新者")
    private Integer updateBy;

    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @TableField(value = "start_time")
    @ApiModelProperty(value = "开始日期")
    private Date startTime;



    @TableField(value = "end_time")
    @NotBlank
    @ApiModelProperty(value = "结束日期")
    private Date endTime;


    @TableField(value = "participants")
    @ApiModelProperty(value = "参与者列表")
    private String participants;

    @TableField(value = "rid")
    @ApiModelProperty(value = "资源需求列表")
    private Integer rid;

    public void copy(SysActivity source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
