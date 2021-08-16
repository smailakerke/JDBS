import java.security.spec.ECField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static Connection conn;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<User>users= new ArrayList<>();
        connectToDb();

        while (true) {
            System.out.println("[1] ADD USER");
            System.out.println("[2] LIST USERS");
            System.out.println("[3] EDIT USER");
            System.out.println("[4] DELETE USER");
            int choice = in.nextInt();
            if (choice == 1) {
                System.out.println("Insert name");
                String name= in.next();
                System.out.println("Insert surname");
                String surname=in.next();
                System.out.println("Insert age");
                int age=in.nextInt();
                User u=new User(null,name, surname,age);
                addUser(u);
            } else if (choice == 2) {
            users= getAllUsers();
            for(User u: users){
                System.out.println(u);
            }
            }else if(choice==3){
                System.out.println("Insert id of user");
                Long id= in.nextLong();
                System.out.println("Insert name");
                String name= in.next();
                System.out.println("Insert surname");
                String surname=in.next();
                System.out.println("Insert age");
                int age=in.nextInt();
                User u=new User(id,name, surname,age);
                updateUser(u);

            }else if(choice==4){
                System.out.println("Insert id of user");
                int id= in.nextInt();
                deleteUser(id);
            }

            }

        }

    public static void connectToDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_test?useUnicode=true&serverTimezone=UTC", "root", "");
            System.out.println("CONNECTED");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> getAllUsers() {
        ArrayList<User> users= new ArrayList<>();
        try {
            PreparedStatement st= conn.prepareStatement("select * from user");
            ResultSet rs=st.executeQuery();
            while (rs.next()){
                Long id=rs.getLong("id");
                String name=rs.getString("name");
                String surname=rs.getString("surname");
                int age=rs.getInt("age");
                users.add(new User(id,name,surname,age));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
}
        public static void addUser(User u){
        try{
            PreparedStatement st=conn.prepareStatement("insert into user(name,surname,age) values(?,?,?)");
            st.setString(1,u.getName());
            st.setString(2,u.getSurname());
            st.setInt(3,u.getAge());
            st.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
        }
        public static void updateUser(User u){
            try{
                PreparedStatement st=conn.prepareStatement("update user set name=?,surname=?,age=? where id = ?");
                st.setString(1,u.getName());
                st.setString(2,u.getSurname());
                st.setInt(3,u.getAge());
                st.setLong(4,u.getId());
                st.executeUpdate();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        public static void deleteUser(int id){
            try{
                PreparedStatement st=conn.prepareStatement("delete from user where id = ?");
                st.setInt(1,id);
                st.executeUpdate();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
}