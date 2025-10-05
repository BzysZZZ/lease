package com.pzj.lease.web.admin.custom.converter;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.pzj.lease.model.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

@Component
public class StringToBaseEnumConverter implements ConverterFactory<String, BaseEnum> {
    //String类型到每个BaseEnum类型的转换
    @Override
    public <T extends BaseEnum>Converter<String, T> getConverter(Class<T> targetType) {
        return new Converter<String,T>(){
            @Override
            public T convert(String code) {
                T[] enumConstants = targetType.getEnumConstants();
                for(T item : enumConstants){
                    if(item.getCode().equals(Integer.valueOf(code))){
                        return item;
                    }
                }
                throw new IllegalArgumentException("code"+code+"非法");
            }
        };
    }
}
