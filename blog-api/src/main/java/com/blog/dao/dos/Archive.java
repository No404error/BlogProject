package com.blog.dao.dos;

import lombok.Data;
//归档,操作的中间对象
@Data
public class Archive {
    private int year;
    private int month;
    private int count;
}
