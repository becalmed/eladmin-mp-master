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
@TableName("resource_requirements")
public class ResourceRequirements implements Serializable {

    @TableId(value = "id" ,type = IdType.AUTO)
    @ApiModelProperty(value = "资源编号")
    private Integer id;

    @TableField(value = "name")
    @NotBlank
    @ApiModelProperty(value = "资源名称")
    private String name;

    @TableField(value = "type")
    @NotBlank
    @ApiModelProperty(value = "资源类型")
    private String type;

    public void copy(ResourceRequirements source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
