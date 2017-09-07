package dao;

public abstract class UserDao<T> implements Fileable, Listable<T> {

    ArrayList<T> objects;

    public ArrayList readFromFile(String path){

        ArrayList<String> lines = new ArrayList<String>();

        String line;

        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            line = bufferedReader.readLine();
            while(line != null){
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("No file!");
        }

        return lines;
    }

    public void update(ArrayList<String> list, String path){
        try{
            FileWriter fileWriter = new FileWriter(path);

            for (String elem: list){
                filedWriter.write(elem);
                fileWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("No file!");
        }
    }

    public <T> void removeFromList(T object){}
    public <T> void addToList(T object){}
}
