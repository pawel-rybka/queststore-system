package dao;

import java.util.ArrayList;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import java.util.Iterator;


public abstract class AbstractDao<T> implements Fileable, Listable<T> {


    private DaoIterator daoIterator; // = new UserIterator();
    ArrayList<T> objects = new ArrayList<>();

    public AbstractDao() {
        this.daoIterator = new DaoIterator();
    }
    public ArrayList<String> readFromFile(String path){

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

    private class DaoIterator implements Iterator {
        int index;

        public DaoIterator() {
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
            if (hasNext()) {
                return objects.get(index++);
            }
            return null;
        }
    }

    public Iterator getIterator() {
        return this.daoIterator;
    }


    public <T> void removeFromList(T object){
        // System.out.println(object);
        this.getAll().remove(object);
    }
    public <T> void addToList(T object){
        // System.out.println(object);
        this.getAll().add(object);
    }

    public ArrayList getAll() {
        return this.objects;
    }
}
