package example2;

public class EmployeePartTime extends Employee{
    private double workTime;

    public EmployeePartTime(int id, String name, int age, String phone, String email) {
        super(id, name, age, phone, email);
    }

    public double getWorkTime() {
        return workTime;
    }

    public void setWorkTime(double workTime) {
        this.workTime = workTime;
    }

    public double getRealSalary(){
        return this.workTime*100000;
    }

    @Override
    public String toString() {
        return "EmployeePartTime{ " + super.toString() + " workTime= " + workTime + '}';
    }
}
