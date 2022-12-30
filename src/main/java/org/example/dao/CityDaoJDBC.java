package org.example.dao;

import org.example.model.City;
import org.example.model.MySqlConnection;
import org.example.exception.DBConnectionException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CityDaoJDBC implements CityDao {

    int _id = 0;
    String name = null;
    String countryCode = null;
    String district = null;
    int population = 0;
    City  city = null;

    //---------------crud operation----------------------
    //Create
    //read
    //update
    //delete

    //---read----
    public City findById(int id) {

        String query = "select * from city where id = ?";



        try(
                Connection connection = MySqlConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ) {
            preparedStatement.setInt(1, id);
          try(ResultSet resultSet = preparedStatement.executeQuery();){
              while (resultSet.next()){
                  _id = resultSet.getInt("id");
                  name = resultSet.getString("name");
                  countryCode = resultSet.getString("countryCode");
                  district = resultSet.getString("district");
                  population = resultSet.getInt("population");
              }
              city =  new City(_id, name,countryCode,district,population);
          }


        } catch (DBConnectionException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return city;
    }

    //---read----
    public List<City> findByCode(String code) {

        String query = "select * from city where countryCode = ?";
        List<City> cityFindByCode = new LinkedList<>();

        try (
                Connection connection = MySqlConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ){

            preparedStatement.setString(1, code);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    _id = resultSet.getInt("id");
                    name = resultSet.getString("name");
                    countryCode = resultSet.getString("countryCode");
                    district = resultSet.getString("district");
                    population = resultSet.getInt("population");

                    city = new City(_id,name,countryCode,district,population);
                    if(city == null) new RuntimeException("no city data");

                    cityFindByCode.add(city);
                }
            }

        } catch (DBConnectionException | SQLException e) {
            System.out.println(e.getMessage());
        }


        return cityFindByCode;
    }

    //---read----
    public List<City> findByName(String name) {
        String query = "select * from city where name = ?";
        List<City>  listCityFindByName = new LinkedList<>();
        try (
                Connection connection = MySqlConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ){
           preparedStatement.setString(1,name);

          try ( ResultSet resultSet = preparedStatement.executeQuery();){
              while (resultSet.next()){
                  _id = resultSet.getInt("id");
                  name = resultSet.getString("name");
                  countryCode = resultSet.getString("countryCode");
                  district = resultSet.getString("district");
                  population = resultSet.getInt("population");

                  city = new City(_id,name,countryCode,district,population);
                  listCityFindByName.add(city);
              }
          }
        } catch (DBConnectionException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return listCityFindByName;
    }

    //---read----
    public List<City> findAll() {
        String query = "select * from city";
        List<City> listAllCity = new LinkedList<>();

        try(
                Connection connection = MySqlConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ) {

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    _id = resultSet.getInt("id");
                    name = resultSet.getString("name");
                    countryCode = resultSet.getString("countryCode");
                    district = resultSet.getString("district");
                    population = resultSet.getInt("population");

                    city = new City(_id, name,countryCode,district,population);
                    listAllCity.add(city);
                }
            }
            return listAllCity;

        } catch (DBConnectionException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    //---create----
    public City add(City city) {
        String query = "insert into city(name, countryCode, district, population) values (?,?,?,?)";
        City cityFind = null;
        try(
                Connection connection = MySqlConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS );
                ) {
            //set data to database
            preparedStatement.setString(1,city.getName());
            preparedStatement.setString(2,city.getCountryCode());
            preparedStatement.setString(3,city.getDistrict());
            preparedStatement.setInt(4,city.getPopulation());
            int result = preparedStatement.executeUpdate();
            System.out.println(result);

          try (ResultSet resultSet = preparedStatement.getGeneratedKeys()){
              if(resultSet.next()){
                  _id = resultSet.getInt(1);
              }
          }

          if(_id == 0){
              throw new RuntimeException("getGenerateKeys value for address query was null");
          }
          cityFind = findById(_id);

        } catch (DBConnectionException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return cityFind;
    }

    //---update----
    public City update(City city) {
        String query = "update city set name = ?, countryCode = ?, district = ?, population = ?  where id = ?";
        City cityUpdate = null;
        try (
                Connection connection = MySqlConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ){
            preparedStatement.setString(1,city.getName());
            preparedStatement.setString(2,city.getCountryCode());
            preparedStatement.setString(3,city.getDistrict());
            preparedStatement.setInt(4, city.getPopulation());
            preparedStatement.setInt(5,city.getId());

            int res = preparedStatement.executeUpdate();
            System.out.println(res);

        } catch (DBConnectionException | SQLException e) {
            System.out.println(e.getMessage());
        }



        return findById(city.getId());
    }

    //---delete----
    public int delete(City city) {
        String query = "delete from city where name = ?";
        int rowAffeccted = 0;
        try(
                Connection connection = MySqlConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ) {

            preparedStatement.setString(1,city.getName());

             rowAffeccted = preparedStatement.executeUpdate();

        } catch (DBConnectionException | SQLException e) {
            System.out.println(e.getMessage());
        }


        return rowAffeccted;
    }
}
