package com.zkkj.kfq.service.impl;

import com.zkkj.kfq.entity.ShapeChangeModel;
import com.zkkj.kfq.entity.ShapeModel;
import com.zkkj.kfq.entity.TimeModel;
import com.zkkj.kfq.mapper.ShapeModelDao;
import com.zkkj.kfq.service.GeomChangeService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class GeomChangeServiceImpl implements GeomChangeService {

    DateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    private ShapeModelDao shapeModelDao;


    /**
     *
     * @param changeType
     * @param name
     * @param startTime
     * @param endTime
     * @param region
     * @return
     */
    @Override
    public List<ShapeChangeModel> listChangeModel(String changeType, String name, Date startTime, Date endTime, String region)  {
        try {
            List<ShapeChangeModel> bhlx = shapeModelDao.listShapeChangeModel(startTime,endTime,name);
            return bhlx;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return  得到类别信息
     */
    @Override
    public Set<String> listTypeName() {
        List<String> list1 = shapeModelDao.listTypeChangeName();
        List<String> list = shapeModelDao.listTypeName();
        Set<String> set = new HashSet<>();
        for ( String s : list){
            set.add(s);
        }
        for(String s : list1){
            set.add(s);
        }
        return set;
    }

    /**
     * 通过类别名得到时间
     * @param typeName 类型
     * @return 得到起始、截止时间的集合
     */
    @Override
    public Map<String,Set<String>> listStartAndEndTime(String typeName) {
        List<TimeModel> timeModels = shapeModelDao.listStartAndEndTime(typeName);
        List<TimeModel> timeModels1 = shapeModelDao.listStartAndEndTimeChange(typeName);
        Map<String,Set<String>> map = new HashMap<>();
        Set<String> setStart = new HashSet<>();
        Set<String> setEnd = new HashSet<>();
        for(TimeModel timeModel : timeModels){
            String start = dateFormat.format(timeModel.getStart_time());
            String end = dateFormat.format(timeModel.getEnd_time());
            setStart.add(start);
            setEnd.add(end);
        }
        for (TimeModel timeModel : timeModels1){
            String start = dateFormat.format(timeModel.getStart_time());
            String end = dateFormat.format(timeModel.getEnd_time());
            setStart.add(start);
            setEnd.add(end);
        }
        map.put("start",setStart);
//        map.put("end",setEnd);
        return map;
    }

    /**
     *
     * @param typeName 类型
     * @param startTime 起始时间
     * @param endTime 截止时间
     * @return  返回kfq_geom_change的矢量信息
     */
    @Override
    public List<ShapeChangeModel> listChangeInfo(String typeName, Date startTime, Date endTime) {
        return shapeModelDao.listShapeChangeModel(startTime,endTime,typeName);
    }

    /**
     *
     * @param typeName  类别
     * @param startTime 起始时间
     * @param endTime 截止时间
     * @return  图表信息
     */
    @Override
    public List<ShapeChangeModel> listChangeChart(String typeName, Date startTime, Date endTime) {
        List<ShapeChangeModel> shapeChangeModels = shapeModelDao.listChangeChart(startTime, endTime, typeName);

        return shapeChangeModels;
    }


    /**
     *
     * @param wktGeom  wkt格式数据
     * @return 返回与之有空间相交的关系的数据信息
     */
    @Override
    public Map<String,List> listShapeIntersect(String wktGeom,Date startTime,Date endTime) {
        Map<String,List> map = new HashMap<>();
        List<ShapeModel> shapeModels = shapeModelDao.listShpIntersect(wktGeom,startTime,endTime);
        List<ShapeChangeModel> shapeChangeModels = shapeModelDao.listChangeIntersect(wktGeom,startTime,endTime);
        map.put("normal",shapeModels);
        map.put("change",shapeChangeModels);
        return map;
    }

    /**
     *
     * @param typeName    类型
     * @param startTime   起始时间
     * @return  返回截止时间
     */
    @Override
    public Collection<String> listEndTimeGet(String typeName,Date startTime) {
        List<Date> dates = shapeModelDao.listEndTimeGet(typeName, startTime);
        List<Date> change = shapeModelDao.listEndTimeGetChange(typeName, startTime);
        Set<String> set = new HashSet<>();
        for(Date date: dates ){
            String end = dateFormat.format(date);
            set.add(end);
        }
        for (Date date : change){
            String end = dateFormat.format(date);
            set.add(end);
        }

        return set;
    }

    /**
     *
     * @param typeName    类型
     * @param startTime   起始时间
     * @param endTime     截止时间
     * @return   返回kfq_geom表的矢量信息
     */
    @Override
    public List<ShapeModel> listShapeModel(String typeName, Date startTime, Date endTime) {
        return shapeModelDao.listShapeModel(typeName,startTime,endTime);
    }
}
