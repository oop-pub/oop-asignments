package input;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;

public final class ObjectListFactory {

    private static ObjectListFactory instance;

    private ObjectListFactory() {
    }

    /**
     *
     * @return
     */
    public static ObjectListFactory getInstance() {
        if (instance == null) {
            instance = new ObjectListFactory();
        }
        return instance;
    }

    /**
     *
     * @param location
     * @param objectMapper
     * @param cls
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> List<T> getObjectList(final JsonNode location, final ObjectMapper objectMapper,
                                     final Class<T> cls) throws IOException {

        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, cls);

        return objectMapper.readValue(
                location.toString(),
                javaType
        );
    }
}
