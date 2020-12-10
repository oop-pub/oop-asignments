package input;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.util.List;

public final class ObjectListFactory {

    private static ObjectListFactory instance;

    private ObjectListFactory() {
    }

    public static ObjectListFactory getInstance() {
        if(instance == null) {
            instance = new ObjectListFactory();
        }
        return instance;
    }

    public <T> List<T> getObjectList(JsonNode location, ObjectMapper objectMapper, Class<T> cls) throws IOException {

        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, cls);

        return objectMapper.readValue(
                location.toString(),
                javaType
        );
    }
}
