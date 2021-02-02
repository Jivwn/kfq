package com.zkkj.kfq.service;

import com.zkkj.kfq.entity.ShapeChangeModel;
import com.zkkj.kfq.entity.ShapeModel;
import java.util.*;

public interface GeomChangeService {

//    List<ShapeModel>  listShapeModel(String name, Date date);

    //通过类型和起始时间和截止时间得到kfq_geom表矢量信息
    List<ShapeModel> listShapeModel(String typeName,Date startTime,Date endTime);
    //类型、起始时间和截止时间得到kfq_geom_chenge表的矢量信息
    List<ShapeChangeModel> listChangeModel(String changeType,String name,Date startTime,Date endTime,String region);

    //类别名称
    List<String> listTypeName(String type) throws Exception;

    //起始截止时间
    Map<String,Set<String>> listStartAndEndTime(String TypeName);

    //变化信息
    List<ShapeChangeModel> listChangeInfo(String typeName,Date startTime,Date endTime);

     //图表信息
    List<ShapeChangeModel> listChangeChart(String typeName,Date startTime,Date endTime);

    //空间查询
    Map<String,List> listShapeIntersect(String wktGeom,Date startTime,String  typeName);

    //截止时间信息
    Collection<String> listEndTimeGet(String typeName,Date startTime);

}
