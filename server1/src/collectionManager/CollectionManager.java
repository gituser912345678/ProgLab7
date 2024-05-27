package collectionManager;

import data.Person;
import exception.UnknowElementException;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
Класс управляющий основной коллекцией
 */
public class CollectionManager {
    /**
    Целевая коллекция.
     */
    private static LinkedHashMap<String, Person> map = new LinkedHashMap<>();
    private static LocalDate date = LocalDate.now();
    private static ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
    Метод возвращающий дату инициализации коллекции.
     */
    public static LocalDate getDate() {
        return date;
    }
    /**
    Метод добавляющий новый объект в коллекцию.
     */
    public static void add(String key, Person person) {
        lock.writeLock().lock();
        map.put(key, person);
        lock.writeLock().unlock();
    }
    /**
    Удаляет элемент коллекции по ключу.
     */
    public static void remove(String key) throws UnknowElementException {
        lock.writeLock().lock();
        if (map.containsKey(key)) {
            map.remove(key);
        } else {
            throw new UnknowElementException();
        }
        lock.writeLock().unlock();
    }


    public static LinkedHashMap<String, Person> getMap() {
        return map;
    }


    public static void setMap(LinkedHashMap<String, Person> map) {
        lock.writeLock().lock();
        if (map != null) {
            CollectionManager.map = map;
        } else {
            CollectionManager.map = new LinkedHashMap<>();
        }
        lock.writeLock().unlock();
    }
}
