package dao;

public interface Listable<T>{

    public <T> void removeFromList(T object);
    public <T> void addToList(T object);
}
