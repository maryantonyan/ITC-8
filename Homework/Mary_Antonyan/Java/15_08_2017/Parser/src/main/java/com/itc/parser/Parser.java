package com.itc.parser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.parser.ParseException;

/**
 * This class represents all functions for parsing to json and vice versa
 * 
 * @author mary
 */
public class Parser {
    private final String file;

    /**
     * Creates Parser object
     * @param file file path
     */
    public Parser(String file) {
        this.file = file;
    }
    
    /**
     * Adds new record to file
     * 
     * @param name student name
     * @param surname student surname
     * @param homeworks list of homework
     * @throws IOException 
     */
    private void record(FileWriter f, String name, String surname, ArrayList<String> homeworks) throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("surname", surname);

        JSONArray list = new JSONArray();
        for (String hmw : homeworks) {
            list.add(hmw);
        }     
        obj.put("homeworks", list);
        f.append(obj.toJSONString());
        System.out.println(name + " recorded...");
    }
    
    /**
     * Creates json file with records about students
     * @throws java.io.IOException
     */
    public void write() throws IOException {
        ArrayList<String> homeworks = new ArrayList<>();
        homeworks.add("C++");
        homeworks.add("Java");
        homeworks.add("Python");
        
        FileWriter f = new FileWriter(this.file);
        System.out.println("Started recording in file " + f.toString());
        record(f, "Vachagan", "Hovhannisyan", homeworks);
        record(f, "Liana", "Andreasyan", homeworks);
        record(f, "Smbat", "Dallakyan", homeworks);
        record(f, "Mary", "Antonyan", homeworks);
        record(f, "Ruben", "Ghalayan", homeworks);
        f.close();
        System.out.println("File closed...");
    }
    
    /**
     * Reads information about all students from json file
     * @return student array
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParseException
     */
    public ArrayList<Student> read() throws FileNotFoundException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader f = new FileReader(this.file);
        Object obj = parser.parse(f);
        
        JSONObject jsonObject = (JSONObject)obj;
        JSONArray stds = (JSONArray)jsonObject.get("students");
        Iterator<JSONObject> iterator = stds.iterator();
        
        ArrayList<Student> students = new ArrayList<>();
        while (iterator.hasNext()) {
            JSONObject next = iterator.next();
            JSONArray hmws = (JSONArray)next.get("homeworks");
            String name = (String)next.get("name");
            String surname = (String)next.get("surname");

            Iterator<String> iter = hmws.iterator();
            ArrayList<String> homeworks = new ArrayList<>();
            while (iter.hasNext()) {
                homeworks.add(iter.next());
            }
            students.add(new Student(name, surname, homeworks));
        }
        return students;
    }
    
    /**
     * Get student form json by name 
     * 
     * @param studentName student name
     * @return list of students with given names
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParseException 
     */
    public ArrayList<Student> getByName(String studentName) throws FileNotFoundException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader f = new FileReader(this.file);
        Object obj = parser.parse(f);
        
        JSONObject jsonObject = (JSONObject)obj;
        JSONArray stds = (JSONArray)jsonObject.get("students");
        Iterator<JSONObject> iterator = stds.iterator();
        
        ArrayList<Student> students = new ArrayList<>();
        while (iterator.hasNext()) {
            JSONObject next = iterator.next();
            String name = (String)next.get("name");
            if (name.equals(studentName)) {
                String surname = (String)next.get("surname");
                JSONArray hmws = (JSONArray)next.get("homeworks");
                Iterator<String> iter = hmws.iterator();
                ArrayList<String> homeworks = new ArrayList<>();
                while (iter.hasNext()) {
                    homeworks.add(iter.next());
                }
                students.add(new Student(name, surname, homeworks));
            }
        }
        return students;
    }
    
    /**
     * Get student from json by homework list
     * 
     * @param studentHomeworks homework list
     * @return list of students with given homework list
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParseException 
     */
    public ArrayList<Student> getByHomework(ArrayList<String> studentHomeworks) throws FileNotFoundException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader f = new FileReader(this.file);
        Object obj = parser.parse(f);
        
        JSONObject jsonObject = (JSONObject)obj;
        JSONArray stds = (JSONArray)jsonObject.get("students");
        Iterator<JSONObject> iterator = stds.iterator();
        
        ArrayList<Student> students = new ArrayList<>();
        while (iterator.hasNext()) {
            JSONObject next = iterator.next();
            JSONArray hmws = (JSONArray)next.get("homeworks");
            if (hmws.equals(studentHomeworks)) {
                String name = (String)next.get("name");
                String surname = (String)next.get("surname");
                students.add(new Student(name, surname, studentHomeworks));
            }
        }
        return students;
    }
}