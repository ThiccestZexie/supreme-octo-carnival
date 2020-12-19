package se.liu.liuid123;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * A simple test class used to exemplify how resources work.
 */
public class ResourceTester
{
    public static void main(String[] args) {
        // Reading a text file from *resources* is a bit cumbersome.
        // We don't know if the file is stored directly in the file system
        // or inside an *archive* (JAR file), so should access it
        // as a "resource" identified by an URL.
        final URL readme = ClassLoader.getSystemResource("README.md");

        // Then we can use this URL to open an *input stream*,
        // create an InputStreamReader that converts bytes to characters
        // according to the default character encoding (typically UTF-8),
        // and then createa a BufferedReader which can be used to read lines.
        //
        // All of this is insiude a "try" statement that ensures the streams
        // and readers are closed when we are done.
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(readme.openStream()))) {
            System.out.println("Contents of the file:");

            // Read and print strings until you get null
            String str = reader.readLine();
            while (str != null) {
                System.out.println(str);
                str = reader.readLine();
            }
        } catch (IOException e) {
            // Needs to be handled somehow...
            e.printStackTrace();
        }

        final URL image = ClassLoader.getSystemResource("images/hello_world.png");

        ImageIcon icon = new ImageIcon(image);
        System.out.println("Read an image with width " + icon.getIconWidth() + " and height " + icon.getIconHeight());
    }
}
