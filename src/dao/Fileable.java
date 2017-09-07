package dao;

import java.util.ArrayList;

public interface Fileable{

    public ArrayList readFromFile(String path);
    public void update(ArrayList<String> list, String path);
}
