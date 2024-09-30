package application;

import entities.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Program {
    public static void main(String[] args) {
        /*
Fazer um programa para ler os dados (nome, email e salário)
de funcionários a partir de um arquivo em formato .csv.
Em seguida mostrar, em ordem alfabética, o email dos
funcionários cujo salário seja superior a um dado valor
fornecido pelo usuário.
Mostrar também a soma dos salários dos funcionários cujo
nome começa com a letra 'M'.
         */

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter full file path: ");
        String path = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            List<Employee> employees = new ArrayList<>();

            String line = br.readLine();
            while (line != null) {

                String[] tokens = line.split(",");
                String name = tokens[0];
                String email = tokens[1];
                double salary = Double.parseDouble(tokens[2]);
                employees.add(new Employee(name, email, salary));

                line = br.readLine();
            }

            System.out.print("Enter salary: ");
            double salary = sc.nextDouble();

            System.out.println("Email of people whose salary is more than " + String.format("%.2f", salary) + ":");

            List<String> salaryBigger = employees.stream()
                    .filter(x -> x.getSalary() > salary)
                    .map(Employee::getEmail)
                    .sorted((x, y) -> x.toUpperCase().compareTo(y.toUpperCase()))
                    .collect(Collectors.toList());

            double sumSalaryM = employees.stream()
                    .filter(x -> x.getName().toUpperCase().charAt(0) == 'M')
                    .map(Employee::getSalary)
                    .reduce(0.0, (x, y) -> x + y);


            salaryBigger.forEach(System.out::println);
            System.out.println("Sum of salary of people whose name starts with 'M': " + String.format("%.2f", sumSalaryM));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}