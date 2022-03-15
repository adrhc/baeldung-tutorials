package com.baeldung.protobuf;

import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ProtobufUnitTest {
	@Test
	public void givenGeneratedProtobufClass_whenCreateClass_thenShouldCreateJavaInstance() {
		//when
		String email = "j@baeldung.com";
		int id = new Random().nextInt();
		String name = "Michael Program";
		Person person =
				Person.newBuilder()
						.setId(id)
						.setName(name)
						.setEmail(email)
						.build();
		//then
		assertEquals(person.getEmail(), email);
		assertEquals(person.getId(), id);
		assertEquals(person.getName(), name);
	}

	@Test
	public void givenAddressBookWithOnePerson_whenSaveAsAFile_shouldLoadFromFileToJavaClass() throws IOException {
		//given
		String email = "j@baeldung.com";
		int id = 13;
		String name = "Michael Program";
		Person person =
				Person.newBuilder()
						.setId(id)
						.setName(name)
						.setEmail(email)
						.build();

		//when
		AddressBook addressBook = AddressBook.newBuilder().addPeople(person).build();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		addressBook.writeTo(outputStream);

		String base64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());
		System.out.println(base64);
//		base64 = "CiMKD01pY2hhZWwgUHJvZ3JhbRANGg5qQGJhZWxkdW5nLmNvbQ==";
		byte[] bytes = Base64.getDecoder().decode(base64);

		//then
		AddressBook deserialized =
				AddressBook.newBuilder().mergeFrom(bytes).build();
		assertEquals(deserialized.getPeople(0).getEmail(), email);
		assertEquals(deserialized.getPeople(0).getId(), id);
		assertEquals(deserialized.getPeople(0).getName(), name);
	}
}
