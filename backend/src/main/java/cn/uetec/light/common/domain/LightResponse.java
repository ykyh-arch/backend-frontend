package cn.uetec.light.common.domain;

import java.util.HashMap;

public class LightResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    public LightResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public LightResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    @Override
    public LightResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
