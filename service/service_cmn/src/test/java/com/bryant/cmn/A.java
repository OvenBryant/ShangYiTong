package com.bryant.cmn;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class A implements Serializable {

    @ExcelProperty("用户id")
    private Integer id;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("颜色")
    private String color;
}
