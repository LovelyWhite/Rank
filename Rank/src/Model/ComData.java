package Model;


import Controller.DataBaseController;
import Listener.DataChangeListener;
import javafx.scene.control.Alert;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;


public class ComData {
    static private boolean isInit = false;
    static private String databaseIP = "localhost";
    static private String databaseName = "";
    static private String databasePort = "3306";
    static private String databaseAccount = "root";
    static private String databasePassword = "";
    static private boolean databaseUseSSL = false;
    static private String databaseCharacterEncoding = "gb2312";
    static private boolean loginAuto = false;
    private static HashMap<String,String> loginField = new HashMap<>();//账户密码二元组
    private static String lastLogin = "";
    private static String lastLoginPw = "";
    private static String loginFieldPath = "E:"+File.separator+"Password.properties";//本地密码文件地址
    private static String initFieldPath = "E:" + File.separator + "Config.properties";//配置文件地址
    private static String nowLogin = "";
    //private Vector<DataChangeListener> dataChangeListener = new Vector<>();
    private double progressRead=0;
    private double progressWrite=0;
    private static boolean isLoad = false;

    public  String getLastLoginPw()
    {
        return lastLoginPw;
    }

    public void setLastLoginPw(String lastLoginPw)
    {
        ComData.lastLoginPw = lastLoginPw;
    }
    public boolean isInit() {

        return isInit;
    }
    public boolean isLoad() {

        return isLoad;
    }
    public boolean isLoginAuto()
    {
        return loginAuto;
    }
    public void setLoginAuto(boolean loginAuto)
    {
        ComData.loginAuto = loginAuto;
    }


    public void addProfile(String account ,String password)
    {
        loginField.put(account,password);
    }
    public void removeProfile(String account)
    {
        loginField.remove(account);
    }

    public HashMap<String, String> getLoginField()
    {
        return loginField;
    }


    private void setInit(boolean init) {
        isInit = init;
    }

    public String getDatabaseIP() {
        return databaseIP;
    }

