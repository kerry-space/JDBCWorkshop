package org.example;

import org.example.dao.CityDaoJDBC;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {


        CityDaoJDBC cityDaoJDBC = new CityDaoJDBC();
        //City city = cityDaoJDBC.add(new City( "smallville","ITA","SUP",93333));

       /* City updateCity = cityDaoJDBC.findById(4080);

        updateCity.setDistrict("MON");

        updateCity.setName("Chole");

        City city = cityDaoJDBC.update(updateCity);
        */

        int city = cityDaoJDBC.delete(cityDaoJDBC.findById(4080));

        System.out.println(city);
    }
}
