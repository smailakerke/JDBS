import java.security.spec.ECField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class Main1 {
    public static Connection conn;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<Items>items= new ArrayList<>();
        connectToDb();

        while (true) {
            System.out.println("PRESS [1] TO ADD ITEMS");
            System.out.println("PRESS [2] TO LIST ITEMS");
            System.out.println("PRESS [3] TO DELETE ITEMS");
            System.out.println("PRESS [0] TO EXIT");
            int choice = in.nextInt();
            if (choice == 1) {
                System.out.println("Insert name");
                String name= in.next();
                System.out.println("Insert price");
                int price=in.nextInt();
                Items i=new Items(null,name,price);
                addItems(i);
            } else if (choice == 2) {
               items= getAllItems();
                for(Items i: items){
                    System.out.println(i);
                }
            }else if(choice==3){
                System.out.println("Insert id of user");
                int id= in.nextInt();
                deleteItems(id);
            }else{
                break;
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

    public static ArrayList<Items> getAllItems() {
        ArrayList<Items> items= new ArrayList<>();
        try {
            PreparedStatement st= conn.prepareStatement("select * from items");
            ResultSet rs=st.executeQuery();
            while (rs.next()){
                Long id=rs.getLong("id");
                String name=rs.getString("name");
                int price=rs.getInt("price");
                items.add(new Items(id,name,price));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
    public static void addItems(Items i){
        try{
            PreparedStatement st=conn.prepareStatement("insert into items (name,price) values(?,?)");
            st.setString(1,i.getName());
            st.setDouble(2,i.getPrice());
            st.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteItems(int id){
        try{
            PreparedStatement st=conn.prepareStatement("delete from items where id = ?");
            st.setInt(1,id);
            st.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}