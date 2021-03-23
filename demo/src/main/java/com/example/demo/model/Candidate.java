
package com.example.demo.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "created_at",
    "emails",
    "id",
    "name",
    "phones",
    "photo_thumb_url",
    "referrer",
    "source",
    "updated_at"
})
public class Candidate {

    @JsonProperty("created_at")
    public String createdAt;
    @JsonProperty("emails")
    public List<String> emails = null;
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("phones")
    public List<Object> phones = null;
    @JsonProperty("photo_thumb_url")
    public String photoThumbUrl;
    @JsonProperty("referrer")
    public Object referrer;
    @JsonProperty("source")
    public String source;
    @JsonProperty("updated_at")
    public String updatedAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
