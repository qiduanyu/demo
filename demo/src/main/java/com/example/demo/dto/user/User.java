package com.example.demo.dto.user;

import com.example.demo.common.group.Edit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author 75412
 * 用户实体类
 */
@ApiModel(value = "User",description = "人员信息")
@Data
public class User implements Serializable{

    @ApiModelProperty(value = "用户id")
    @NotNull(
            groups = {Edit.class},
            message = "用户ID不能为空"
    )
    private Integer id;


}
