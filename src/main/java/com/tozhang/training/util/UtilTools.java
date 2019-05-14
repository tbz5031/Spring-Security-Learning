package com.tozhang.training.util;


import com.tozhang.training.data.webservice.AdminController;
import org.apache.log4j.Logger;

import java.util.Map;

public class UtilTools {
    private static final Logger logger = Logger.getLogger(UtilTools.class);
    public boolean checkParameters(Map payload, String[] requiredList){
        int num = 0;
        for (String param : requiredList){
            try{
                if(payload.get(param)==null) throw new ServiceRuntimeException();
                num++;
            }catch (Exception e){
                logger.error("Field : "+ param +" is missing");
                e.printStackTrace();
            }
//            if(payload.containsKey(param)) {
//                logger.info("Param :" + param + "field check is passed");
//                num++;
//            }
        }
        if(requiredList.length == num) return true;
        else return false;

    }
}
