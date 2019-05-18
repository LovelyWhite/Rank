package Model;

import javafx.scene.control.Alert;

import java.sql.*;

public class DataBase
{
    private ComData comData;
    private Connection con;
    private boolean linked;
    private String ip;
    private String port;
    private String name;
    private String ac;
    private String pw;
    private boolean ssl;
    private String end;
    private String sys;
    private String url;
//   threadSync Problem


    public DataBase()
    {
        comData = new ComData();
        linked = true;
        ip = comData.getDatabaseIP();
        port = comData.getDatabasePort();
        name = comData.getDatabaseName();
        ac = comData.getDatabaseAccount();
        pw = comData.getDatabasePassword();
        ssl = comData.isDatabaseUseSSL();
        end = comData.getDatabaseCharacterEncoding();
        sys = "sys";
        url = "jdbc:mysql://" + ip + ":" + port + "/" + comData.getDatabaseName() + "?user=" + ac + "&serverTimezone=UTC&password=" + pw + "&useSSL=" + ssl + "&characterEncoding=" + end;
    }

    public boolean initDataBase()
    {
        PreparedStatement preparedStatement;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver"); // init jdbc-mysql Driver
        } catch (ClassNotFoundException e)
        {e.printStackTrace();
            return false;// link false;
        }
        try
        {
            con = DriverManager.getConnection(url);
        } catch (SQLException e)
        {
            linked = false;
        }
        if (!linked)
        {                     //link built-in database
            try
            {
                //build database
                con = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + sys + "?user=" + ac + "&password=" + pw + "&useSSL=" + ssl + "&serverTimezone=UTC&characterEncoding=" + end);
                System.out.println(con);
                preparedStatement = con.prepareStatement("CREATE database " + comData.getDatabaseName());
                preparedStatement.executeUpdate();
                con.close();//restart
                con = DriverManager.getConnection(url);
                con.setAutoCommit(false);
//                preparedStatement = con.prepareStatement("CREATE TABLE INFORMATION (Account VARCHAR(20) NOT NULL ,Name VARCHAR (20) NOT NULL ,TypeOfPassport ENUM('身份证','护照','军官证','其他')," +
//                        "IDCardNumber VARCHAR(20) NOT NULL ,Sex ENUM('女','男') NOT NULL , People ENUM ('汉族','蒙古族','回族','藏族','维吾尔族','苗族','彝" +
//                        "族','壮族','布依族','朝鲜族','满族','侗族','瑶族','白族','土家族','哈尼族','哈萨克族','傣族','黎族','僳僳族','佤族','畲族','高" +
//                        "山族','拉祜族','水族','东乡族','纳西族','景颇族','柯尔克孜族','土族','达斡尔族','仫佬族','羌族','布朗族','撒拉族','毛南族','仡佬" +
//                        "族','锡伯族','阿昌族','普米族','塔吉克族','怒族','乌孜别克族','俄罗斯族','鄂温克族','德昂族','保安族','裕固族','京族','塔塔尔族','" +
//                        "独龙族','鄂伦春族','赫哲族','门巴族','珞巴族','基诺族') NOT NULL ,EducationResume VARCHAR(20) NOT NULL ,Occupation ENUM('教育/培" +
//                        "训','公务员/行政/事业单位/','模特','空姐','学生','其他') NOT NULL,PostalAdress VARCHAR(50) NOT NULL ,PostalCode VARCHAR(20) NOT NULL" +
//                        ",Tel1 VARCHAR(20) NOT NULL, TEL2 VARCHAR(20) NULL, Email VARCHAR(20) NOT NULL,PhotoPath VARCHAR(50) NOT NULL, PRIMARY KEY (Account, IDCardNumber) )");
                    preparedStatement = con.prepareStatement("CREATE TABLE INFORMATION (Account VARCHAR(20) NOT NULL ,Name VARCHAR (20) NOT NULL ,TypeOfPassport VARCHAR (20) NOT NULL," +
                      "IDCardNumber VARCHAR(20) NOT NULL ,Sex VARCHAR (20) NOT NULL, People VARCHAR (20) NOT NULL ,EducationResume VARCHAR(20) NOT NULL ,Occupation EVARCHAR (20) NOT NULL,PostalAdress VARCHAR(50) NOT NULL ,PostalCode VARCHAR(20) NOT NULL" +
                        ",Tel1 VARCHAR(20) NOT NULL, TEL2 VARCHAR(20) NULL, Email VARCHAR(20) NOT NULL,PhotoPath VARCHAR(50) NOT NULL, PRIMARY KEY (Account, IDCardNumber) )");
                preparedStatement.executeUpdate();
                preparedStatement = con.prepareStatement("CREATE TABLE CodeAndSubject (Code VARCHAR(10) NOT NULL , Subject VARCHAR(20) NOT NULL ,PRIMARY KEY(Code))");
                preparedStatement.executeUpdate();
                preparedStatement = con.prepareStatement("CREATE TABLE Users(Account VARCHAR(20) NOT NULL ,Password VARCHAR(20) NOT NULL ,PRIMARY KEY (Account))");
                preparedStatement.executeUpdate();
            } catch (SQLException e)
            {
                e.printStackTrace();
                return false;//link failed
            }

            try
            {
                con.commit();// commit
                // con.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
                try
                {
                    con.rollback();
                } catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
        return true;

    }
    private Connection linkDatabase()
    {

        try
        {
            con = DriverManager.getConnection(url);
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        return con;
    }

    public ResultSet SearchData(String sql)
    {
        ResultSet rs;
        Connection x;
        if ((x = linkDatabase()) != null)
        {
            try
            {
                PreparedStatement p = x.prepareStatement(sql);
                rs = p.executeQuery();
            } catch (SQLException e)
            {
                e.printStackTrace();
                return null; // return null
            }
            return rs;
        }
        return null;
    }

    public int updateData(String sql, boolean autoCommit)
    {
        if (autoCommit)
        {
            if ((con = linkDatabase()) != null)
            {
                try
                {
                    PreparedStatement p = con.prepareStatement(sql);
                    return p.executeUpdate(sql);
                } catch (SQLException e)
                {
                    e.printStackTrace();
                    return -1; // -1 equals failed
                }

            }
            return -1;
        } else
        {
            if ((con = linkDatabase()) != null)
            {
                try
                {

                    con.setAutoCommit(false);
                } catch (SQLException e)
                {
                    e.printStackTrace();
                    return -1;
                }
                try
                {
                    PreparedStatement p = con.prepareStatement(sql);

                } catch (SQLIntegrityConstraintViolationException e)
                { e.printStackTrace();
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("失败");
                    alert.setHeaderText(null);
                    alert.setContentText("请检查是否有写文件的权限!");
                    alert.showAndWait();
                    return -1; // -1 equals failed
                }
                catch (Exception e1)
                {
                    e1.printStackTrace();
                }

            }
        }
        return 0;
    }
   public void commit()
    {
        try
        {
            con.commit();
        } catch (SQLException e)
        {
            e.printStackTrace();
            try
            {
                con.rollback();
            } catch (SQLException e1)
            {
                e1.printStackTrace();
            }
        }
    }
    public boolean testLink()
    {
        Connection connection;
        try
        {
            connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + sys + "?user=" + ac + "&password=" + pw + "&useSSL=" + ssl + "&serverTimezone=UTC&characterEncoding=" + end);
        } catch (SQLException e)
        {
            return false;
        }
        return true;
    }
}
