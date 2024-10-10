package com.ezwel.esp.batch.sample.test.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserDetail {
    private int id;
    private String emp_id;
    private String name;
    private String phone_num;
    private Date start_date;
    private Date end_date;
    private String active;
}
