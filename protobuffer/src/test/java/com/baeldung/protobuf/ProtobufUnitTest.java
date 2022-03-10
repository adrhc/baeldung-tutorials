package com.baeldung.protobuf;

import org.junit.After;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class ProtobufUnitTest {
	private final String filePath = "address_book";

	@After
	public void cleanup() throws IOException {
		Files.deleteIfExists(Paths.get(filePath));
	}

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
		int id = new Random().nextInt();
		String name = "Michael Program";
		Person person =
				Person.newBuilder()
						.setId(id)
						.setName(name)
						.setEmail(email)
						.build();

		//when
		AddressBook addressBook = AddressBook.newBuilder().addPeople(person).build();
		FileOutputStream fos = new FileOutputStream(filePath);
		addressBook.writeTo(fos);
		fos.close();

		//then
		FileInputStream fis = new FileInputStream(filePath);
		AddressBook deserialized =
				AddressBook.newBuilder().mergeFrom(fis).build();
		fis.close();
		assertEquals(deserialized.getPeople(0).getEmail(), email);
		assertEquals(deserialized.getPeople(0).getId(), id);
		assertEquals(deserialized.getPeople(0).getName(), name);
	}
}
