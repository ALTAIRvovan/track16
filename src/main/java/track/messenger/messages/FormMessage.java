package track.messenger.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by altair on 23.11.16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FormMessage extends Message {
    private Map<String, String> fields = new HashMap<>();

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    public String getField(String name) {
        return fields.get(name);
    }

    public void setField(String name, String value) {
        fields.put(name, value);
    }
}
