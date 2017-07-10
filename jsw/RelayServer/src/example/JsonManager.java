package example;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dsm_045 on 2017-07-10.
 */
public class JsonManager {
    public static String makeRespInfo(Info info) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "response");
        jsonObject.put("msg", new JSONObject().put("id", info.getId()).put("ip", info.getIp()));
        return jsonObject.toString();
    }

    public static String makeRespInfoList(ArrayList<Info> infoList) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("type", "response");

        for(Info info : infoList){
            JSONObject object = new JSONObject();
            object.put("id", info.getId());
            object.put("ip", info.getIp());
            jsonArray.put(object);
        }
        jsonObject.put("msg", jsonArray);
        return jsonObject.toString();
    }

    public static String makeResp(String message){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "response");
        jsonObject.put("msg", message);
        return jsonObject.toString();
    }
}
