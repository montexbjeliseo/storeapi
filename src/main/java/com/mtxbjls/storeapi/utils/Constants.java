package com.mtxbjls.storeapi.utils;

public class Constants {
    public abstract static class Endpoints {
        public static final String CATEGORIES = "/api/v1/categories";
        public static final String PRODUCTS = "/api/v1/products";
        public static final String FILES = "/api/v1/files";
        public static final String UPLOAD = "/upload";
        
        public static final String AUTH = "/api/v1/auth";        
        public static final String REGISTER = "/register";
        public static final String LOGIN = "/login";

    }

    public abstract static class PathVariables {
        public static final String ID = "/{id}";
        public static final String FILENAME = "/{fileName}";
    }

    public abstract static class Storage {
        public static final String UPLOAD_DIR = "./uploads";

    }

    public abstract static class Roles {
        public static final String CUSTOMER = "CUSTOMER";
        public static final String CUSTOMER_DESCRIPTION = "Costumer";
        public static final String ADMIN = "ADMIN";
        public static final String ADMIN_DESCRIPTION = "Admin";
    }
}
