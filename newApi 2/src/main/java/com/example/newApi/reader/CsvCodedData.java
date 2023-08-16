package com.example.newApi.reader;

import com.example.newApi.model.UserRequest;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvCodedData {
    private List<String> countries;
    private List<String> categories;

    private static final String file = "/Users/pulkitbarwal/Desktop/newApi/src/main/resources/static/CsvFile";

    public CsvCodedData() {
    }

    public void loadData(){
        this.countries = new ArrayList<>();
        this.categories = new ArrayList<>();

        try {

            FileReader filereader = new FileReader(file);

            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                String currentCountry = nextRecord[0];
                String currentCategory  = nextRecord[1];
                countries.add(currentCountry);
                categories.add(currentCategory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addData(List<UserRequest> userRequest){

       try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file, true);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // add data to csv
            for(UserRequest userRequest1 : userRequest){
                String[] data1 = { userRequest1.getCountry(), userRequest1.getCategory() };
                writer.writeNext(data1);
            }

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
