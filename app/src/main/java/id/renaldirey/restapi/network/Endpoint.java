package id.renaldirey.restapi.network;

import id.renaldirey.restapi.BuildConfig;

public class Endpoint {
    public static final String API_URL = BuildConfig.BASE_URL;

    public static final String API_CREATE = "/crudandroid/index.php/data/add";
    public static final String API_READ = "/crudandroid/index.php/data/";
    public static final String API_UPDATE = "/crudandroid/index.php/data/edit/";
    public static final String API_DELETE = "/crudandroid/index.php/data/delete/";
    public static final String API_UPLOAD = "/crudandroid/index.php/data/uploadimage/";
}
