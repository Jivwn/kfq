package com.zkkj.kfq.util;

import com.zkkj.kfq.entity.ShapeModel;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.Property;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

public class ShapeReader<E> {

    public List<E> readShapeFile(File folder, Class<E> clazz) {
        List<E> modelList = new ArrayList<>();

        if (!folder.isDirectory()) {
            if (folder.toString().endsWith(".shp")) {
                try {
                    modelList = getShapeFile(folder, clazz);
                    return modelList;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("选择的文件后缀名不是.shp");
            }
        } else {
            File[] files = folder.listFiles();
            if (files.length < 0) {
                System.out.println("目录文件为空");
                return modelList;
            }

            for (File file : files) {
                if (!file.toString().endsWith(".shp")) {
                    continue;
                }
                try {
                    List<E> models = getShapeFile(file, clazz);
                    modelList.addAll(models);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return modelList;
    }

    public List<E> getShapeFile(File file, Class<E> clazz) throws Exception {

        String name = file.getName();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("url", file.toURI().toURL());
        List<E> models = new ArrayList<>();
        DataStore dataStore = DataStoreFinder.getDataStore(map);

        //字符转码，防止中文乱码
        ((ShapefileDataStore) dataStore).setCharset(Charset.forName("utf8"));
//        ((ShapefileDataStore) dataStore).setCharset(Charset.forName("GBK"));
        String typeName = dataStore.getTypeNames()[0];
        FeatureSource<SimpleFeatureType, SimpleFeature> source = dataStore.getFeatureSource(typeName);
        FeatureCollection<SimpleFeatureType, SimpleFeature> collection = source.getFeatures();
        FeatureIterator<SimpleFeature> features = collection.features();

        BeanInfo beanInfo = null;   //获取BeanInfo
        try {
            beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException ex) {
            ex.printStackTrace();
        }
        List<PropertyDescriptor> propertyDescriptors = Arrays.asList(beanInfo.getPropertyDescriptors());
        Map<String, PropertyDescriptor> fieldMap = propertyDescriptors.stream().filter(propertyDescriptor -> {
            return propertyDescriptor.getWriteMethod() != null;
        }).collect(Collectors.toMap(PropertyDescriptor::getName, propertyDescriptor -> propertyDescriptor));


        while (features.hasNext()) {
            SimpleFeature feature = features.next();
            E obj = clazz.newInstance();

            Iterator<? extends Property> iterator = feature.getValue().iterator();

            while (iterator.hasNext()) {
                Property property = iterator.next();
                String propertyName = property.getName().toString().toLowerCase();
//                System.out.println(propertyName);

                PropertyDescriptor propertyDescriptor = fieldMap.get(propertyName);
                Method method = propertyDescriptor.getWriteMethod();
                if (method != null) {
                    method.invoke(obj, property.getValue().toString());
                }
            }
            models.add(obj);
        }
        return models;
    }



    public static void main(String[] args) {

        Integer integer = Integer.valueOf("02");
        System.out.println(integer);


//
//        String filePath = "E:\\开发区Shape测试数据\\河道侵占\\river-shp";
        String filePath = "E:\\shp\\shap";
        ShapeReader shapeReader = new ShapeReader();
        List<ShapeModel> shapeModels = shapeReader.readShapeFile(new File(filePath), ShapeModel.class);

        for (ShapeModel shapeModel : shapeModels) {
            System.out.println(shapeModel);
        }
    }


}