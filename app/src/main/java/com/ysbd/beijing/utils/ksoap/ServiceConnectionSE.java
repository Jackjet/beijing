package com.ysbd.beijing.utils.ksoap;

import org.ksoap2.transport.ServiceConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by lcjing on 2018/8/30.
 */

public class ServiceConnectionSE  implements ServiceConnection {
    private HttpURLConnection connection;

    public ServiceConnectionSE(String url) throws IOException {
        this.connection = ((HttpURLConnection) new URL(url).openConnection());
        this.connection.setUseCaches(false);
        this.connection.setDoOutput(true);
        this.connection.setDoInput(true);
    }

    public void connect() throws IOException {
        this.connection.connect();
    }

    public void disconnect() {
        this.connection.disconnect();
    }

    @Override
    public List getResponseProperties() throws IOException {
        return null;
    }

    @Override
    public int getResponseCode() throws IOException {
        return 0;
    }

    public void setRequestProperty(String string, String soapAction) {
        this.connection.setRequestProperty(string, soapAction);
    }

    public void setRequestMethod(String requestMethod) throws IOException {
        this.connection.setRequestMethod(requestMethod);
    }

    @Override
    public void setFixedLengthStreamingMode(int i) {

    }

    @Override
    public void setChunkedStreamingMode() {

    }

    public OutputStream openOutputStream() throws IOException {
        return this.connection.getOutputStream();
    }

    public InputStream openInputStream() throws IOException {
        return this.connection.getInputStream();
    }

    public InputStream getErrorStream() {
        return this.connection.getErrorStream();
    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public int getPort() {
        return 0;
    }

    @Override
    public String getPath() {
        return null;
    }

    // 设置连接服务器的超时时间,毫秒级,此为自己添加的方法
    public void setConnectionTimeOut(int timeout) {
        this.connection.setConnectTimeout(timeout);
    }
}
