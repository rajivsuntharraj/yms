//package com.application.yms;
//
//import com.application.yms.dto.Employee;
//
//import java.util.*;
//import java.util.concurrent.CopyOnWriteArrayList;
//import java.util.stream.Collectors;
//
//public class Java8Features {
//    public static void main(String[] args) {
//        List<Employee> emp = Arrays.asList(
//                new Employee(1,"ABC",30,"Male",50000),
//                new Employee(2,"DEF",35,"Female",80000),
//                new Employee(3,"GHI",26,"Male",30000));
////        System.out.println(emp);
////        Map<String, Long> genderCount = emp.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
////        System.out.println(genderCount);
////        Map<String, Double> avgSalary = emp.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingDouble(Employee::getSalary)));
////        System.out.println(avgSalary);
////        Employee youngest = emp.stream().min(Comparator.comparingInt(Employee::getAge)).orElseThrow();
////        System.out.println("Youngest: " + youngest.getName());
//        String s1 = "rajivta";
//        System.out.println(s1.toUpperCase());
//        System.out.println(s1.toLowerCase());
//        System.out.println(s1.length());
//        String s2 = s1;
//        System.out.println(s2);
//        System.out.println(s1==s2);
//        System.out.println(s1.isEmpty());
//        System.out.println(s1.isBlank());
//
//
//    }
//}
