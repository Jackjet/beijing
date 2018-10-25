package com.ysbd.beijing.utils.ksoap;

import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.ServiceConnection;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Proxy;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Created by lcjing on 2018/8/20.
 */

public class MyGBKSe extends HttpTransportSE{
    public MyGBKSe(String url) {
        super(url);
    }

    public MyGBKSe(Proxy proxy, String url) {
        super(proxy, url);
    }

    public MyGBKSe(String url, int timeout) {
        super(url, timeout);
    }

    public MyGBKSe(Proxy proxy, String url, int timeout) {
        super(proxy, url, timeout);
    }

    public MyGBKSe(String url, int timeout, int contentLength) {
        super(url, timeout, contentLength);
    }

    public MyGBKSe(Proxy proxy, String url, int timeout, int contentLength) {
        super(proxy, url, timeout, contentLength);
    }
    public List call(String soapAction, SoapEnvelope envelope, List headers, File outputFile) throws HttpResponseException, IOException, XmlPullParserException {
        if(soapAction == null) {
            soapAction = "\"\"";
        }

        byte[] requestData = this.createRequestData(envelope, "GBK");
        this.requestDump = this.debug?new String(requestData):null;
        this.responseDump = null;
        ServiceConnection connection = this.getServiceConnection();
        connection.setRequestProperty("User-Agent", "ksoap2-android/2.6.0+");
        if(envelope.version != 120) {
            connection.setRequestProperty("SOAPAction", soapAction);
        }

        if(envelope.version == 120) {
            connection.setRequestProperty("Content-Type", "application/soap+xml;charset=gbk");
        } else {
            connection.setRequestProperty("Content-Type", "text/xml;charset=gbk");
        }

        connection.setRequestProperty("Accept-Encoding", "gzip");
        if(headers != null) {
            for(int i = 0; i < headers.size(); ++i) {
                HeaderProperty hp = (HeaderProperty)headers.get(i);
                connection.setRequestProperty(hp.getKey(), hp.getValue());
            }
        }

        connection.setRequestMethod("POST");
        this.sendData(requestData, connection, envelope);
        requestData = null;
        InputStream is = null;
        List retHeaders = null;
        byte[] buf = null;
        int contentLength = 8192;
        boolean gZippedContent = false;
        boolean xmlContent = false;
        int status = connection.getResponseCode();

        try {
            retHeaders = connection.getResponseProperties();

            for(int i = 0; i < retHeaders.size(); ++i) {
                HeaderProperty hp = (HeaderProperty)retHeaders.get(i);
                if(null != hp.getKey()) {
                    if(hp.getKey().equalsIgnoreCase("content-length") && hp.getValue() != null) {
                        try {
                            contentLength = Integer.parseInt(hp.getValue());
                        } catch (NumberFormatException var17) {
                            contentLength = 8192;
                        }
                    }

                    if(hp.getKey().equalsIgnoreCase("Content-Type") && hp.getValue().contains("xml")) {
                        xmlContent = true;
                    }

                    if(hp.getKey().equalsIgnoreCase("Content-Encoding") && hp.getValue().equalsIgnoreCase("gzip")) {
                        gZippedContent = true;
                    }
                }
            }

            if(status != 200 && status != 202) {
                throw new HttpResponseException("HTTP request failed, HTTP status: " + status, status, retHeaders);
            }

            if(contentLength > 0) {
                if(gZippedContent) {
                    is = this.getUnZippedInputStream(new BufferedInputStream(connection.openInputStream(), contentLength));
                } else {
                    is = new BufferedInputStream(connection.openInputStream(), contentLength);
                }
            }
        } catch (IOException var18) {
            if(contentLength > 0) {
                if(gZippedContent) {
                    is = this.getUnZippedInputStream(new BufferedInputStream(connection.getErrorStream(), contentLength));
                } else {
                    is = new BufferedInputStream(connection.getErrorStream(), contentLength);
                }
            }

            if(var18 instanceof HttpResponseException && !xmlContent) {
                if(this.debug && is != null) {
                    this.readDebug((InputStream)is, contentLength, outputFile);
                }

                connection.disconnect();
                throw var18;
            }
        }

        if(this.debug) {
            is = this.readDebug((InputStream)is, contentLength, outputFile);
        }

        if(is != null) {
            this.parseResponse(envelope, (InputStream)is, retHeaders);
        }

        is = null;
        buf = null;
        connection.disconnect();
        connection = null;
        return retHeaders;
    }

    private InputStream getUnZippedInputStream(InputStream inputStream) throws IOException {
        try {
            return (GZIPInputStream)inputStream;
        } catch (ClassCastException var3) {
            return new GZIPInputStream(inputStream);
        }
    }

    private InputStream readDebug(InputStream is, int contentLength, File outputFile) throws IOException {
        Object bos;
        if(outputFile != null) {
            bos = new FileOutputStream(outputFile);
        } else {
            bos = new ByteArrayOutputStream(contentLength > 0?contentLength:262144);
        }

        byte[] buf = new byte[256];

        while(true) {
            int rd = is.read(buf, 0, 256);
            if(rd == -1) {
                ((OutputStream)bos).flush();
                if(bos instanceof ByteArrayOutputStream) {
                    buf = ((ByteArrayOutputStream)bos).toByteArray();
                }

                bos = null;
                this.responseDump = new String(buf);
                is.close();
                return (InputStream)(outputFile != null?new FileInputStream(outputFile):new ByteArrayInputStream(buf));
            }

            ((OutputStream)bos).write(buf, 0, rd);
        }
    }
}
