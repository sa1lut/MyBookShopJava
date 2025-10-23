package ru.saltanov.MyFirstTestAppSpringBoot.someMethods;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class SomeMethodsController {
    public ArrayList<String> arrayList;
    public HashMap<Integer, String> hashMap;

    @GetMapping("/update-array")
    public String updateArrayList(@RequestParam(value = "item", defaultValue = "") String item) {
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            return "ArrayList создан!";
        }

        if (item.isEmpty()) {
            return "Укажите для добавления в item!";
        }

        arrayList.add(item);
        return String.format("Элемент %s добавлен в ArrayList", item);
    }

    @GetMapping("/show-array")
    public String showArrayList() {
        if (arrayList == null) {
            return "ArrayList ещё не создан! Создайте через /update-array";
        }

        return arrayList.toString();
    }

    @GetMapping("/update-map")
    public String updateHashMap(@RequestParam(value = "item", defaultValue = "") String item) {
        if (hashMap == null) {
            hashMap = new HashMap<>();
            return "HashMap успешно создан!";
        }

        if (item.isEmpty()) {
            return "Укажите элемент для добавления в item!";
        }

        hashMap.put(hashMap.size(), item);
        return String.format("Элемент %s добавлен в HashMap", item);
    }

    @GetMapping("/show-map")
    public String showHashMap() {
        if (hashMap == null) {
            return "HashMap ещё не создан! Создайте через /update-map";
        }

        return hashMap.toString();
    }

    @GetMapping("/show-all-length")
    public String showAllLength() {
        if (arrayList == null) {
            return "ArrayList ещё не создан! Создайте через /update-array";
        }

        if (hashMap == null) {
            return "HashMap ещё не создан! Создайте через /update-map";
        }

        return String.format("Размер ArrayList: %s \nРазмер HashMap: %s", arrayList.size(), hashMap.size());
    }

}
