import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class Student {
    private String name;
    private double score;

    public Student(String name, double score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getLetterGrade() {
        if (score >= 80) return "A";
        if (score >= 70) return "B";
        if (score >= 60) return "C";
        if (score >= 50) return "D";
        if (score >= 40) return "E";
        return "F";
    }
}

public class StudentGradeTracker{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        while (true) {
            System.out.println("WELCOME TO THE STUDENT GRADE TRACKER");
            System.out.println("1. Add Student");
            System.out.println("2. Display Summary Report of All Students");
            System.out.println("3. Update Student Score");
            System.out.println("4. Delete Student Record");
            System.out.println("5. Exit Program");
            System.out.print("Select an option (1-5): ");

            int choice = readInt(scanner);

            switch (choice) {
                case 1:
                    addStudent(scanner, students);
                    break;
                case 2:
                    if (students.isEmpty()) {
                        System.out.println("\nNo records found. Add students first.");
                    } else {
                        promptSortAndDisplay(scanner, students);
                    }
                    break;
                case 3:
                    updateStudent(scanner, students);
                    break;
                case 4:
                    deleteStudent(scanner, students);
                    break;
                case 5:
                    if (!students.isEmpty()) {
                        System.out.print("\nWould you like to display the summary report before exiting? (Y/N): ");
                        String response = scanner.nextLine().trim();
                        if (response.equalsIgnoreCase("Y")) {
                            promptSortAndDisplay(scanner, students);
                        }
                    }
                    System.out.println("\nExiting program. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("\nInvalid option! Please enter a number between 1 and 5.");
            }
        }
    }

    private static void addStudent(Scanner scanner, ArrayList<Student> students) {
        System.out.println("\nADD STUDENT ");
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        double score = readValidScore(scanner);
        Student student = new Student(name, score);
        students.add(student);
        System.out.println("Successfully added " + name + " | Score: " + score + " | Grade: " + student.getLetterGrade());
    }

    private static void updateStudent(Scanner scanner, ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("\n No student records available to update.");
            return;
        }

        System.out.println("\n UPDATE STUDENT SCORE ");
        displayQuickList(students);
        System.out.print("Enter the number of the student to update: ");
        int index = readInt(scanner) - 1;

        if (index >= 0 && index < students.size()) {
            Student student = students.get(index);
            System.out.println("Current entry: " + student.getName() + " - Score: " + student.getScore());
            double newScore = readValidScore(scanner);
            student.setScore(newScore);
            System.out.println("Updated " + student.getName() + "'s score to " + newScore + " (" + student.getLetterGrade() + ")");
        } else {
            System.out.println("Invalid selection.");
        }
    }

    private static void deleteStudent(Scanner scanner, ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("\nNo student records available to delete.");
            return;
        }

        System.out.println("\n--- DELETE STUDENT RECORD ---");
        displayQuickList(students);
        System.out.print("Enter the number of the student to delete: ");
        int index = readInt(scanner) - 1;

        if (index >= 0 && index < students.size()) {
            Student removed = students.remove(index);
            System.out.println("Successfully removed record for " + removed.getName());
        } else {
            System.out.println("Invalid selection.");
        }
    }

    private static void displayQuickList(ArrayList<Student> students) {
        for (int i = 0; i < students.size(); i++) {
            System.out.printf("%d. %-20s | Score: %.2f (%s)%n", 
                    (i + 1), students.get(i).getName(), students.get(i).getScore(), students.get(i).getLetterGrade());
        }
    }

    private static double readValidScore(Scanner scanner) {
        while (true) {
            System.out.print("Enter score (0 - 100): ");
            if (scanner.hasNextDouble()) {
                double score = scanner.nextDouble();
                scanner.nextLine();
                if (score >= 0 && score <= 100) {
                    return score;
                }
            } else {
                scanner.nextLine();
            }
            System.out.println("Invalid entry! Score must be a number between 0 and 100.");
        }
    }

    private static int readInt(Scanner scanner) {
        if (scanner.hasNextInt()) {
            int val = scanner.nextInt();
            scanner.nextLine();
            return val;
        }
        scanner.nextLine();
        return -1;
    }

    private static void promptSortAndDisplay(Scanner scanner, ArrayList<Student> students) {
        System.out.println("\n Choose sort order for report:");
        System.out.println("1. By Highest Score (Descending)");
        System.out.println("2. Alphabetically by Name (A-Z)");
        System.out.println("3. Original Entry Order");
        System.out.print("Enter option (1-3): ");

        int option = readInt(scanner);

        switch (option) {
            case 1:
                Collections.sort(students, Comparator.comparingDouble(Student::getScore).reversed());
                break;
            case 2:
                Collections.sort(students, Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER));
                break;
            default:
                break;
        }

        displaySummaryReport(students);
    }

    private static void displaySummaryReport(ArrayList<Student> students) {
        double total = 0;
        Student highestStudent = students.get(0);
        Student lowestStudent = students.get(0);
        int[] gradeCounts = new int[6]; // A, B, C, D, E, F

        for (Student s : students) {
            double score = s.getScore();
            total += score;

            if (score > highestStudent.getScore()) highestStudent = s;
            if (score < lowestStudent.getScore()) lowestStudent = s;

            switch (s.getLetterGrade()) {
                case "A": gradeCounts[0]++; break;
                case "B": gradeCounts[1]++; break;
                case "C": gradeCounts[2]++; break;
                case "D": gradeCounts[3]++; break;
                case "E": gradeCounts[4]++; break;
                case "F": gradeCounts[5]++; break;
            }
        }

        double average = total / students.size();

        System.out.println("SUMMARY REPORT OF ALL STUDENTS");
        System.out.printf("%-20s | %-10s | %-6s%n", "Student Name", "Score", "Grade");

        for (Student s : students) {
            System.out.printf("%-20s | %-10.2f | %-6s%n", s.getName(), s.getScore(), s.getLetterGrade());
        }

        System.out.printf("Total Students : %d%n", students.size());
        System.out.printf("Class Average  : %.2f%n", average);
        System.out.printf("Highest Score  : %.2f (%s) - Grade %s%n", 
                highestStudent.getScore(), highestStudent.getName(), highestStudent.getLetterGrade());
        System.out.printf("Lowest Score   : %.2f (%s) - Grade %s%n", 
                lowestStudent.getScore(), lowestStudent.getName(), lowestStudent.getLetterGrade());

     
        System.out.println("GRADE DISTRIBUTION");
        char[] gradeLabels = {'A', 'B', 'C', 'D', 'E', 'F'};
        for (int i = 0; i < gradeCounts.length; i++) {
            double percentage = ((double) gradeCounts[i] / students.size()) * 100;
            System.out.printf("Grade %c : %2d student(s)  (%.1f%%)%n", gradeLabels[i], gradeCounts[i], percentage);
        }
    }
}