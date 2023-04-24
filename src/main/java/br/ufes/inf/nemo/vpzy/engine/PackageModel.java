package br.ufes.inf.nemo.vpzy.engine;

import java.util.ArrayList;
import java.util.List;

public class PackageModel {
    private final String name;

    private final List<ClassModel> classes = new ArrayList<>();

    private final List<PackageModel> packages = new ArrayList<>();

    public PackageModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<ClassModel> getClasses() {
        return classes;
    }

    public List<PackageModel> getPackages() {
        return packages;
    }
}
