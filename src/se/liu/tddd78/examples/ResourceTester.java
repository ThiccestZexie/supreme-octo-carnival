package se.liu.tddd78.examples;

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
        testLoadingTextResource();
        testLoadingImageResource();
    }

    private static void testLoadingTextResource() {
        // Reading a text file from *resources* is a bit cumbersome.

        // We don't know if the file is stored directly in the file system
        // or inside an *archive* (JAR file), so we should access it
        // as a "resource" identified by an URL.  Here we want to open a
        // file that is in the root of the resource folder (and again, that
        // folder could actually exist inside a JAR file, so that you can't
        // open it using an ordinary filename).
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

            // Read and print strings until you get null, which indicates "end of file"
            // for Reader objects
            String str = reader.readLine();
            while (str != null) {
                System.out.println(str);
                str = reader.readLine();
            }
        } catch (IOException e) {
            // TODO: Exceptions need to be handled somehow.  This code is incomplete
            //  and the rest is left as an exercise for course participants.
            e.printStackTrace();
        }
    }


    private static void testLoadingImageResource() {
        // Like above, we need to access the image through a resource.
        final URL image = ClassLoader.getSystemResource("images/hello_world.png");

        // If the image does not exist, image will be null, and a NullPointerException
        // will be thrown when the ImageIcon is created below.  This is OK in this
        // very specific case, because each *resource* is an integral part of the
        // program.  It cannot be missing unless the project is damaged,
        // in which case all bets are off in any case.

        // The ImageIcon class can read an entire image directly from any URL.
        ImageIcon icon = new ImageIcon(image);

        // We won't go all the way to showing the image here, but we can print
        // some information about it!
        System.out.println("Read an image with width " + icon.getIconWidth() + " and height " + icon.getIconHeight());
    }
}
