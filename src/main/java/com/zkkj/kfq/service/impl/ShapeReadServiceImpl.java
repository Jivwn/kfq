package com.zkkj.kfq.service.impl;

import com.zkkj.kfq.entity.ShapeChangeModel;
import com.zkkj.kfq.entity.ShapeModel;
import com.zkkj.kfq.mapper.ShapeModelDao;
import com.zkkj.kfq.service.ShapeReadService;
import com.zkkj.kfq.util.ShapeReader;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FilenameFilter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ShapeReadServiceImpl implements ShapeReadService {


//    @Autowired
    @Resource
    ShapeModelDao shapeModelDao;

    DateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * 得到时间数据
     * @param name
     * @return
     * @throws Exception
     */
    private Pair<Date, Date> getDateFromShapeName(String name) throws Exception {

        //river_encroachment-20200601_20200701-kfq.shp
        //river-20200601_20200701-kfq.shp

        String[] split = name.split("-");
        Exception exception = new Exception("shape文件名称不符合规定！示例：river_encroachment-20200601_20200701-kfq.shp");
        if (split.length != 3) {
            throw exception;
        }

        String[] dateArr = split[1].split("_");
        if (dateArr.length != 2 && dateArr[0].length() != 8 && dateArr[1].length() != 8) {
            throw exception;
        }

        String firstDateStr = dateArr[0];
        String fDateFormat = firstDateStr.substring(0, 4) +"-" +firstDateStr.substring(4, 6)+"-"+firstDateStr.substring(6, 8);

        Date fDate = dateFormat.parse(fDateFormat);

        String secondDateStr = dateArr[1];
        String sDateFormat = secondDateStr.substring(0, 4) +"-" +secondDateStr.substring(4, 6)+"-"+secondDateStr.substring(6, 8);
        Date sDate = dateFormat.parse(sDateFormat);

        return new Pair<>(fDate, sDate);
    }

    /**
     * 得到shp文件
     * @param filePath
     * @return
     * @throws Exception
     */
    private File getShapeFile(String filePath) throws Exception {
        File folder = new File(filePath);
        File shpFile;

        if (folder.isDirectory()) {
            File[] files = folder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".shp");
                }
            });
            if (files.length != 1) {
                throw new Exception("请确认该目录下只有一个目标shp文件");
            }
            shpFile = files[0];
        } else {
            shpFile = folder;
        }
        return shpFile;
    }


    /**
     * 插入kfq_geom中数据
     * @param filePath
     */
    @Override
    public void uploadShapeFile(String filePath) {
        ShapeReader<ShapeModel> shapeModelShapeReader = new ShapeReader<>();

        try {

            File shpFile = getShapeFile(filePath);
            String name = shpFile.getName();
            Pair<Date, Date> datePair = getDateFromShapeName(name);

            List<ShapeModel> shapeModels = shapeModelShapeReader.readShapeFile(shpFile, ShapeModel.class);

            shapeModels.stream().forEach(shapeModel -> {
                shapeModel.setStartTime(datePair.getKey());
                shapeModel.setEndTime(datePair.getValue());
            });

            shapeModelDao.insertShapeModel(shapeModels);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 插入kfq_geom_change中的数据
     * @param filePath
     */
    @Override
    public void uploadChangeShapeFile( String filePath) {
        ShapeReader<ShapeChangeModel> shapeModelShapeReader = new ShapeReader<>();
        try {
            File shpFile = getShapeFile(filePath);
            String name = shpFile.getName();
            Pair<Date, Date> datePair = getDateFromShapeName(name);
            List<ShapeChangeModel> shapeChangeModels = shapeModelShapeReader.readShapeFile(shpFile, ShapeChangeModel.class);

            shapeChangeModels.stream().forEach(shapeModel -> {
                shapeModel.setStartTime(datePair.getKey());
                shapeModel.setEndTime(datePair.getValue());
                String value = shapeModel.getValue();
                String[] split = value.split("→");
                if (split.length == 2){
                    shapeModel.setFrom(split[0]);
                    shapeModel.setTo(split[1]);
                }

            });

            shapeModelDao.insertShapeChangeModel(shapeChangeModels);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
