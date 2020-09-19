package example2;

import example.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeManager {
    public static void main(String[] args) {
        List<Employee> employeeList = new ArrayList<>();
        boolean check = true;
        double totalSalary = 0;

        employeeList.add(new EmployeeFullTime(1,"Nam",18, "0952334333","nam@gmail.com",10000000));
        employeeList.add(new EmployeeFullTime(2,"Ngoc",28, "0952786878","ngoc@gmail.com",25000000));
        employeeList.add(new EmployeeFullTime(3,"Trung",15, "0945424533","trung@gmail.com",20000000));
        employeeList.add(new EmployeePartTime(4,"Dung",17, "0955453453","dung@gmail.com"));
        employeeList.add(new EmployeePartTime(5,"Quynh",34, "0945475525","quynh@gmail.com"));
        employeeList.add(new EmployeePartTime(6,"Chi",21, "095374878633","chi@gmail.com"));
        EmployeeManager employeeManager = new EmployeeManager();
        do{
            System.out.printf("Nhập vào lựa chọn :%n" +
                    "1. Hiển thị toàn bộ danh sách. %n" +
                    "2. Thêm mới nhân viên. %n" +
                    "3. Nhập tiền thưởng, tiền phạt, số giờ làm cho nhân viên %n" +
                    "4. Hiển thị danh sách nhân viên Fulltime có mức lương thấp hơn mức lương trung bình. %n" +
                    "5. Hiển thị tổng lương phải trả cho tất cả nhân viên Parttime. %n" +
                    "6. Hiển thị danh sách nhân viên Fulltime theo số lương tăng dần. %n" +
                    "7. Thoát. %n"
                    );
            Scanner scanner = new Scanner(System.in);
            int selection = scanner.nextInt();
            switch (selection){
                case 1 :
                    for(Employee employee : employeeList){
                        System.out.println(employee.toString());
                    }
                    break;
                case 2 :
                    employeeManager.addEmployee(employeeList);
                    break;
                case 3 :
                    totalSalary = employeeManager.calculatorSalary(employeeList);
                    break;
                case 4 :
                    if (totalSalary != 0){
                        employeeManager.underAverageList(employeeList,totalSalary);
                    }else {
                        System.out.println("Hãy nhập tiền thưởng, tiền phạt, số giờ làm cho nhân viên trước.");
                    }
                    break;
                case 5 :
                    double totalSalaryPartTime = employeeManager.totalSalaryPartTime(employeeList);
                    if (totalSalaryPartTime != 0){
                        System.out.print("Tổng lương phải trả cho tất cả nhân viên Parttime là : ");
                        System.out.printf("%1$,.0f", totalSalaryPartTime);
                        System.out.print(" Đồng");
                        System.out.println();
                    }else {
                        System.out.println("Hãy nhập tiền thưởng, tiền phạt, số giờ làm cho nhân viên trước.");
                    }

                    break;
                case 6:
                    List<EmployeeFullTime> fullTimeList = employeeManager.sortFullTimeForSalary(employeeList);
                    for (EmployeeFullTime employeeFullTime : fullTimeList){
                        System.out.println(employeeFullTime.toString());
                    }
                    break;
                case 7 :
                    check = false;
                    break;
            }
        }while (check);

    }

    public void addEmployee(List<Employee> employeeList){
        String name;
        int age;
        String phone;
        String email;
        int type;
        double salary;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Them Nhan vien FullTime nhap so 1, nhan vien PartTime nhap so 2.");
        type = scanner.nextInt();
        System.out.println("Nhap vao ten nhan vien :");
        scanner.nextLine();
        name = scanner.nextLine();
        System.out.println("Nhap vao tuoi :");
        age = scanner.nextInt();
        System.out.println("Nhap vao so dien thoai :");
        scanner.nextLine();
        phone = scanner.nextLine();
        System.out.println("Nhap vao email :");
        email = scanner.nextLine();
        System.out.println("Nhap vao luong :");
        salary = scanner.nextDouble();
        if (type == 1){
            Employee employee = new EmployeeFullTime(employeeList.size()+1,name,age,phone,email,salary);
            employeeList.add(employee);
            System.out.println("Them thanh cong.");
        }else if (type == 2){
            Employee employee = new EmployeePartTime(employeeList.size()+1,name,age,phone,email);
            employeeList.add(employee);
            System.out.println("Them thanh cong.");
        }else {
            System.out.println("Chua xac dinh loai nhan vien");
        }

    }

    public double calculatorSalary(List<Employee> employees){
        Scanner scanner = new Scanner(System.in);
        double totalSalary = 0;
        for (Employee employee : employees){
            if (employee instanceof EmployeeFullTime){
                double bonus;
                double forfeit;
                System.out.println("Nhap vao tien thuong cua nhan vien "+ employee.getId() + " : ");
                bonus = scanner.nextDouble();
                ((EmployeeFullTime) employee).setBonus(bonus);
                System.out.println("Nhap vao tien phat cua nhan vien "+ employee.getId() + " : ");
                forfeit = scanner.nextDouble();
                ((EmployeeFullTime) employee).setForfeit(forfeit);
                ((EmployeeFullTime) employee).setSalary(((EmployeeFullTime) employee).getSalary()-(((EmployeeFullTime) employee).getBonus()-((EmployeeFullTime) employee).getForfeit()));
                totalSalary += ((EmployeeFullTime) employee).getSalary();
            }else if (employee instanceof EmployeePartTime){
                int workTime;
                System.out.println("Nhap vao so gio lam cua nhan vien "+ employee.getId() + " : ");
                workTime = scanner.nextInt();
                ((EmployeePartTime) employee).setWorkTime(workTime);
                totalSalary += ((EmployeePartTime) employee).getRealSalary();
            }
        }
        return totalSalary;
    }
    public void underAverageList(List<Employee> employees,double totalSalary) {
        for (Employee employee : employees) {
            if (employee instanceof EmployeeFullTime) {
                if (((EmployeeFullTime) employee).getSalary() < totalSalary/employees.size()) {
                    System.out.println(employee.toString());
                }
            }
        }
    }
    public double totalSalaryPartTime(List<Employee> employeeList){
        double totalSalaryPartTime = 0;
        for (Employee employee : employeeList){
            if (employee instanceof EmployeePartTime){
                totalSalaryPartTime += ((EmployeePartTime) employee).getRealSalary();
            }
        }
        return totalSalaryPartTime;
    }
    public List<EmployeeFullTime> sortFullTimeForSalary(List<Employee> employeeList){
        List<EmployeeFullTime> fullTimeList = new ArrayList<>();
        for (Employee employee : employeeList){
            if (employee instanceof EmployeeFullTime){
                fullTimeList.add((EmployeeFullTime) employee);
            }
        }
        EmployeeFullTime swap;
        for (int j = 0; j < fullTimeList.size()-1; j++){
            for (int i = 0; i < fullTimeList.size()-1-j; i++){
                if (fullTimeList.get(i).getSalary() > fullTimeList.get(i+1).getSalary()){
                    swap = fullTimeList.get(i);
                    fullTimeList.set(i,fullTimeList.get(i+1));
                    fullTimeList.set(i+1,swap);
                }
            }
        }
        return fullTimeList;
    }
}
