package stream.mockdata;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import stream.beans.Car;
import stream.beans.Person;

public class MockData {

	public static List<Person> getPeople() throws IOException {
		Type listType = new TypeToken<List<Person>>() {
		}.getType();
		InputStream inputStream = MockData.class.getClassLoader().getResourceAsStream("people.json");
		String peopleString = IOUtils.toString(inputStream);
		List<Person> peopleList = new Gson().fromJson(peopleString, listType);
		return peopleList;
	}

	public static List<Car> getCars() throws IOException {
		Type listType = new TypeToken<List<Car>>() {
		}.getType();
		InputStream inputStream = MockData.class.getClassLoader().getResourceAsStream("cars.json");
		String carString = IOUtils.toString(inputStream);
		List<Car> cars = new Gson().fromJson(carString, listType);
		return cars;
	}

	public static void main(String[] args) throws IOException {
		List<Person> people = getPeople();

		// Lecture 1
		// Imperative style of coding
		imerativeApproach(people);
		// Declarative style using streams
		declarativeApproachUsingStream(people);

		// Lecture 2
		// IntStream.range(), IntStream.rangeClosed()
		range();
		// Iterating list using range
		rangeIteratingLists(people);
		// IntStream iterate
		intStreamIterate();

		// Lecture 3
		// min()
		min();
		// max()
		max();

		// Lecture 4
		// distinct()
		distinct();
		// distinctWithSet()
		distinctWithSet();
	}

	// Lecture 1
	private static void declarativeApproachUsingStream(List<Person> people) {
		people.stream().filter(person -> person.getAge() <= 18).limit(20).collect(Collectors.toList())
				.forEach(System.out::println);
	}

	private static void imerativeApproach(List<Person> people) {
		System.out.println("imerativeApproach");
		List<Person> youngPeople = new ArrayList<Person>();
		int count = 0;
		int limit = 20;

		for (Person person : people) {
			if (person.getAge() <= 18) {
				youngPeople.add(person);
				count++;
				if (count == limit) {
					break;
				}
			}
		}

		for (Person person : youngPeople) {
			System.out.println(person);
		}
	}

	// Lecture 2
	private static void range() {
		System.out.println("range");
		for (int i = 0; i < 10; i++) {
			System.out.println(i);
		}
		System.out.println("Exclusive");
		IntStream.range(0, 10).forEach(System.out::println);
		System.out.println("Inclusive");
		IntStream.rangeClosed(0, 10).forEach(System.out::println);
	}

	private static void rangeIteratingLists(List<Person> people) {
		System.out.println("rangeIteratingLists");
		IntStream.range(0, people.size()).forEach(index -> {
			Person person = people.get(index);
			System.out.println(person);
		});
	}

	private static void intStreamIterate() {
		System.out.println("intStreamIterate");
		IntStream.iterate(0, operator -> operator + 1).filter(number -> number % 2 == 0).limit(20)
				.forEach(System.out::println);
	}

	// Lecture 3
	private static void min() {
		System.out.println("min");
		List<Integer> integers = new ArrayList<>(
				Arrays.asList(4, 65, 3, 44, 6, 74, 5, 3, 45, 4, 43, 15, 96, 563, 10, 74));
		int min = integers.stream().min(Comparator.naturalOrder()).get();
		System.out.println(min);
	}

	private static void max() {
		System.out.println("max");
		List<Integer> integers = new ArrayList<>(
				Arrays.asList(4, 65, 3, 44, 6, 74, 5, 3, 45, 4, 43, 15, 96, 563, 10, 74));
		int max = integers.stream().max(Comparator.naturalOrder()).get();
		System.out.println(max);
	}

	// Lecture 4
	private static void distinct() {
		System.out.println("distinct");
		List<Integer> integers = new ArrayList<>(
				Arrays.asList(4, 65, 3, 44, 6, 74, 5, 3, 45, 4, 43, 15, 96, 563, 10, 74));
		integers.stream().distinct().collect(Collectors.toList()).forEach(System.out::println);
	}

	private static void distinctWithSet() {
		System.out.println("distinctWithSet");
		List<Integer> integers = new ArrayList<>(
				Arrays.asList(4, 65, 3, 44, 6, 74, 5, 3, 45, 4, 43, 15, 96, 563, 10, 74));
		integers.stream().collect(Collectors.toSet()).forEach(System.out::println);
	}

}
