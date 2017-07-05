package emulator.manger;

import com.google.gson.Gson;
import emulator.data.Config;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

/**
 * Created by dsm_025 on 2017-04-30.
 */
public class JSONManager {
    public static String readConfigFile(){
        return readTextFile("config.txt");
    }

    public static String readDefConfigFile(){
        return readTextFile("default.txt");
    }

    private static String readTextFile(String fileName){
        String result = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File("data/" + fileName)));

            String tmp;
            while ((tmp = in.readLine()) != null) {
                result += tmp;
            }

            Config.configFile = new Gson().fromJson(result, Config.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void parseAndSave(String json){
        Config.configFile = new Gson().fromJson(json, Config.class);
    }

    public static void bindJsonFile(Config config) {
        try {
            //json 파일 초기화
            PrintWriter writer = new PrintWriter(new File("data/config.txt"));
            writer.print("");
            writer.close();

            String json = new Gson().toJson(config);
            BufferedWriter out = new BufferedWriter(new FileWriter(new File("data/config.txt")));
            out.write(json);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String bindConfigMsg(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", "config");
        jsonObject.put("data", data);
        return jsonObject.toString();
    }
}
