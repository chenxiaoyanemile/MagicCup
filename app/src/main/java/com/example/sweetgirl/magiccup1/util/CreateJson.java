package com.example.sweetgirl.magiccup1.util;

import com.example.sweetgirl.magiccup1.model.TallScene;

import org.json.JSONObject;

/**
 * Created by sweetgirl on 2016/12/14.
 */

public class CreateJson {


    //json对象

    public static String createJson(TallScene scene) {


        // TODO Auto-generated method stub

        try {
            // 第一个场景的json
            JSONObject firstJson = new JSONObject();

            firstJson.put("sexman", scene.getFirstScene().getSexman());
            firstJson.put("sexwoman", scene.getFirstScene().getSexwoman());

            // 第二个场景的json
            JSONObject secondJson = new JSONObject();
            secondJson.put("action", scene.getSecondScene().getAction());
            secondJson.put("background", scene.getSecondScene().getBackground());

            // 第三个场景的json
            JSONObject thirdJson = new JSONObject();
            thirdJson.put("background", scene.getThirdScene().getBackground());
            thirdJson.put("action", scene.getThirdScene().getAction());
            thirdJson.put("text", scene.getThirdScene().getText());
            thirdJson.put("time", scene.getThirdScene().getTime());
            // 第四个场景的json
            JSONObject fourScene = new JSONObject();
            fourScene.put("injection", scene.getFourScene().getInjection());
            fourScene.put("text", scene.getFourScene().getText());

            // 所有场景的json
            JSONObject allSceneJson = new JSONObject();

            allSceneJson.put("firstscene", firstJson);
            allSceneJson.put("secondscene",secondJson);
            allSceneJson.put("thirdscene",thirdJson);
            allSceneJson.put("fourthscene",fourScene);

            L.i("这个是所有场景的json", allSceneJson.toString() );
            return allSceneJson.toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

   /* //json数组
    public static String createJsonArray() {

        // TODO Auto-generated method stub

        List<Scene> persons = getTestValues();
        try {
            JSONObject jsonObject = new JSONObject();

            JSONArray array = new JSONArray();
            for (int i = 0; i < persons.size(); i++) {
                JSONObject scene = new JSONObject();
                Scene p = persons.get(i);
                scene.put("id", p.getId());
                scene.put("name", p.getName());
                scene.put("age", p.getConstellation());
                array.put(scene);
            }
            jsonObject.put("scene", array);
            System.out.println(jsonObject.toString());
            return jsonObject.toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public static String createJsonString() {
        // TODO Auto-generated method stub

        List<Scene> scenes = getTestValues();
        JSONStringer jsonStringer = new JSONStringer();
        try {
            jsonStringer.object();
            jsonStringer.key("persons");
            jsonStringer.array();

            for (int i = 0; i < scenes.size(); i++) {
                Scene scene = scenes.get(i);

                jsonStringer.object();
                jsonStringer.key("id").value(scene.getId());
                jsonStringer.key("name").value(scene.getName());
                jsonStringer.key("depiction").value(scene.getDepiction());
                jsonStringer.endObject();
            }
            jsonStringer.endArray();

            jsonStringer.endObject();

            System.out.println(jsonStringer.toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonStringer.toString();
    }*/
}
