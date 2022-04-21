package org.az.climator.experimental;

import lombok.Data;
import org.jose4j.json.internal.json_simple.JSONArray;
import org.jose4j.json.internal.json_simple.JSONObject;

@Data
public class DataDTO {

    private String filename;
    private JSONObject data;
}
