package com.mtxbjls.storeapi.utils;

public class Constants {
    public abstract static class Endpoints {
        public static final String CATEGORIES = "/api/v1/categories";
        public static final String PRODUCTS = "/api/v1/products";
        public static final String FILES = "/api/v1/files";
        public static final String UPLOAD = "/upload";
    }

    public abstract static class PathVariables {
        public static final String ID = "/{id}";
        public static final String FILENAME = "/{fileName}";
    }

    public abstract static class Storage {
        public static final String UPLOAD_DIR = "./uploads";

    }
}
