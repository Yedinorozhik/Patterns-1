package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;


public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity() {
        String[] cities = new String[]{"Барнаул", "Чита", "Хабаровск", "Кострома", "Нижний Новгород", "Ростов-на-Дону",
                "Ханты-Мансийск", "Москва", "Черкесск", "Краснодар"};
        return cities[new Random().nextInt(cities.length)];
    }

    public static String generateName(String local) {
        Faker faker = new Faker(new Locale(local));
        return faker.name().fullName();
    }

    public static String generatePhone(String local) {
        Faker faker = new Faker(new Locale(local));
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        static Faker faker;

        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            faker = new Faker(new Locale("ru"));
            return new UserInfo(generateCity(), generateName(locale), generatePhone(locale));
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}