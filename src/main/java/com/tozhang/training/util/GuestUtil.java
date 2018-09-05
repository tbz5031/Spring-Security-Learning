package com.tozhang.training.util;

import com.tozhang.training.data.entity.Guest;

import java.util.Map;

public class GuestUtil {

    public static boolean ifExistGuest(Guest guest){
        if (guest!=null) return true;
        else return false;
    }
}
