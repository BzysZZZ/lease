package com.pzj.lease.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursorPageVo <T>{
    private List<T> records;
    private Long nextLastId;
    private boolean hasNext;

    public CursorPageVo(List<T> records, Long size) {
        this.records = records;
        this.hasNext=records.size()==size;
        this.nextLastId=records.isEmpty()?0:((UserInfo)records.get(records.size()-1)).getId();
    }
}
