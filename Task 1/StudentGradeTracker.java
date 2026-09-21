//Student Grade Tracker
//This program allows users to enter student names and scores, then generates a summary report with letter grades, class average, highest and lowest scores, and grade distribution.
// This program  uses ArrayList to store student records and provides both a quick batch entry mode and an interactive management menu for adding, updating, and deleting student records.

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

//class to represent a student with name and score
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
//method to determine letter grade based on score
    public String getLetterGrade() {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        if (score >= 50) return "E";
        return "F";
    }
}
//main class to manage student grade tracking
public class StudentGradeTracker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("STUDENT GRADE TRACKER");
            System.out.println("1. Quick Batch Entry & Report Generation");
            System.out.println("2. Interactive Management Menu");
            System.out.println("3. Exit");
            System.out.print("Select mode (1-3): ");

            int mode = readInt(scanner);

            switch (mode) {
                case 1:
                    runVideoStyleBatchEntry(scanner);
                    break;
                case 2:
                    runInteractiveMenu(scanner);
                    break;
                case 3:
                    keepRunning = false;
                    System.out.println("\nExiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option! Select 1, 2, or 3.");
            }
        }
        scanner.close();
    }
    //method to handle quick batch entry of student records
    private static void runVideoStyleBatchEntry(Scanner scanner) {
        System.out.println("\n QUICK BATCH GRADE ENTRY");
        System.out.print("Enter the number of students: ");
        int numStudents = readInt(scanner);

        while (numStudents <= 0) {
            System.out.print("Please enter a valid positive number of students: ");
            numStudents = readInt(scanner);
        }

        ArrayList<Student> students = new ArrayList<>();

        for (int i = 0; i < numStudents; i++) {
            System.out.print("\nEnter the name of student " + (i + 1) + ": ");
            String name = scanner.nextLine().trim();
            while (name.isEmpty()) {
                System.out.print("Name cannot be empty. Enter name of student " + (i + 1) + ": ");
                name = scanner.nextLine().trim();
            }

            double score = readValidScore(scanner);
            students.add(new Student(name, score));
        }

        promptReportOptionsAndDisplay(scanner, students);
    }
    //method to handle interactive menu for managing student records
    private static void runInteractiveMenu(Scanner scanner) {
        ArrayList<Student> students = new ArrayList<>();
        boolean inMenu = true;

        while (inMenu) {
            System.out.println("\n INTERACTIVE MENU");
            System.out.println("1. Add Student");
            System.out.println("2. Display Summary Report");
            System.out.println("3. Update Student Score");
            System.out.println("4. Delete Student Record");
            System.out.println("5. Return to Main Menu");
            System.out.print("Select an option (1-5): ");

            int choice = readInt(scanner);

            switch (choice) {
                case 1:
                    addSingleStudent(scanner, students);
                    break;
                case 2:
                    if (students.isEmpty()) {
                        System.out.println("\nNo student records found. Add students first.");
                    } else {
                        promptReportOptionsAndDisplay(scanner, students);
                    }
                    break;
                case 3:
                    updateStudent(scanner, students);
                    break;
                case 4:
                    deleteStudent(scanner, students);
                    break;
                case 5:
                    inMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Enter 1-5.");
            }
        }
    }
//method to add a single student record
    private static void addSingleStudent(Scanner scanner, ArrayList<Student> students) {
        System.out.print("\nEnter student name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        double score = readValidScore(scanner);
        Student student = new Student(name, score);
        students.add(student);
        System.out.println("Added: " + name + " | Score: " + score + " | Grade: " + student.getLetterGrade());
    }
//method to update an existing student's score
    private static void updateStudent(Scanner scanner, ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No records to update.");
            return;
        }
        displayQuickList(students);
        System.out.print("Select student number to update: ");
        int index = readInt(scanner) - 1;

        if (index >= 0 && index < students.size()) {
            Student s = students.get(index);
            System.out.println("Selected: " + s.getName() + " (Current Score: " + s.getScore() + ")");
            double newScore = readValidScore(scanner);
            s.setScore(newScore);
            System.out.println("Updated " + s.getName() + "'s score to " + newScore + " (" + s.getLetterGrade() + ")");
        } else {
            System.out.println("Invalid student number.");
        }
    }
//method to delete a student record
    private static void deleteStudent(Scanner scanner, ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No records to delete.");
            return;
        }
        displayQuickList(students);
        System.out.print("Select student number to delete: ");
        int index = readInt(scanner) - 1;

        if (index >= 0 && index < students.size()) {
            Student removed = students.remove(index);
            System.out.println("Removed record for " + removed.getName());
        } else {
            System.out.println("Invalid student number.");
        }
    }
//method to display a quick list of students with their scores and grades   
    private static void displayQuickList(ArrayList<Student> students) {
        System.out.println("\n CURRENT ROSTER");
        for (int i = 0; i < students.size(); i++) {
            System.out.printf("%d. %-20s | Score: %.2f (%s)%n", 
                    (i + 1), students.get(i).getName(), students.get(i).getScore(), students.get(i).getLetterGrade());
        }
    }
//method to read a valid score between 0 and 100 from user input
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
//method to read an integer from user input, returning -1 for invalid input
    private static int readInt(Scanner scanner) {
        if (scanner.hasNextInt()) {
            int val = scanner.nextInt();
            scanner.nextLine(); 
            return val;
        }
        scanner.nextLine();
        return -1;
    }
//for the report generation, prompt user for options and display the summary report
    private static void promptReportOptionsAndDisplay(Scanner scanner, ArrayList<Student> students) {
        System.out.print("\nCalculate and include class average score in the report? (Y/N): ");
        String avgChoice = scanner.nextLine().trim();
        boolean includeAverage = avgChoice.equalsIgnoreCase("Y");

        System.out.println("\nChoose sort order for report:");
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

        displaySummaryReport(students, includeAverage);
    }
//method to display the summary report with student details, class average, highest and lowest scores, and grade distribution
    private static void displaySummaryReport(ArrayList<Student> students, boolean includeAverage) {
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

        System.out.println("STUDENT PERFORMANCE REPORT");
        System.out.printf("%-20s | %-10s | %-6s%n", "Student Name", "Score", "Grade");

        for (Student s : students) {
            System.out.printf("%-20s | %-10.2f | %-6s%n", s.getName(), s.getScore(), s.getLetterGrade());
        }
        System.out.printf("Total Students : %d%n", students.size());

        if (includeAverage) {
            double average = total / students.size();
            System.out.printf("Class Average  : %.2f%n", average);
        } else {
            System.out.println("Class Average  : [Omitted by user choice]");
        }

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