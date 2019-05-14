package com.tozhang.training.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tozhang.training.data.entity.Guest;

import java.util.HashMap;
import java.util.Map;

public class GuestUtil {

    public static boolean ifExistGuest(Guest guest){
        if (guest!=null) return true;
        else return false;
    }

    public static Map<String,Object> mappingHelper(Guest guest){

        ObjectMapper m = new ObjectMapper();
        Map<String,Object> res = m.convertValue(guest,Map.class);
        return res;
    }

    public static Map<String,Object> mappingHelper(Map map){

        ObjectMapper m = new ObjectMapper();
        Map<String,Object> res = m.convertValue(map,Map.class);
        return res;
    }
}