    public void setDatabaseIP(String databaseIP) { ComData.databaseIP = databaseIP; }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        ComData.databaseName = databaseName;
    }

    public String getDatabasePort() {
        return databasePort;
    }

    public void setDatabasePort(String databasePort) {
        ComData.databasePort = databasePort;
    }

    public String getDatabaseAccount() {
        return databaseAccount;
    }

    public void setDatabaseAccount(String databaseAccount) {
        ComData.databaseAccount = databaseAccount;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        ComData.databasePassword = databasePassword;
    }

    public boolean isDatabaseUseSSL() {
        return databaseUseSSL;
    }

    public void setDatabaseUseSSL(boolean databaseUseSSL) {
        ComData.databaseUseSSL = databaseUseSSL;
    }

    public String getDatabaseCharacterEncoding() {
        return databaseCharacterEncoding;
    }

    public void setDatabaseCharacterEncoding(String databaseCharacterEncoding) {
        ComData.databaseCharacterEncoding = databaseCharacterEncoding;
    }

    public  String getNowLogin()
    {
        return nowLogin;
    }

    public void setNowLogin(String nowLogin)
    {
        ComData.nowLogin = nowLogin;
    }

    public  String getLastLogin()
    {
        return lastLogin;
    }

    public  void setLastLogin(String lastLogin)
    {
        ComData.lastLogin = lastLogin;
    }

    public ComData() {
    }
    //when read file  length = 0               true
    //when read file  Exception                false
    //when read file  do not exsit             true
    //when read file  exist and file lenth =0   false
    //when read not completed  load = false
    public void init()
    {
        //load SYS file
        InputStream inputStream = null;// input/output stream
        File file = new File(initFieldPath); // input/output file
        if (file.exists())
        {
            try
            {
                inputStream = new BufferedInputStream(new FileInputStream(file));
            } catch (FileNotFoundException e)
            {//file not found
                e.printStackTrace();
            }
            if (file.length() != 0)
            {
                Properties prop = new Properties();
                try
                {
                    prop.load(inputStream);
                } catch (IOException e)
                {e.printStackTrace();
                    isInit = false;
                }

                //Check Error
                String a, b, c, d, e, g;
                boolean f;
                a = prop.getProperty("databaseIP");//1
                b = prop.getProperty("databaseName");//2
                c = prop.getProperty("databasePort");//3
                d = prop.getProperty("databaseAccount");//4
                e = prop.getProperty("databasePassword");//5
                f = Boolean.parseBoolean(prop.getProperty("databaseUseSSL"));//6
                g = prop.getProperty("databaseCharacterEncoding");//7
                try
                {
                    inputStream.close();
                } catch (IOException e1)
                {
                    e1.printStackTrace();
                }
                //start to load
                if (!(a.length() == 0 || b.length() == 0 || c.length() == 0 || d.length() == 0||g.length()==0))
                {
                    databaseIP = a;//1
                    databaseName = b;//2
                    databasePort = c;//3
                    databaseAccount = d;//4
                    databasePassword = e;//5
                    databaseUseSSL = f;//6
                    databaseCharacterEncoding = g;//7
                    isInit = true;
                    isLoad = true;
                }
            }
            else
            {
                isInit = true;
            }
        } else
        {
            isInit = true;
        }

        //load Password File
        file = new File(loginFieldPath);
        if (file.exists())
        {
            try
            {
                inputStream = new BufferedInputStream(new FileInputStream(file));
            } catch (FileNotFoundException e)
            {e.printStackTrace();
                setInit(true);
            }
            if (file.length() != 0)
            {
                Properties prop = new Properties();
                try
                {
                    prop.load(inputStream);
                } catch (IOException e)
                {
                    isInit = false;
                    e.printStackTrace();
                }
                prop.forEach((k, v) -> loginField.put(k.toString(), v.toString()));//将Prop读到散列表中
                lastLogin = loginField.get("lastLogin");
                lastLoginPw =loginField.get("lastLoginPw");
                System.out.println(lastLoginPw);

                loginAuto = Boolean.parseBoolean( loginField.get("loginAuto"));
                isInit = true;
            } else
            {
                setInit(true);
            }
        } else
        {
            setInit(true);
        }
    }

    //post data
    public boolean postDatabaseData() {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(initFieldPath, false);
        } catch (FileNotFoundException e) {e.printStackTrace();
            return false;
        }
        Properties  prop = new Properties();
        prop.setProperty("databaseIP",databaseIP);//1
        prop.setProperty("databaseName",databaseName);//2
        prop.setProperty("databasePort", databasePort);//3
        prop.setProperty("databaseAccount",databaseAccount);//4
        prop.setProperty("databasePassword", databasePassword);//5
        prop.setProperty("databaseUseSSL",Boolean.toString(databaseUseSSL));//6
        prop.setProperty("databaseCharacterEncoding",databaseCharacterEncoding );//7
        new DataBaseController().initDatabase();  //load database
        try {
            prop.store(outputStream,null);//store
            outputStream.close();
        } catch (IOException e) {e.printStackTrace();
           return false;//store False

        }

        return true;
    }
    public boolean postAccountData()
    {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(loginFieldPath, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        Properties  prop = new Properties();
        loginField.forEach((a,b)->prop.setProperty(a,b));
        prop.setProperty("loginAuto",Boolean.toString(loginAuto));
        prop.setProperty("lastLogin",lastLogin);
        prop.setProperty("lastLoginPw",lastLoginPw);
        try {
            prop.store(outputStream,null);//存
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
//    private void changProgressRead(double nowProgressRead)//设置读监听
//    {
//        boolean b = false;
//        if (progressRead != nowProgressRead) b = true;
//        progressRead = nowProgressRead;
//        notice(new DataChangeEvent(ComData. nowProgressRead,-1));
//    }
//    private void changProgressWrite(double nowProgressWrite)//设置写监听
//    {
//        boolean b = false;
//        if (progressWrite!=nowProgressWrite) b = true;
//        progressWrite= nowProgressWrite;
//        notice(new DataChangeEvent(ComData. -1,nowProgressWrite));
//    }
//    private synchronized void notice(DataChangeEvent e) {
//        for (DataChangeListener i : dataChangeListener) {
//            i.handle(e);
//        }
//    }
}