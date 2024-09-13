package com.instagram.models;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEnumConverter implements Converter<String, Location>{
        @Override
        public Location convert(String source) {
            return Location.valueOf(source.toUpperCase());
        }

}
