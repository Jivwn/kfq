package com.zkkj.kfq.mapper;

import com.zkkj.kfq.entity.ShapeChangeModel;
import com.zkkj.kfq.entity.ShapeModel;
import com.zkkj.kfq.entity.TimeModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface ShapeModelDao {

    //插入矢量数据
    void insertShapeModel(List<ShapeModel> shapeModels);

    //插入变化的矢量数据
    void insertShapeChangeModel(List<ShapeChangeModel> shapeChangeModels);

    //查询类别
    List<String> listTypeName();

    List<String> listTypeChangeName();

    //查询起始截止时间
    List<TimeModel> listStartAndEndTime(String typeName);
    List<TimeModel> listStartAndEndTimeChange(String typeName);

//    List<ShapeModel> listShapeModel(Date date,String typeName);
    //通过类型和起始时间得到截止时间
    List<Date> listEndTimeGet(String typeName,Date startTime);
    List<Date> listEndTimeGetChange(String typeName,Date startTime);

    //通过类别、起始、截止日期查询 非变化表矢量
    List<ShapeModel> listShapeModel(String typeName,Date startTime,Date endTime);

    //通过类别、起始、截止日期查询 变化表矢量
    List<ShapeChangeModel> listShapeChangeModel(Date startTime,Date endTime,String typeName);

    //通过类别、起始、截止日期查询变化表的相关信息（面积、变化原因）
    List<ShapeChangeModel> listChangeChart(Date startTime,Date endTime,String typeName);

    //空间查询得到变化表中的信息
    List<ShapeChangeModel> listChangeIntersect(String wktGeom,Date startTime,Date endTime);

    //空间查询得到表中信息
    List<ShapeModel> listShpIntersect(String wktGeom,Date startTime,String  typeName);
}
