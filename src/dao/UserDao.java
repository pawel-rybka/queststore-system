package dao;

import java.util.ArrayList;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import java.util.Iterator;


public abstract class UserDao<T> implements Fileable, Listable<T> {

    private UserIterator userIterator = new UserIterator();
    ArrayList<T> objects = new ArrayList<>();


    public ArrayList readFromFile(String path){

        ArrayList<String> lines = new ArrayList<String>();

        String line;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            while((line = bufferedReader.readLine()) != null){
                lines.add(line);
            }
        }
        catch (IOException e) {
            System.out.println("No file!");
        }

        return lines;
    }

    public void update(ArrayList<String> list, String path){
        try{
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (String elem: list){
                bufferedWriter.write(elem);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("No file!");
        }
    }

    private class UserIterator implements Iterator {
        int index;

        public UserIterator() {
            this.index = 0;
        }

        public boolean hasNext() {
            if (index < objects.size()) {
                return true;
            }
            index = 0;
            return false;
        }

        public T next() {
            if (!hasNext()) {
                return objects.get(index++);
            }
            return null;
        }
    }

    public Iterator getIterator() {
        return this.userIterator;
    }

    public <T> void removeFromList(T object){
        this.getAll().remove(object);
    }
    public <T> void addToList(T object){
        this.getAll().add(object);
    }

    public abstract ArrayList getAll();
}
