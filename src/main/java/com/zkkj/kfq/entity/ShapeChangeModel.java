package com.zkkj.kfq.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ShapeChangeModel {

    private String id;

    private String area;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private String the_geom;

    private String type_name;

    private String type ;

    private String value;

    private String from;

    private String to;

}
