package com.zkkj.kfq.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ShapeModel {

    private String id;

    private String area;

    private String type_name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    private Date endTime;

    private String the_geom;

}
