import java.sql.*;

/**
 * 测试连接数据库
 */
public class testOracle {
    Connection con;//声明Connection对象
    Statement sql;
    ResultSet res;
    public Connection getConnection() {  //建立返回值为Connection的方法
        try {        //加载数据库驱动类
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("数据库驱动加载成功");  //返回加载驱动成功信息
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        try {
            con=DriverManager.getConnection("jdbc:oracle:" + "thin:@127.0.0.1:1521:orcl","ssm","ylh013954");//通过访问数据库的URL获取数据库连接对象 ，这里后两个参数分别是数据库的用户名及密码
            System.out.println("数据库连接成功");  //返回连接成功信息
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return con;//按方法要求返回一个Connection对象
    }
    public static void main(String[] args) throws Exception {
        testOracle testOracle=new testOracle();
        testOracle.getConnection();
    }
}
