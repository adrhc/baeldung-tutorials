package com.baeldung.bval.avro.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.avro.Schema;
import org.apache.avro.generic.IndexedRecord;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotGeneratedAvroHttpRequest implements IndexedRecord {
	public long requestTime;
	public NotGeneratedClientIdentifier clientIdentifier;
	public List<CharSequence> employeeNames;
	public Active active;

	// Used by DatumWriter.  Applications should not call.
	public java.lang.Object get(int field$) {
		switch (field$) {
			case 0: return requestTime;
			case 1: return clientIdentifier;
			case 2: return employeeNames;
			case 3: return active;
			default: throw new org.apache.avro.AvroRuntimeException("Bad index");
		}
	}

	// Used by DatumReader.  Applications should not call.
	@SuppressWarnings(value="unchecked")
	public void put(int field$, java.lang.Object value$) {
		switch (field$) {
			case 0: requestTime = (java.lang.Long)value$; break;
			case 1: clientIdentifier = (NotGeneratedClientIdentifier)value$; break;
			case 2: employeeNames = (java.util.List<java.lang.CharSequence>)value$; break;
			case 3: active = (Active)value$; break;
			default: throw new org.apache.avro.AvroRuntimeException("Bad index");
		}
	}

	@Override
	public Schema getSchema() {
		return null;
	}
}
