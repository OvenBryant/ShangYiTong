package com.bryant.cmn;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.sun.prism.GraphicsPipeline;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class AListener extends AnalysisEventListener<A> {

    /**
     * 一行一行读取,从第二行开始读取,第一行不读取
     * @param a
     * @param analysisContext
     */
    @Override
    public void invoke(A a, AnalysisContext analysisContext) {
        System.out.println("读excel数据："+a.toString());
    }

    /**
     * 读取完成之后,就输出这句话
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("读完了....");
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+ headMap);
    }
}
