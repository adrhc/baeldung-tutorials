package com.baeldung.bval.avro.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.avro.Schema;
import org.apache.avro.generic.IndexedRecord;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotGeneratedClientIdentifier implements IndexedRecord {
	@Deprecated public java.lang.CharSequence hostName;
	@Deprecated public java.lang.CharSequence ipAddress;

	public java.lang.Object get(int field$) {
		switch (field$) {
			case 0: return hostName;
			case 1: return ipAddress;
			default: throw new org.apache.avro.AvroRuntimeException("Bad index");
		}
	}

	// Used by DatumReader.  Applications should not call.
	@SuppressWarnings(value="unchecked")
	public void put(int field$, java.lang.Object value$) {
		switch (field$) {
			case 0: hostName = (java.lang.CharSequence)value$; break;
			case 1: ipAddress = (java.lang.CharSequence)value$; break;
			default: throw new org.apache.avro.AvroRuntimeException("Bad index");
		}
	}

	@Override
	public Schema getSchema() {
		return null;
	}
}
