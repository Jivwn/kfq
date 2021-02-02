package com.zkkj.kfq.controller;


import com.zkkj.kfq.entity.ShapeChangeModel;
import com.zkkj.kfq.entity.ShapeModel;
import com.zkkj.kfq.service.GeomChangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/geomselect")
@Api("矢量查询api")
public class KfqSelectController {

    @Autowired
    private GeomChangeService geomChangeService;

    private final DateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd");


    @GetMapping("/return/typename")
    @ApiOperation("产品类别信息")
    public Object getListTypeName(){
        return geomChangeService.listTypeName();
    }


    @PostMapping("/return/geom")
    @ApiOperation("矢量信息")
    public Object getGeomInfo(@RequestParam("type") String typeName,
                              @RequestParam("start") String startTime,
                              @RequestParam("end") String endTime){
        try {
            List<ShapeModel> shapeModels =
                    geomChangeService.listShapeModel(typeName, dateFormat.parse(startTime), dateFormat.parse(endTime));
            List<ShapeChangeModel> changeModels =
                    geomChangeService.listChangeInfo(typeName, dateFormat.parse(startTime), dateFormat.parse(endTime));
            List<String> listGeom = new ArrayList<>();
            List<String> listGeom1 = new ArrayList<>();
            Map<String,List<String>> mapGeom = new HashMap<>();
            for(ShapeModel shapeModel : shapeModels){
                listGeom.add(shapeModel.getThe_geom());
            }
            mapGeom.put("normal",listGeom);
            for (ShapeChangeModel shapeChangeModel : changeModels){
                listGeom1.add(shapeChangeModel.getThe_geom());
            }
            mapGeom.put("change",listGeom1);

            return mapGeom;
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/return/chart")
    @ApiOperation("图表信息")
    public Object getInfoChart(@RequestParam("type")String typeName,
                               @RequestParam("start")String startTime,
                               @RequestParam("end")String endTime){
        try {
            List<ShapeChangeModel> shapeChangeModels =
                    geomChangeService.listChangeChart(typeName, dateFormat.parse(startTime), dateFormat.parse(endTime));
            return shapeChangeModels;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }



    @PostMapping("/return/intersection")
    @ApiOperation("矢量面相交")
    public Object intersectGeomFromShp(@RequestParam("geom") String wktGeom,
                                       @RequestParam("start") String startTime,
                                       @RequestParam("end") String endTime){
        //格式是wkt格式数据
        try {
            Map<String, List> stringListMap =
                    geomChangeService.listShapeIntersect(wktGeom,dateFormat.parse(startTime),dateFormat.parse(endTime));
            return stringListMap;
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }
}

//    @PostMapping("/return/start")
//    @ApiOperation("起始时间")
//    public Object getListStartAndEndTime(@RequestParam("type") String typeName){
//        Map<String, Set<String>> stringListMap = geomChangeService.listStartAndEndTime(typeName);
//        return stringListMap;
//    }

//    @PostMapping("/return/end")
//    @ApiOperation("产品截止时间")
//    public Object getEndTime(@RequestParam("type") String typeName,
//                             @RequestParam("start") String startTime){
//        try {
//            Collection<String> strings = geomChangeService.listEndTimeGet(typeName, dateFormat.parse(startTime));
//            return strings;
//        }catch (ParseException e){
//            e.printStackTrace();
//        }
//        return null;
//    }




//    @GetMapping("/return/typeinfo")
//    @ApiOperation("类别信息、时间信息综合")
//    public Object getListInfo(){
//        Map<String, Object> map =new HashMap<>();
//        Set<String> set = geomChangeService.listTypeName();
//        for (String typeName : set){
//            Map<String, Set<String>> stringListMap = geomChangeService.listStartAndEndTime(typeName);
//            map.put(typeName,stringListMap);
//        }
//        return map;
//    }

//    @PostMapping("/return/geomchart")
//    @ApiOperation("矢量和图表信息")
//    public Object getGeomChartInfo(@RequestParam("type") String typeName,
//                                   @RequestParam("start") String startTime,
//                                   @RequestParam("end") String endTime){
//        try {
//            List<ShapeModel> shapeModels =
//                    geomChangeService.listShapeModel(typeName, dateFormat.parse(startTime), dateFormat.parse(endTime));
//            List<ShapeChangeModel> changeModels =
//                    geomChangeService.listChangeModel(null, typeName, dateFormat.parse(startTime), dateFormat.parse(endTime), null);
//            List<String> listGeom = new ArrayList<>();
//            List<String> listGeom1 = new ArrayList<>();
//            Map<String,List<String>> mapGeom = new HashMap<>();
//            for(ShapeModel shapeModel : shapeModels){
//                listGeom.add(shapeModel.getThe_geom());
//            }
//            mapGeom.put("normal",listGeom);
//            for (ShapeChangeModel shapeChangeModel : changeModels){
//                listGeom1.add(shapeChangeModel.getThe_geom());
//            }
//            mapGeom.put("change",listGeom1);
//            List<ShapeChangeModel> shapeChangeModels =
//                    geomChangeService.listChangeChart(typeName, dateFormat.parse(startTime), dateFormat.parse(endTime));
//            Map<String,Object> map = new HashMap<>();
//            map.put("矢量",mapGeom);
//            map.put("图表",shapeChangeModels);
//            return map;
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
