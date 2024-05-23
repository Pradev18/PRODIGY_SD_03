package task_3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactManager {
    private static final String FILE_NAME = "contacts.dat";
    private List<Contact> contacts;
    private Scanner scanner;

    public ContactManager() {
        contacts = loadContacts();
        scanner = new Scanner(System.in);
    }

    public void addContact() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter email address: ");
        String email = scanner.nextLine();

        contacts.add(new Contact(name, phoneNumber, email));
        saveContacts();
        System.out.println("Contact added successfully!");
    }

    public void viewContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            for (int i = 0; i < contacts.size(); i++) {
                System.out.println((i + 1) + ". " + contacts.get(i));
            }
        }
    }

    public void editContact() {
        System.out.print("Enter the number of the contact to edit: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        if (index >= 0 && index < contacts.size()) {
            Contact contact = contacts.get(index);
            System.out.print("Enter new name (leave blank to keep " + contact.getName() + "): ");
            String name = scanner.nextLine();
            System.out.print("Enter new phone number (leave blank to keep " + contact.getPhoneNumber() + "): ");
            String phoneNumber = scanner.nextLine();
            System.out.print("Enter new email address (leave blank to keep " + contact.getEmail() + "): ");
            String email = scanner.nextLine();

            if (!name.isEmpty()) contact.setName(name);
            if (!phoneNumber.isEmpty()) contact.setPhoneNumber(phoneNumber);
            if (!email.isEmpty()) contact.setEmail(email);

            saveContacts();
            System.out.println("Contact updated successfully!");
        } else {
            System.out.println("Invalid contact number.");
        }
    }

    public void deleteContact() {
        System.out.print("Enter the number of the contact to delete: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        if (index >= 0 && index < contacts.size()) {
            contacts.remove(index);
            saveContacts();
            System.out.println("Contact deleted successfully!");
        } else {
            System.out.println("Invalid contact number.");
        }
    }

    private void saveContacts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            System.err.println("Error saving contacts: " + e.getMessage());
        }
    }

    private List<Contact> loadContacts() {
        List<Contact> contacts = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            contacts = (List<Contact>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File not found, no contacts to load
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading contacts: " + e.getMessage());
        }
        return contacts;
    }

    public static void main(String[] args) {
        ContactManager manager = new ContactManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nContact Manager Menu:");
            System.out.println("1. Add Contact");
            System.out.println("2. View Contacts");
            System.out.println("3. Edit Contact");
            System.out.println("4. Delete Contact");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    manager.addContact();
                    break;
                case 2:
                    manager.viewContacts();
                    break;
                case 3:
                    manager.editContact();
                    break;
                case 4:
                    manager.deleteContact();
                    break;
                case 5:
                    System.out.println("Exiting Contact Manager. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
