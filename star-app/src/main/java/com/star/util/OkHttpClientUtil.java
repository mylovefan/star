package com.star.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OkHttpClientUtil {

    private static final OkHttpClient OK_HTTP_CLIENT;
    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    static {
    	HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OK_HTTP_CLIENT = new OkHttpClient.Builder()
	            .connectTimeout(30, TimeUnit.SECONDS)
	            .readTimeout(20, TimeUnit.SECONDS)
	            .addNetworkInterceptor(logInterceptor)
	            .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .sslSocketFactory(SSLSocket.getSSLSocketFactory())
                .hostnameVerifier(SSLSocket.getHostnameVerifier())
	            .build();
    }

    public static OkHttpResult get(String url) {
        return get(url, null);
    }

    public static OkHttpResult get(String url, Map<String, String> queryParams) {
        return get(url, queryParams, null);
    }


    /**
     * Get Http请求
     *
     * @param url         请求url
     * @param queryParams url请求
     * @return
     */
    public static OkHttpResult get(String url, Map<String, String> queryParams, Map<String, String> headerMap) {
        try {
            Request.Builder reqBuild = new Request.Builder();
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

            //添加查询参数
            if (queryParams != null) {
                queryParams.forEach((key, value) -> {
                    if (value == null) {
                        value = "";
                    }
                    urlBuilder.addQueryParameter(key, value);
                });
            }

            //添加header
            if (headerMap != null) {
                headerMap.forEach((key, value) -> {
                    if (value == null) {
                        value = "";
                    }
                    reqBuild.header(key, value);
                });
            }

            Request request = reqBuild.url(urlBuilder.build()).build();
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            if (response.isSuccessful()) {
                return new OkHttpResult(true, response.body().string());
            } else {
                return new OkHttpResult(false, "请求失败，返回值" + response.code());
            }
        } catch (IOException exception) {
            log.error("", exception);
            return new OkHttpResult(false, "GET请求发生异常");
        }
    }

    /**
     * Get Http请求
     *
     * @param url       请求url
     * @param headerMap 请求头参数
     * @param queryParams url请求
     * @return
     */
    public static OkHttpResult getParamObj(String url, String jsonValue, Map<String, Object> headerMap, Map<String, Object> queryParams) {
        try {
            Request.Builder reqBuild = new Request.Builder();
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

            //添加查询参数
            if (queryParams != null) {
                queryParams.forEach((key, value) -> {
                    if (value == null) {
                        value = "";
                    }
                    urlBuilder.addQueryParameter(key, value.toString());
                });
            }

            //添加header
            if (headerMap != null) {
                headerMap.forEach((key, value) -> {
                    if (value == null) {
                        value = "";
                    }
                    reqBuild.header(key, value.toString());
                });
            }

            Request request = reqBuild.url(urlBuilder.build()).build();
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            if (response.isSuccessful()) {
                return new OkHttpResult(true, response.body().string());
            } else {
                return new OkHttpResult(false, "请求失败，返回值" + response.code());
            }
        } catch (IOException ex) {
            log.error("GET请求发生异常", ex);
            return new OkHttpResult(false, "GET请求发生异常:" + ex.getMessage());
        }
    }

    public static OkHttpResult post(String url, Map<String, Object> queryParams) {
        return post(url, queryParams, null);
    }

    public static OkHttpResult post(String url, Map<String, Object> queryParams, Map<String, String> headerMap) {
        return post(url, null, queryParams, headerMap);
    }

    public static OkHttpResult post(String url, String jsonValue) {
        return post(url, jsonValue, null, null);
    }

    public static OkHttpResult post(String url, String jsonValue, Map<String, String> headerMap) {
        return post(url, jsonValue, null, headerMap);
    }

    /*public static OkHttpResult getByJson(String url, String jsonValue) {
        return getParamObj(url, jsonValue, null, null);
    }*/


    public static OkHttpResult postFromData(String url, String paramStr) {
        try {
            RequestBody requestBody = null;

            //json请求数据
            if (paramStr != null) {
                requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=utf-8"), paramStr);
            }

            Request.Builder reqBuilder = new Request.Builder();
            reqBuilder.addHeader("Connection","close");
            Request request = reqBuilder.url(url).post(requestBody).build();
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            if (response.isSuccessful()) {
                return new OkHttpResult(true, response.body().string());
            } else {
                return new OkHttpResult(false, "请求失败，返回值" + response.code());
            }
        } catch (IOException exception) {
            log.error("", exception);
            return new OkHttpResult(false, "POST请求发生异常");

        }
    }

    /**
     * POST Http请求
     *
     * @param url         请求url
     * @param json        json数据
     * @param queryParams 表单数据
     * @param headerMap   header
     * @return
     */
    private static OkHttpResult post(String url, String json, Map<String, Object> queryParams, Map<String, String> headerMap) {

        try {
            RequestBody requestBody = null;

            //json请求数据
            if (json != null) {
                requestBody = RequestBody.create(JSON, json);
            }

            //普通表单请求数据 x-www-form-urlencoded
            if (queryParams != null) {
                FormBody.Builder builder = new FormBody.Builder();
                queryParams.forEach((key, value) -> {
                    if (value == null) {
                        value = "";
                    }
                    builder.add(key, value.toString());
                });
                requestBody = builder.build();
            }

            Request.Builder reqBuilder = new Request.Builder();
            //添加header
            if (headerMap != null) {
                headerMap.forEach((key, value) -> {
                    if (value == null) {
                        value = "";
                    }
                    reqBuilder.header(key, value);
                });
            }
            Request request = reqBuilder.url(url).post(requestBody).build();
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            if (response.isSuccessful()) {
                return new OkHttpResult(true, response.body().string());
            } else {
                return new OkHttpResult(false, "请求失败，返回值" + response.code());
            }
        } catch (IOException exception) {
            log.error("", exception);
            return new OkHttpResult(false, "POST请求发生异常");
        }
    }


    public static OkHttpResult put(String url, Map<String, Object> headerParam, String jsonValue) {
	    try {
	    	RequestBody requestBody = null;
            if (StringUtils.isNotBlank(jsonValue)) {
                requestBody = RequestBody.create(JSON, jsonValue);
            }
            Request.Builder reqBuilder = new Request.Builder();
            /**
             * 添加header
             */
            if (headerParam != null) {
            	headerParam.forEach((key, value) -> {
                    if (value == null) {
                        value = "";
                    }
                    reqBuilder.header(key, value.toString());
                });
            }
            Request request = reqBuilder.url(url).put(requestBody).build();

		    Response response = OK_HTTP_CLIENT.newCall(request).execute();
            if (response.isSuccessful()) {
                return new OkHttpResult(true, response.body().string());
            } else {
                return new OkHttpResult(false, "请求失败，返回值" + response.code());
            }
	    } catch (Exception ex) {
	        log.error("PUT请求发生异常", ex);
	        return new OkHttpResult(false, "PUT请求发生异常");
	    }
    }


    /**
     * @param url
     * @param queryParams
     * @param headerMap
     * @return: byte[]  当返回的是流数据时，把二进制流返回出去，当不是流数据时，返回null
     * @author: chaoli
     * @date: 2020/3/13
     * @description: 主要是给身份证系统做下载用，其他用途请自行判断是否能复用。
     */
    public static byte[] getStream(String url, Map<String, String> queryParams, Map<String, String> headerMap) {
        try {
            Request.Builder reqBuild = new Request.Builder();
            HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

            //添加查询参数
            if (queryParams != null) {
                queryParams.forEach((key, value) -> {
                    if (value == null) {
                        value = "";
                    }
                    urlBuilder.addQueryParameter(key, value);
                });
            }

            //添加header
            if (headerMap != null) {
                headerMap.forEach((key, value) -> {
                    if (value == null) {
                        value = "";
                    }
                    reqBuild.header(key, value);
                });
            }
            Request request = reqBuild.url(urlBuilder.build()).build();
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            //
            String contentType = response.header("Content-Type");
            if (response.isSuccessful()) {
                if (org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE.equals(contentType) ||
                        org.springframework.http.MediaType.IMAGE_JPEG_VALUE.equals(contentType) ||
                        org.springframework.http.MediaType.IMAGE_PNG_VALUE.equals(contentType)) {

                    return response.body().bytes();
                }else if (org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE.equals(contentType)){
                    log.info(response.body().string());
                }
            } else {
                return null;
            }
        } catch (IOException exception) {
            log.error("", exception);
            return null;
        }
        return null;
    }


    public static class OkHttpResult {

        public OkHttpResult(boolean isSuccess, String resultOrMessage) {
            this.isSuccess = isSuccess;
            if (isSuccess) {
                this.result = resultOrMessage;
            } else {
                this.errorMessage = resultOrMessage;
            }
        }

        /**
         * 是否成功
         */
        private boolean isSuccess;
        /**
         * 错误消息
         */
        private String errorMessage;
        /**
         * 请求结果
         */
        private String result;

        public boolean isSuccess() {
            return isSuccess;
        }

        public void setSuccess(boolean success) {
            isSuccess = success;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

    private static class HttpLogger implements HttpLoggingInterceptor.Logger {

        @Override
        public void log(String s) {
            //log.info(s);
        }
    }
}
