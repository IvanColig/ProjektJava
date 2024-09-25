package com.example.projektjava;

import com.example.projektjava.entity.Laptop;
import com.example.projektjava.entity.PC;
import com.example.projektjava.entity.Router;
import com.example.projektjava.generator.TableGenerator;

import java.util.ArrayList;
import java.util.List;

public class Database {

    private static Database database;

    private List<PC> pcs;
    private List<Laptop> laptops;
    private List<Router> routers;

    private Database() {
        this.pcs = new ArrayList<>(TableGenerator.generatePCs());
        this.laptops = new ArrayList<>(TableGenerator.generateLaptops());
        this.routers = new ArrayList<>(TableGenerator.generateRouters());
    }

    public static Database getInstance() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    public List<PC> getPCs() {
        return pcs;
    }

    public List<Laptop> getLaptops() {
        return laptops;
    }

    public List<Router> getRouters() {
        return routers;
    }

    public void setPcs(List<PC> pcs) {
        this.pcs = new ArrayList<>(pcs);
    }

    public void setLaptops(List<Laptop> laptops) {
        this.laptops = new ArrayList<>(laptops);
    }

    public void setRouters(List<Router> routers) {
        this.routers = new ArrayList<>(routers);
    }
}
