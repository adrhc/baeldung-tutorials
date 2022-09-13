package com.baeldung.jackson.streaming;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class StreamingAPIUnitTest {

    @Test
    public void givenJsonGenerator_whenAppendJsonToIt_thenGenerateJson() throws IOException {
        // given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        JsonFactory jfactory = new JsonFactory(new ObjectMapper());
        JsonGenerator jGenerator = jfactory.createGenerator(stream, JsonEncoding.UTF8);

        // when
        jGenerator.writeStartObject();
        jGenerator.writeStringField("name", "Tom");
        jGenerator.writeNumberField("age", 25);

        jGenerator.writeFieldName("objects");
        jGenerator.writeStartArray();
        IntStream.range(0, 3).forEach(it -> {
            try {
                jGenerator.writePOJO(new HashMap<String, String>() {{
                    put("key1", "value1");
                    put("key2", "value2");
                }});
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        jGenerator.writeEndArray();

        jGenerator.writeFieldName("strings");
        jGenerator.writeStartArray();
        jGenerator.writeString("textX");
        jGenerator.writeString("textY");
        IntStream.range(0, 3).forEach(it -> {
            try {
                jGenerator.writeString("text-" + it);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        jGenerator.writeEndArray();

        jGenerator.writeNullField("nullField");
        jGenerator.writeEndObject();
        jGenerator.close();

        // then
        String json = stream.toString(StandardCharsets.UTF_8.name());
        // assertEquals(json, "{\"name\":\"Tom\",\"age\":25,\"address\":[\"Poland\",\"5th avenue\"]}");
    }

    @Test
    public void givenJson_whenReadItUsingStreamAPI_thenShouldCreateProperJsonObject() throws IOException {
        // given
        String json = "{\"name\":\"Tom\",\"age\":25,\"address\":[\"Poland\",\"5th avenue\"]}";
        JsonFactory jfactory = new JsonFactory();
        JsonParser jParser = jfactory.createParser(json);

        String parsedName = null;
        Integer parsedAge = null;
        List<String> addresses = new LinkedList<>();

        // when
        while (jParser.nextToken() != JsonToken.END_OBJECT) {

            String fieldname = jParser.getCurrentName();
            if ("name".equals(fieldname)) {
                jParser.nextToken();
                parsedName = jParser.getText();

            }

            if ("age".equals(fieldname)) {
                jParser.nextToken();
                parsedAge = jParser.getIntValue();

            }

            if ("address".equals(fieldname)) {
                jParser.nextToken();

                while (jParser.nextToken() != JsonToken.END_ARRAY) {
                    addresses.add(jParser.getText());
                }
            }

        }
        jParser.close();

        // then
        assertEquals(parsedName, "Tom");
        assertEquals(parsedAge, (Integer) 25);
        assertEquals(addresses, Arrays.asList("Poland", "5th avenue"));

    }

    @Test
    public void givenJson_whenWantToExtractPartOfIt_thenShouldExtractOnlyNeededFieldWithoutGoingThroughWholeJSON() throws IOException {
        // given
        String json = "{\"name\":\"Tom\",\"age\":25,\"address\":[\"Poland\",\"5th avenue\"]}";
        JsonFactory jfactory = new JsonFactory();
        JsonParser jParser = jfactory.createParser(json);

        String parsedName = null;
        Integer parsedAge = null;
        List<String> addresses = new LinkedList<>();

        // when
        while (jParser.nextToken() != JsonToken.END_OBJECT) {

            String fieldname = jParser.getCurrentName();

            if ("age".equals(fieldname)) {
                jParser.nextToken();
                parsedAge = jParser.getIntValue();
                return;
            }

        }
        jParser.close();

        // then
        assertNull(parsedName);
        assertEquals(parsedAge, (Integer) 25);
        assertTrue(addresses.isEmpty());

    }
}
