package models;

public class User {
    private String name;
    private int age;
    private int id;

    // Constructor
    public User(String name, int age, int id){
        this.name = name;
        this.age = age;
        this.id = id;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Show Models.User infos
    @Override
    public String toString() {
        return  "---------------------------------\n" +
                "id   = " + id + "\n" +
                "name = " + name + "\n" +
                "age  = " + age + "\n" +
                "---------------------------------";
    }
}
