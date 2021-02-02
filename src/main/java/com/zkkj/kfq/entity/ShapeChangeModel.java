package com.zkkj.kfq.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ShapeChangeModel {

    private String id;

    private String area;

    private Date startTime;

    private Date endTime;

    private String the_geom;

    private String type_name;

    private String type ;

    private String value;

    private String from;

    private String to;

}
