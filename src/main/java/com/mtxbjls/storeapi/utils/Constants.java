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
        public static final String PROFILE = "/profile";

        public static final String DOCS = "/api/v1/docs/**";
        public static final String SWAGGER_CONFIG = "/v3/api-docs/**";

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

    public abstract static class Docs {
        public static final String TITLE = "Store API";
        public static final String DESCRIPTION = "Store API";
        public static final String VERSION = "1.0.0";

        public static final String BEARER_AUTH = "bearerAuth";
        public static final String BEARER_FORMAT = "JWT";
        public static final String BEARER_SCHEME = "bearer";

        public abstract static class Tags {
            public static final String AUTH = "Authentication";
            public static final String AUTH_DESCRIPTION = "Authentication endpoints";
            public static final String CATEGORIES = "Categories";
            public static final String CATEGORIES_DESCRIPTION = "Categories endpoints";
        }

        public abstract static class Operations {
            public static final String AUTH_LOGIN = "Login a user";
            public static final String AUTH_CREATE_USER = "Create a new user";
            public static final String AUTH_GET_PROFILE = "Get profile";
            public static final String CATEGORIES_CREATE_NEW = "Create a new category";
            public static final String CATEGORIES_GET_ALL = "Get all categories";
            public static final String CATEGORIES_GET_BY_ID = "Get a category by id";
            public static final String CATEGORIES_UPDATE = "Update a category";
            public static final String CATEGORIES_DELETE = "Delete a category";
        }

        public abstract static class ResponseCodes {
            public static final String SUCCESS = "200";
            public static final String CREATED = "201";
            public static final String BAD_REQUEST = "400";
            public static final String UNAUTHORIZED = "401";
            public static final String FORBIDDEN = "403";
            public static final String NOT_FOUND = "404";
            public static final String CONFLICT = "409";
            public static final String INTERNAL_SERVER_ERROR = "500";
        }

        public abstract static class ResponseDescriptions {
            public static final String SUCCESS = "Success";
            public static final String CREATED = "Created";
            public static final String BAD_REQUEST = "Bad request";
            public static final String UNAUTHORIZED = "Unauthorized";
            public static final String FORBIDDEN = "Forbidden";
            public static final String NOT_FOUND = "Not found";
            public static final String CONFLICT = "Conflict";
            public static final String INTERNAL_SERVER_ERROR = "Internal server error";

            public static final String USER_CREATED = "User created";
            public static final String USER_LOGGED_IN = "User logged in";
            public static final String NOT_FOUND_USER = "User not found";
            public static final String USER_PROFILE = "User profile";
            public static final String CATEGORY_CREATED = "Category created";
            public static final String CATEGORIES_RETRIEVED = "Categories retrieved";
            public static final String CATEGORY_UPDATED = "Category updated";
            public static final String CATEGORY_NOT_FOUND = "Category not found";
            public static final String CATEGORY_DELETED = "Category deleted";
        }
    }
}
