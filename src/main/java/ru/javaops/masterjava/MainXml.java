package ru.javaops.masterjava;

/**
 * MainXml.
 *
 * @author Stanislav (376825@gmail.com)
 * @since 23.05.2018
 */
public class MainXml {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Format: projectName, xmlName");
            System.exit(1);
        }
    }
}