package com.baeldung.bval.avro.util.serealization;

import com.baeldung.bval.avro.model.Active;
import com.baeldung.bval.avro.model.NotGeneratedAvroHttpRequest;
import com.baeldung.bval.avro.model.NotGeneratedClientIdentifier;
import com.baeldung.bval.avro.util.model.AvroHttpRequest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class GenericAvroSerializerTest {
	private final GenericAvroSerializer serializer = new GenericAvroSerializer(AvroHttpRequest.SCHEMA$);

	@Test
	void serealizeAvroHttpRequestJSON() {
		List<CharSequence> employeeNames = new ArrayList<>();
		employeeNames.add("gigi");
		employeeNames.add("kent");

		NotGeneratedAvroHttpRequest request = new NotGeneratedAvroHttpRequest(
				System.currentTimeMillis(),
				new NotGeneratedClientIdentifier("localhost", "127.0.0.1"),
				employeeNames, Active.YES);

		serializer.serializeAvroHttpRequestJSON(request);
	}
}