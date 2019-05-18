package Controller;


import Model.DataBase;

import java.sql.ResultSet;

public class DataBaseController
{
    private DataBase dataBase  = new DataBase();

    public boolean initDatabase()
    {
        return dataBase.initDataBase();
    }
    public ResultSet SearchData(String sql)
    {
        return dataBase.SearchData(sql);
    }
    public int updateData(String sql, boolean autoCommit)
    {
        return  dataBase.updateData(sql,autoCommit);
    }
    public void commit()
    {
        dataBase.commit();
    }
    public boolean testLink()
    {
        return dataBase.testLink();
    }

}
