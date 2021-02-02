package com.zkkj.kfq.controller;

import com.zkkj.kfq.entity.ShapeChangeModel;
import com.zkkj.kfq.service.GeomChangeService;
import com.zkkj.kfq.service.ShapeReadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/geom")
@Api("矢量读写api")
public class KfqGeomController {




    @Autowired
    ShapeReadService shapeReadService;



    @PostMapping("/change/upload")
    @ApiOperation("上传变化矢量文件")
    public Object uploadChangeShapeFile(@RequestParam("filePath") String  filePath ) {
        try {
            shapeReadService.uploadChangeShapeFile( filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/normal/upload")
    @ApiOperation("上传矢量文件")
    public Object uploadShapeFile(@RequestParam("filePath") String  filePath ) {
        try {
            shapeReadService.uploadShapeFile( filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
