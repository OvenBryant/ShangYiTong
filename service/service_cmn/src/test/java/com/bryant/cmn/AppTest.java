package com.bryant.cmn;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.listener.ReadListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bryant.cmn.service.IDictService;
import com.bryant.yygh.model.cmn.Dict;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */


//@RunWith(SpringRunner.class)
//@SpringBootTest
public class AppTest {


    @Autowired
    private IDictService dictService;


    /**
     * 写操作
     */
    @Test
    public void writeExcel() {

        String path = "src/main/resources/file.xlsx";
        List<A> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            A a = new A(i,"name"+i,"红"+1);
             list.add(a);
        }

        EasyExcel.write(path, A.class).sheet("首页").doWrite(list);

    }

    // 读操作
    @Test
    public void readExcel(){

        String path = "src/main/resources/file.xlsx";

        EasyExcel.read(path,A.class,new AListener()).sheet().doRead();

    }

}




