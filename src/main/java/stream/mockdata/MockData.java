package stream.mockdata;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import stream.beans.Car;
import stream.beans.Person;
import stream.beans.PersonDTO;

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
		List<Car> cars = getCars();

		// Lecture 1
		// Imperative style of coding
		imperativeApproach(people);
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

		// Lecture 5
		// Filter
		understandingFilter(cars);
		// Map
		outFirstMapping(people);
		// mapToDouble(), average()
		averageCarPrice(cars);

		// Lecture 6
		// findAny()
		findAny();
		// findFirst()
		findFirst();

		// Lecture 7
		// count()
		count(people);
		// min()
		minWithoutComparator(cars);
		// max()
		maxWithoutComparator(cars);
		// average()
		averageAge(people);
		// sum()
		sum(cars);
		// summaryStatistics()
		statistics(cars);

		// Lecture 8
		// Collectors.groupingBy()
		simpleGrouping(cars);
		// Collectors.counting()
		groupingAndCounting();

		// Lecture 9
		// reduce()
		reduce();

		// Lecture 10
		withoutFlatMap();
		// flatMap()
		withFlatMap();

		// Lecture 11
		joinString();
		// Collectors.joining()
		joinStringWithStream();

	}

	// Lecture 1
	private static void imperativeApproach(List<Person> people) {
		System.out.println("imperativeApproach");
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

	private static void declarativeApproachUsingStream(List<Person> people) {
		people.stream().filter(person -> person.getAge() <= 18).limit(20).collect(Collectors.toList())
				.forEach(System.out::println);
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

	// Lecture 5
	private static void understandingFilter(List<Car> cars) {
		System.out.println("understandingFilter");
		Predicate<Car> predicate = car -> car.getPrice() <= 20000;
		cars.stream().filter(predicate).collect(Collectors.toList()).forEach(System.out::println);
	}

	private static void outFirstMapping(List<Person> people) {
		System.out.println("outFirstMapping");
		people.stream().map(person -> {
			PersonDTO personDTO = PersonDTO.map(person);
			return personDTO;
		}).collect(Collectors.toList()).forEach(System.out::println);
	}

	private static void averageCarPrice(List<Car> cars) {
		System.out.println("averageCarPrice");
		double average = cars.stream().mapToDouble(car -> car.getPrice()).average().orElse(0);
		System.out.println(average);
	}

	// Lecture 6

	private static void findAny() {
		System.out.println("findAny");
		Integer[] numbers = { 52, 46, 45, 63, 215, 43, 22, 39, 12, 89, 124, 986, 215, 723, 17, 9, 67 };
		Integer findAny = Arrays.stream(numbers).filter(n -> n > 10).findAny().orElse(0);
		System.out.println(findAny);
	}

	private static void findFirst() {
		System.out.println("findFirst");
		Integer[] numbers = { 52, 46, 45, 63, 215, 43, 22, 39, 12, 89, 124, 986, 215, 723, 17, 9, 67 };
		Integer findFirst = Arrays.stream(numbers).filter(n -> n > 52).findFirst().orElse(0);
		System.out.println(findFirst);
	}

	// Lecture 7
	private static void count(List<Person> people) {
		System.out.println("count");
		long count = people.stream().filter(person -> person.getGender().equalsIgnoreCase("male")).count();
		System.out.println(count);
	}

	private static void minWithoutComparator(List<Car> cars) {
		System.out.println("minWithoutComparator");
		double min = cars.stream().filter(car -> car.getColor().equalsIgnoreCase("yellow"))
				.mapToDouble(car -> car.getPrice()).min().orElse(0);
		System.out.println(min);
	}

	private static void maxWithoutComparator(List<Car> cars) {
		System.out.println("maxWithoutComparator");
		double max = cars.stream().filter(car -> car.getColor().equalsIgnoreCase("yellow")).mapToDouble(Car::getPrice)
				.max().orElse(0);
		System.out.println(max);
	}

	private static void averageAge(List<Person> people) {
		System.out.println("averageAge");
		double averageAge = people.stream().filter(person -> person.getGender().equalsIgnoreCase("female"))
				.mapToDouble(Person::getAge).average().orElse(0);
		System.out.println(averageAge);
	}

	private static void sum(List<Car> cars) {
		System.out.println("sum");
		double sum = cars.stream().mapToDouble(Car::getPrice).sum();
		BigDecimal bigDecimal = BigDecimal.valueOf(sum);
		System.out.println(sum);
		System.out.println(bigDecimal);
	}

	private static void statistics(List<Car> cars) {
		System.out.println("statistics");
		DoubleSummaryStatistics statistics = cars.stream().mapToDouble(Car::getPrice).summaryStatistics();

		System.out.println(statistics);
	}

	// Lecture 8
	private static void simpleGrouping(List<Car> cars) {
		System.out.println("groupingBy");
		Map<String, List<Car>> groupingBy = cars.stream().collect(Collectors.groupingBy(Car::getMake));

		groupingBy.forEach((make, groupedCars) -> {
			System.out.println(make);
			groupedCars.forEach(System.out::println);
		});
	}

	private static void groupingAndCounting() {
		System.out.println("groupingAndCounting");
		List<String> names = new ArrayList<>(Arrays.asList("Remon", "Tomon", "Tonu", "Remon", "Mariam", "Nusaibah",
				"Muhammad", "Muhammad", "Muhammad"));
		Map<String, Long> collect = names.stream().collect(Collectors.groupingBy(name -> name, Collectors.counting()));
//		Map<String, Long> collect = names.stream()
//				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		collect.forEach((name, count) -> System.out.println(name + " " + count));
	}

	// Lecture 9
	private static void reduce() {
		System.out.println("reduce");
		Integer[] numbers = { 52, 46, 45, 63, 215, 43, 22, 39, 12, 89, 124, 986, 215, 723, 17, 9, 67 };

		Integer sum = Arrays.stream(numbers).reduce(0, Integer::sum);
		System.out.println("sum: " + sum);

		Integer sum2 = Arrays.stream(numbers).reduce(0, (a, b) -> a + b);
		System.out.println("sum2: " + sum2);

		Integer sum3 = Arrays.stream(numbers).mapToInt(i -> i).sum();
		System.out.println("sum3: " + sum3);

		Integer sum4 = Arrays.stream(numbers).mapToInt(Integer::intValue).sum();
		System.out.println("sum4: " + sum4);

		Integer min = Arrays.stream(numbers).reduce((a, b) -> a < b ? a : b).get();
		System.out.println("min: " + min);

		Integer max = Arrays.stream(numbers).reduce((a, b) -> a > b ? a : b).get();
		System.out.println("max: " + max);

	}

	// Lecture 10
	private static void withoutFlatMap() {
		System.out.println("withoutFlatMap");
		List<List<String>> oldList = new ArrayList<List<String>>(Arrays.asList(Arrays.asList("one", "two", "three"),
				Arrays.asList("four", "five", "six"), Arrays.asList("seven", "eight", "nine")));
		List<String> newList = new ArrayList<String>();

		for (List<String> ls : oldList) {
			for (String s : ls) {
				newList.add(s);
			}
		}
		oldList.forEach(System.out::println);
		newList.forEach(System.out::println);
	}

	// flatMap()
	private static void withFlatMap() {
		System.out.println("withFlatMap");
		List<List<String>> oldList = new ArrayList<List<String>>(Arrays.asList(Arrays.asList("one", "two", "three"),
				Arrays.asList("four", "five", "six"), Arrays.asList("seven", "eight", "nine")));

		List<String> newList = oldList.stream().flatMap(List::stream).collect(Collectors.toList());

		oldList.forEach(System.out::println);
		newList.forEach(System.out::println);
	}

	// Lecture 11
	private static void joinString() {
		System.out.println("joinString");
		List<String> stringList = new ArrayList<String>(
				Arrays.asList("one", "two", "three", "four", "five", "six", "seven"));
		String join = "";

		for (String s : stringList) {
			join += s + ", ";
		}
		System.out.println("join: " + join.substring(0, join.length() - 2));
	}

	// Collectors.joining()
	private static void joinStringWithStream() {
		System.out.println("joinStringWithStream");
		List<String> stringList = new ArrayList<String>(
				Arrays.asList("one", "two", "three", "four", "five", "six", "seven"));

		String joinWithStream = stringList.stream().collect(Collectors.joining(",", "Hello", "World"));
		System.out.println("joinWithStream: " + joinWithStream);
	}

}
