package com.ysbd.beijing.utils;


import android.content.Context;
import android.text.ClipboardManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ysbd.beijing.App;
import com.ysbd.beijing.bean.AddressBean;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.litepal.crud.DataSupport;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by lcjing on 2018/8/9.
 */

public class WebServiceUtils {
    private static WebServiceUtils client;

    private WebServiceUtils() {
        WebServiceManager.getInstance().setDebug(true);
    }

    public void initId(Context context) {
        userid = context.getSharedPreferences(Constants.SP, Context.MODE_PRIVATE).getString(Constants.USER_ID, "");
    }

    public static WebServiceUtils getInstance() {
        if (client == null) {
            synchronized (WebServiceUtils.class) {
                if (client == null)
                    client = new WebServiceUtils();
            }
        }
        return client;
    }


    //    public static final String HOST1 = "http://192.168.0.102:9998";
//    public static final String HOST1 = "http://172.28.68.48:9910";
//    public static final String HOST2 = "http://218.60.41.112:9998";
//    public static final String HOST = HOST1;
    public static final String HOST = SpUtils.getInstance().getIP();// ;"http://172.10.48.92:9998/risenetoabjcz"
    private static final String NAME_SPACE = "http://www.freshpower.com.cn";//http://mobile.risesoft.net
    //    http://www.webxml.com.cn/WebServices/WeatherWebService.asmx
    private static final String HOST_TODO = HOST + "/services/TodoFile";//?wsdl/risenetoabjcz
    private static final String HOST_WORKFLOW = HOST + "/services/mobileWorkflowInstance";//?wsdl
    private static final String HOST_USER = HOST + "/services/mobileUserInfo";//?wsdl
    private static final String HOST_PORTAL = HOST + "/services/portalFrameworkService";//?wsdl

    public static final String SIGNATURE_IMG = HOST + "/riseoffice/default/signatureimg.jsp?personGUID=";///risenetoabjcz

    //http://218.60.41.112:9998/risenetoabjcz/services/mobileUserInfo?wsdl
    //http://218.60.41.112:9998/risenetoabjcz/services/TodoFile?wsdl
    //http://218.60.41.112:9998/risenetoabjcz/services/mobileWorkflowInstance?wsdl
    //http://218.60.41.112:9998/risenetoabjcz/services/portalFrameworkService?wsdl
    //http://218.60.41.112:9998/risenetoabjcz/login/db/login.jsp
    //http://192.168.0.100:9998/risenetoabjcz/riseoffice/default/signatureimg.jsp?personGUID={0A2FCA25-FFFF-FFFF-81CF-8A1700000001}

//  public String login(String name,String password){
//      String method = "login";
//      int envolopeVersion = SoapEnvelope.VER11;
//      SoapObject request = new SoapObject(NAME_SPACE, method);
//      String value="{\"username\":\""+name+"\",\"password\":\"" + password + "\"}";
//      request.addProperty("in0", value);
//      SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(envolopeVersion);
//      envelope.setOutputSoapObject(request);
//      envelope.dotNet = false;
//      MyGBKSe se = new MyGBKSe(HOST_USER);
//      try {
//          se.call(null, envelope);
//          SoapObject response1 = (SoapObject) envelope.bodyIn;
//          SoapPrimitive o = (SoapPrimitive) response1.getProperty(0);
//          String a = o.getValue().toString();
//          return a;
//      } catch (Exception e) {
//          e.printStackTrace();
//      }
//      return "";
//  }

    public String login(String name, String password) {
        String value = "{\"username\":\"" + name + "\",\"password\":\"" + password + "\"}";
        return WebServiceManager.getInstance().connect("login", HOST_USER, value);
    }

    public String login(String name, String password, ClipboardManager clipboardManager) {
        String value = "{\"username\":\"" + name + "\",\"password\":\"" + password + "\"}";
        return WebServiceManager.getInstance().connect("login", HOST_USER, value, clipboardManager);
    }

