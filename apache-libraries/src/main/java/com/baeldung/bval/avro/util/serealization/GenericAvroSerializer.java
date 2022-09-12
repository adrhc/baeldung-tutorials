package com.baeldung.bval.avro.util.serealization;

import com.baeldung.bval.avro.util.model.AvroHttpRequest;
import lombok.RequiredArgsConstructor;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.IndexedRecord;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
public class GenericAvroSerializer {
	private static final Logger logger = LoggerFactory.getLogger(GenericAvroSerializer.class);
	private final Schema schema;

	public <T extends IndexedRecord> byte[] serializeAvroHttpRequestJSON(T request) {
		DatumWriter<T> writer = new GenericDatumWriter<>(schema);
		byte[] data = new byte[0];
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Encoder jsonEncoder;
		try {
			jsonEncoder = EncoderFactory.get()
					.jsonEncoder(AvroHttpRequest.getClassSchema(), stream);
			writer.write(request, jsonEncoder);
			jsonEncoder.flush();
			data = stream.toByteArray();
		} catch (IOException e) {
			logger.error("Serialization error " + e.getMessage());
		}
		return data;
	}
}
