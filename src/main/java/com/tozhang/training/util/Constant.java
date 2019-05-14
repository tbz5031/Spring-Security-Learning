package com.tozhang.training.util;

public interface Constant {
    public static final class Param{
        public static final String firstName = "firstName";
        public static final String lastName= "lastName";
        public static final String account = "account";
        public static final String password = "password";
        public static final String emailAddress = "emailAddress";
        public static final String address = "address";
        public static final String country = "country";
        public static final String state = "state";
        public static final String phoneNumber = "phoneNumber";
        public static final String newAccount = "newAccount";
        public static final String newPasscode = "newPasscode";
        public static final String ts = "ts";

    }

    public static class RequiredParams {
        public static String[] guestSignUp = {Param.firstName, Param.lastName, Param.account, Param.password,
                Param.phoneNumber, Param.emailAddress};
        public static String[] guestSignIn = { Param.account, Param.password};
        public static String[] guestGet = { Param.account, Param.emailAddress};
        public static String[] guestPut = { Param.account, Param.emailAddress};
        public static String[] guestDel = { Param.account, Param.emailAddress};
        public static String[] adminUpdate = {Param.account, Param.password, Param.emailAddress
                , Param.phoneNumber};
        public static String[] adminSignIn = {Param.account,Param.password,Param.emailAddress};

    }
}
