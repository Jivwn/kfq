package com.zkkj.kfq.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ShapeModel {

    private String id;

    private String area;

    private String type_name;

    private Date startTime;

    private Date endTime;

    private String the_geom;

}
