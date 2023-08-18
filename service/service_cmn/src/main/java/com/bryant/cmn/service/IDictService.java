package com.bryant.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bryant.yygh.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IDictService extends IService<Dict> {
    List<Dict> findChildData(Long id);

    void exportDictData(HttpServletResponse response);

    void uploadData(MultipartFile file);
}
