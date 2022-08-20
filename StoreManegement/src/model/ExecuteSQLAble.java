package model;

public interface ExecuteSQLAble {

  String autoIncreaseID();

  String getMaxID();

  boolean updateSQL();

  boolean insertSQL();

  boolean deleteSQL();

  boolean querySQL(String s);
}