    public String findToDoFileInfo() {

        String method = "findTodoFileInfo";
        int envolopeVersion = SoapEnvelope.VER11;
        SoapObject request = new SoapObject(NAME_SPACE, method);
        //{0A2FF324-FFFF-FFFF-CBC7-A9A80000002A}主办文    ；      {0A2FF324-FFFF-FFFF-8885-740500000008}
        String value = "{\"instanceguid\":\"{0A2FF324-FFFF-FFFF-CBC7-A9A80000002A}\",\"userid\":\"7cd7db16c48a5fa039ab300c79d46bc5000000\"}";
        request.addProperty("in0", value);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(envolopeVersion);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = false;
        HttpTransportSE se = new HttpTransportSE(HOST_TODO);
//        String soapAction = "http://mobile.risesoft.net/login";
        try {
            se.call(null, envelope);
            SoapObject response1 = (SoapObject) envelope.bodyIn;
            SoapPrimitive o = (SoapPrimitive) response1.getProperty(0);
            String a = o.getValue().toString();
            return a;
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void refreshAddressBook() {
        DataSupport.deleteAll(AddressBean.class);
//        SpUtils.getInstance().setAddressVisiable(false);
        String data = WebServiceManager.getInstance().connect("getAdderBook", HOST_TODO, "?");
        try {
            JSONObject jsonObject = new JSONObject(data);
            if (jsonObject.has("userlist")) {
                insertAddressBook(jsonObject.get("userlist").toString());
            }
            if (jsonObject.has("deptlist")) {
                insertAddressBook(jsonObject.get("deptlist").toString());
            } else {
                insertAddressBook(data);
            }
            SpUtils.getInstance().setAddressVisiable(true);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void getAddressBook() {
        String data = WebServiceManager.getInstance().connect("getAdderBook", HOST_TODO, "?");
        try {
            JSONObject jsonObject = new JSONObject(data);
            if (jsonObject.has("userlist")) {
                insertAddressBook(jsonObject.get("userlist").toString());
            }
            if (jsonObject.has("deptlist")) {
                insertAddressBook(jsonObject.get("deptlist").toString());
            } else {
                insertAddressBook(data);
            }
            SpUtils.getInstance().setAddressVisiable(true);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    //领导日程
    public String LeadershipAgenda() {
        return WebServiceManager.getInstance().connect("LeadershipAgenda", HOST_PORTAL, "{\"\":\"\"}");
    }

    public void insertAddressBook(String data) {
        Map<String, AddressBean> address = new Gson().fromJson(data, new TypeToken<Map<String, AddressBean>>() {
        }.getType());
        if (address != null) {
            //遍历map中的值
            for (AddressBean value : address.values()) {
                List<AddressBean> persons = DataSupport.select("nodeGuid", "nodeName").where("nodeGuid = ?", value.getNodeGuid()).find(AddressBean.class);
                if (persons.size() < 1) {
                    try {
                        value.save();
                    } catch (Exception e) {
                        App.catchE(e);
                    }

                }
            }
        }
    }

    public String updateFile(String instanceGuid, String fileguid, String filename, String filePath) {
        String method = "updateFile";
        int envolopeVersion = SoapEnvelope.VER11;
        SoapObject request = new SoapObject(NAME_SPACE, method);
        //{0A2FF324-FFFF-FFFF-CBC7-A9A80000002A}主办文    ；      {0A2FF324-FFFF-FFFF-8885-740500000008}
        File file = new File(filePath);
        OutputStream outStream = null;
        try {
            outStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String jsonData = "{\"instanceGuid\":\"" + instanceGuid + "\",\"fileguid\":\"" + fileguid + "\",\"filename\":\"" + filename + "\",\"fujian\":\"" + outStream.toString() + "\"}";
        request.addProperty("in0", jsonData);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(envolopeVersion);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = false;
        HttpTransportSE se = new HttpTransportSE(HOST_WORKFLOW);

        try {
            se.call(null, envelope);
            SoapObject response1 = (SoapObject) envelope.bodyIn;
            SoapPrimitive o = (SoapPrimitive) response1.getProperty(0);
            String a = o.getValue().toString();
            return a;
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String findToDoFileInfo(String jsonData) {
        return WebServiceManager.getInstance().connect("findTodoFileInfo", HOST_TODO, jsonData);
    }

    public String findTodoFiles(int page) {
        String value = "{\"instanceguid\":\"{0A2FF324-FFFF-FFFF-8885-740500000008}\",\"userid\":\"" + userid + "\",\"stalength\":" + page + ",\"returnlength\":30}";
        return WebServiceManager.getInstance().connect("findTodoFiles", HOST_TODO, value);
    }

    //获取红绿灯
    public String findTodoRedGreenFiles(String instanceGuid, String userId) {
        String value = "{\"instanceguid\":\"" + instanceGuid + "\",\"userid\":\"" + userId + "\"}";
        return WebServiceManager.getInstance().connect("userhld", HOST_TODO, value);
    }

    public String queryForm(String type, Map<String, String> params) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"").append("leixing").append("\":\"").append(type).append("\"");
        for (String key : params.keySet()) {
            sb.append(",\"").append(key).append("\":\"").append(params.get(key)).append("\"");
        }
        sb.append("}");
        String value = sb.toString();
//        return value;
        return WebServiceManager.getInstance().connect("getGongWenQuery", HOST_TODO, value);
    }

    public String queryForm(Map<String, String> params) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"").append("isop").append("\":\"").append("1").append("\"");
        for (String key : params.keySet()) {
            sb.append(",\"").append(key).append("\":\"").append(params.get(key)).append("\"");
        }
        sb.append("}");
        String value = sb.toString();
//        return value;
        return WebServiceManager.getInstance().connect("getGongWenQuery", HOST_TODO, value);
    }

    /**
     * 根据用户id 获取用户信息
     *
     * @param userid
     * @return
     */
    public String roleRelateDepartment(String userid) {
        String method = "roleRelateDepartment";
        int envolopeVersion = SoapEnvelope.VER11;
        SoapObject request = new SoapObject(NAME_SPACE, method);
        //{0A2FF324-FFFF-FFFF-CBC7-A9A80000002A}主办文    ；      {0A2FF324-FFFF-FFFF-8885-740500000008}
        String value = "{\"userid\":\"" + userid + "\"}";
        request.addProperty("in0", value);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(envolopeVersion);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = false;
        HttpTransportSE se = new HttpTransportSE(HOST_TODO);
//        String soapAction = "http://mobile.risesoft.net/login";
        try {
            se.call(null, envelope);
            SoapObject response1 = (SoapObject) envelope.bodyIn;
            SoapPrimitive o = (SoapPrimitive) response1.getProperty(0);
            String a = o.getValue().toString();
            return a;
        } catch (IOException | XmlPullParserException | ClassCastException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String findTodoFiles1(String type, String page, String actors) {
        String method = "findTodoFiles";
        int envolopeVersion = SoapEnvelope.VER11;
        SoapObject request = new SoapObject(NAME_SPACE, method);
        //{0A2FF324-FFFF-FFFF-CBC7-A9A80000002A}主办文    ；      {0A2FF324-FFFF-FFFF-8885-740500000008}
        String value = "{\"instanceguid\":\"{0A2FF324-FFFF-FFFF-8885-740500000008}\",\"userid\":\"" + userid + "\",\"stalength\":" + page + ",\"returnlength\":10,\"actors\":\"" +
                actors + "\",\"type\":\"" + type + "\"}";
        request.addProperty("in0", value);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(envolopeVersion);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = false;
        HttpTransportSE se = new HttpTransportSE(HOST_TODO);
//        String soapAction = "http://mobile.risesoft.net/login";
        try {
            se.call(null, envelope);
            SoapObject response1 = (SoapObject) envelope.bodyIn;
            SoapPrimitive o = (SoapPrimitive) response1.getProperty(0);
            String a = o.getValue().toString();
            return a;
        } catch (IOException | XmlPullParserException | ClassCastException e) {
            e.printStackTrace();
        }
        return "";
    }

    //分类获取待办
//    public String findTodoFiles(String id,String type,String page,String actors){
//        String method = "findTodoFiles";
//        int envolopeVersion = SoapEnvelope.VER11;
//        SoapObject request = new SoapObject(NAME_SPACE, method);
//        //{0A2FF324-FFFF-FFFF-CBC7-A9A80000002A}主办文    ；      {0A2FF324-FFFF-FFFF-8885-740500000008}
//        String value="{\"instanceguid\":\""+id+"\",\"userid\":\"" + userid + "\",\"stalength\":"+page+",\"returnlength\":10,\"actors\":\""+
//                actors+"\",\"type\":\""+type+"\"}";
//        request.addProperty("in0", value);
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(envolopeVersion);
//        envelope.setOutputSoapObject(request);
//        envelope.dotNet = false;
//        HttpTransportSE se = new HttpTransportSE(HOST_TODO);
////        String soapAction = "http://mobile.risesoft.net/login";
//        try {
//            se.call(null, envelope);
//            SoapObject response1 = (SoapObject) envelope.bodyIn;
//            SoapPrimitive o = (SoapPrimitive) response1.getProperty(0);
//            String a = o.getValue().toString();
//            return a;
//        } catch (IOException | XmlPullParserException|ClassCastException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

    //分类 获取待办
    public String findTodoFiles(String id, String type, String page, String actors) {
        String viewGUID = "";
        String workflowGUID = "";
        switch (type) {
            case "主办文":
                viewGUID = "{0A2FF41F-FFFF-FFFF-B7C0-27C00000000D}";
                workflowGUID = "{A9522312-FFFF-FFFF-9538-050B00000002}";
                break;
            case "局内传文":
                viewGUID = "{0A2FF40B-FFFF-FFFF-A033-B83600000005}";
                workflowGUID = "{A9522312-FFFF-FFFF-9529-B92C00000001}";
                break;
            case "一般发文":
                viewGUID = "{A952230B-0000-0000-7EE5-364900000020}";
                workflowGUID = "{A9522310-FFFF-FFFF-8778-020E0000008B}";
                break;
            case "指标文":
                viewGUID = "{A952230B-FFFF-FFFF-8EA3-F93400000017}";
                workflowGUID = "{A952230B-0000-0000-7D54-8A5700000335}";
                break;
            case "结余资金":
                viewGUID = "{0A2FCA25-0000-0000-1BC0-3262FFFF982E}";
                workflowGUID = "{0A2FCA25-0000-0000-1BC0-3262FFFF982E}";
                break;
            case "市转文":
                viewGUID = "{A9522312-FFFF-FFFF-AF67-3D0B000001B0}";
                workflowGUID = "{A952230B-FFFF-FFFF-8121-FCF400000001}";
                break;

        }
        String value = "{\"instanceguid\":\"" + id + "\",\"userid\":\"" + userid + "\",\"stalength\":" + page + ",\"returnlength\":10,\"actors\":\"" +
                actors + "\",\"type\":\"" + type + "\",\"viewGUID\":\"" + viewGUID + "\",\"workflowGUID\":\"" + workflowGUID + "\"}";

        return WebServiceManager.getInstance().connect("findTodoFiles", HOST_TODO, value);
    }

    public String resetPass(String name, String newPass) {//user userid password

        String value = "{\"user\":\"" + name + "\",\"userid\":\"" + userid + "\",\"password\":\"" + newPass + "\"}";
        return WebServiceManager.getInstance().connect("updatepassword", HOST_USER, value);
    }


    public String getUserInfo(String userid){
        String value = "{\"userid\":\""+ userid  + "\"}";
        return WebServiceManager.getInstance().connect("userinfos", HOST_TODO, value);
    }

    /**
     * 公文发送
     *
     * @param id            公文id
     * @param actionId      发送动作
     * @param recipientguid 收件人
     * @return
     */
    public String sendInstance(String id, String actionId, String recipientguid) {
        String value = "{\"userid\":\"" + userid + "\"," +
                "\"id\":\"" + id + "\",\"actionguid\":\"" + actionId + "\"," +
                "\"recipientguid\":\"" + recipientguid + "\"," +
                "\"issms\":\"off\",\"smsMessage\":\"\"}";
        String method = "sendInstance";
        return WebServiceManager.getInstance().connect(method, HOST_WORKFLOW, value);
    }

    public String sendInstanceUser(String id, String actionId) {
        String value = "{\"userid\":\"" + userid + "\"," +
                "\"id\":\"" + id + "\",\"actionguid\":\"" + actionId + "\"," +
                "\"recipientguid\":\"" + "\"," +
                "\"issms\":\"off\",\"smsMessage\":\"\"}";
        String method = "sendInstanceUser";
        return WebServiceManager.getInstance().connect(method, HOST_WORKFLOW, value);
    }

    /**
     * 完成接口
     *
     * @param actionguid 动作id
     * @param id         公文id
     * @return
     */
    public String finishInstance(String actionguid, String id) {
        String value = "{\"userid\":\"" + userid + "\"," +
                "\"id\":\"" + id + "\",\"actionguid\":\"" + actionguid + "\"}";
        String method = "sendInstance";
        int envolopeVersion = SoapEnvelope.VER11;
        SoapObject request = new SoapObject(NAME_SPACE, method);
        //{0A2FF324-FFFF-FFFF-CBC7-A9A80000002A}主办文    ；      {0A2FF324-FFFF-FFFF-8885-740500000008}
//        String value="{\"userid\":\"7cd7db16c48a5fa039ab300c79d46bc5000000\"}";
        request.addProperty("in0", value);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(envolopeVersion);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = false;
        HttpTransportSE se = new HttpTransportSE(HOST_WORKFLOW);
//        String soapAction = "http://mobile.risesoft.net/login";
        try {
            se.call(null, envelope);
            SoapFault fault = (SoapFault) envelope.bodyIn;
            SoapObject response1 = (SoapObject) envelope.bodyIn;
            SoapPrimitive o = (SoapPrimitive) response1.getProperty(0);

            String a = o.getValue().toString();
            return a;
        } catch (IOException | XmlPullParserException | ClassCastException e) {

            e.printStackTrace();
        }
        return "";
    }

    //获取个人意见
    public String getPersonComment() {
        String method = "getPersonComment";
        int envolopeVersion = SoapEnvelope.VER11;
        SoapObject request = new SoapObject(NAME_SPACE, method);
        //{0A2FF324-FFFF-FFFF-CBC7-A9A80000002A}主办文    ；      {0A2FF324-FFFF-FFFF-8885-740500000008}
        String value = "{\"userid\":\"" + userid + "\"}";
        request.addProperty("in0", value);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(envolopeVersion);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = false;
        HttpTransportSE se = new HttpTransportSE(HOST_WORKFLOW);
//        String soapAction = "http://mobile.risesoft.net/login";
        try {
            se.call(null, envelope);
            SoapObject response1 = (SoapObject) envelope.bodyIn;
            SoapPrimitive o = (SoapPrimitive) response1.getProperty(0);
            String a = o.getValue().toString();
            return a;
        } catch (IOException | XmlPullParserException | ClassCastException e) {
            e.printStackTrace();
        }
        return "";
    }

    //填写意见 不能修改 只能新建
//    public String writeComment(String id,String type,String filename,String filepath,String opinion,String comment_guid){
//        String method = "writeComment";
//        int envolopeVersion = SoapEnvelope.VER11;
//        SoapObject request = new SoapObject(NAME_SPACE, method);
//        //{0A2FF324-FFFF-FFFF-CBC7-A9A80000002A}主办文    ；      {0A2FF324-FFFF-FFFF-8885-740500000008}
//        CommentBean commentBean=new CommentBean(id,type,filename,filepath,opinion,comment_guid,userid);
//        String value=new Gson().toJson(commentBean);
//        request.addProperty("in0", value);
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(envolopeVersion);
//        envelope.setOutputSoapObject(request);
//        envelope.dotNet = false;
//        MyGBKSe se = new MyGBKSe(HOST_WORKFLOW);
////        String soapAction = "http://mobile.risesoft.net/login";
//        try {
//            se.call(null, envelope);
//            SoapObject response1 = (SoapObject) envelope.bodyIn;
//            SoapPrimitive o = (SoapPrimitive) response1.getProperty(0);
//            String a = o.getValue().toString();
//            return a;
//        } catch (IOException | XmlPullParserException|ClassCastException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

    public String getCommentList(String instanceGuid, String userId) {
        String value = "{\"instanceguid\":\"" + instanceGuid + "\",\"userid\":\"" + userId + "\"}";
        return WebServiceManager.getInstance().connect("userinfo", HOST_TODO, value);
    }

    public String writeComment(String id, String opinion, String comment_guid, String row_guid) {
        CommentBean commentBean = new CommentBean(id, opinion, comment_guid, userid, row_guid);
        String value = new Gson().toJson(commentBean);
        String method = "writeComment";
        return WebServiceManager.getInstance().connect(method, HOST_WORKFLOW, value);
    }

    public String writeComment(String id, String type, String filename, String filepath, String opinion, String comment_guid) {
        CommentBean commentBean = new CommentBean(id, type, filename, filepath, opinion, comment_guid, userid);
        String value = new Gson().toJson(commentBean);
        String method = "writeComment";
        return WebServiceManager.getInstance().connect(method, HOST_WORKFLOW, value);
    }

    //删除意见
//    public String deleteComment(String id,String comment_guid){
//        String method = "deleteComment";
//        int envolopeVersion = SoapEnvelope.VER11;
//        SoapObject request = new SoapObject(NAME_SPACE, method);
//        //{0A2FF324-FFFF-FFFF-CBC7-A9A80000002A}主办文    ；      {0A2FF324-FFFF-FFFF-8885-740500000008}
//        DeleteCommentBean commentBean=new DeleteCommentBean(id,comment_guid,userid);
//        String value=new Gson().toJson(commentBean);
//        request.addProperty("in0", value);
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(envolopeVersion);
//        envelope.setOutputSoapObject(request);
//        envelope.dotNet = false;
//        HttpTransportSE se = new HttpTransportSE(HOST_WORKFLOW);
////        String soapAction = "http://mobile.risesoft.net/login";
//        try {
//            se.call(null, envelope);
//            SoapObject response1 = (SoapObject) envelope.bodyIn;
//            SoapPrimitive o = (SoapPrimitive) response1.getProperty(0);
//            String a = o.getValue().toString();
//            return a;
//        } catch (IOException | XmlPullParserException|ClassCastException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

    public String deleteComment(String id, String comment_guid) {
        DeleteCommentBean commentBean = new DeleteCommentBean(id, comment_guid, userid);
        String value = new Gson().toJson(commentBean);
        String method = "deleteComment";
        return WebServiceManager.getInstance().connect(method, HOST_WORKFLOW, value);
    }

    public String deleteComment(String rowId) {
        DeleteCommentBean commentBean = new DeleteCommentBean(rowId);
        String value = new Gson().toJson(commentBean);
        String method = "deleteComment";
        return WebServiceManager.getInstance().connect(method, HOST_WORKFLOW, value);
    }

    String userid = "";

    //获取监控已办监控在办的权限
    public String getUserMenus(String deedboxGUID) {
        String value = "{\"userid\":\"" + userid + "\"," +
                "\"deedboxGUID\":\"" + deedboxGUID + "\"}";
        String method = "getUserMenus";
        return WebServiceManager.getInstance().connect(method, HOST_TODO, value);
    }


//    public String getactionguid(String actionguid,String id){
//        String method = "getactionguid";
//        int envolopeVersion = SoapEnvelope.VER11;
//        SoapObject request = new SoapObject(NAME_SPACE, method);
//        //{0A2FF324-FFFF-FFFF-CBC7-A9A80000002A}主办文    ；      {0A2FF324-FFFF-FFFF-8885-740500000008}
//        String value="{\"userid\":\""+userid+"\",\"actionguid\":\""+actionguid+"\"" +
//                ",\"id\":\""+id+"\",\"status\":\"\"}";
//        request.addProperty("in0", value);
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(envolopeVersion);
//        envelope.setOutputSoapObject(request);
//        envelope.dotNet = false;
//        HttpTransportSE se = new HttpTransportSE(HOST_TODO);
////        String soapAction = "http://mobile.risesoft.net/login";
//        try {
//            se.call(null, envelope);
//            SoapObject response1 = (SoapObject) envelope.bodyIn;
//            SoapPrimitive o = (SoapPrimitive) response1.getProperty(0);
//            String a = o.getValue().toString();
//            return a;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

    /**
     * @param actionguid 动作id
     * @param id         公文id
     * @return
     */
    public String getactionguid(String actionguid, String id) {
        String value = "{\"userid\":\"" + userid + "\",\"actionguid\":\"" + actionguid + "\"" +
                ",\"id\":\"" + id + "\",\"status\":\"\"}";
        String method = "getactionguid";
        return WebServiceManager.getInstance().connect(method, HOST_WORKFLOW, value);
    }

    //公文发送 userid 、id公文id、actionguid 发送动作、recipientguid、issms、smsMessage
    public String sendInstance(String value) {
        String method = "sendInstance";
        int envolopeVersion = SoapEnvelope.VER11;
        SoapObject request = new SoapObject(NAME_SPACE, method);
        //{0A2FF324-FFFF-FFFF-CBC7-A9A80000002A}主办文    ；      {0A2FF324-FFFF-FFFF-8885-740500000008}
//        String value="{\"userid\":\"7cd7db16c48a5fa039ab300c79d46bc5000000\"}";
        request.addProperty("in0", value);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(envolopeVersion);
        envelope.setOutputSoapObject(request);
        envelope.dotNet = false;
        HttpTransportSE se = new HttpTransportSE(HOST_WORKFLOW);
//        String soapAction = "http://mobile.risesoft.net/login";
        try {
            se.call(null, envelope);
            SoapObject response1 = (SoapObject) envelope.bodyIn;
            SoapPrimitive o = (SoapPrimitive) response1.getProperty(0);
            String a = o.getValue().toString();
            return a;
        } catch (IOException | XmlPullParserException | ClassCastException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void findToDoFileInfo1() {
        String namespace = NAME_SPACE;
        String methodName = "findTodoFileInfo";
        SoapObject soapObject = new SoapObject(namespace, methodName);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        soapObject.addProperty("id", "{0A2FF324-FFFF-FFFF-8885-740500000008}");//带参数的方法调用，若调用无参数的，则无需此句
        soapObject.addProperty("userId", "7cd7db16c48a5fa039ab300c79d46bc5000000");
        String value = "{\"id\":\"{0A2FF324-FFFF-FFFF-8885-740500000008}\",\"userid\":\"" + userid + "\"}";
//        try {
//            URLEncoder.encode(value,"utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        soapObject.addProperty("jsonStr", value);

        envelope.dotNet = false;

        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTranstation = new HttpTransportSE(HOST_TODO);


        try {
            httpTranstation.call(null, envelope);
            Object result = envelope.getResponse();

            String str = (String) result.toString();//获得请求的字符串
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }


//        WebserviceClient client = new WebserviceClient("http://www.xxxx.com:8088/apk/webservice", "regionWs", "allRegion",
//                null, List.class, Region.class);
//        List<Region> list = client.execute();// 执行以下webservice客户端，就得到了一个List，轻松搞定


    }


    private class CommentBean {
        String id;
        String type;
        String filename;
        String filepath;
        String opinion;
        String comment_guid;
        String userid;
        String row_guid;

        public CommentBean(String id, String opinion, String comment_guid, String userid, String row_guid) {
            this.id = id;
            this.opinion = opinion;
            this.comment_guid = comment_guid;
            this.userid = userid;
            this.row_guid = row_guid;
            this.type = "2";
        }

        public CommentBean(String id, String type, String filename, String filepath, String opinion, String comment_guid, String userid) {
            this.id = id;
            this.type = type;
            this.filename = filename;
            this.filepath = filepath;
            this.opinion = opinion;
            this.comment_guid = comment_guid;
            this.userid = userid;
        }
    }

    private class DeleteCommentBean {
        String id;
        String comment_guid;
        String userid;
        String row_guid;

        public DeleteCommentBean(String row_guid) {
            this.row_guid = row_guid;
        }

        public DeleteCommentBean(String id, String comment_guid, String userid) {
            this.id = id;
            this.comment_guid = comment_guid;
            this.userid = userid;
        }
    }


//    public void login(String name, String password, PrintWriter printWriter){
//        String method = "login";
//        int envolopeVersion = SoapEnvelope.VER11;
//        SoapObject request = new SoapObject(NAME_SPACE, method);
//        request.addProperty("username", name);
//        request.addProperty("password", password);
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(envolopeVersion);
//        envelope.setOutputSoapObject(request);
//        envelope.dotNet = true;
//        HttpTransportSE se = new HttpTransportSE(HOST_TODO);
//        String soapAction = "http://mobile.risesoft.net/login";
//        try {
//            se.call(soapAction, envelope);
//            SoapObject response = (SoapObject) envelope.bodyIn;
//            SoapPrimitive o = (SoapPrimitive) response.getProperty(0);
////                System.out.println(o.toString());
//            String  a= o.getValue().toString();
////               String ss=new String (a.getBytes("GBK"),"UTF-8");
//            write(printWriter, a);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            write(printWriter,AAContants.globalbase.Z_FALSE);
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//            write(printWriter,AAContants.globalbase.Z_FALSE);
//        }
//
//    }

}
