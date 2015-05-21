
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import steganographer.Steganographer;
import imageconverter.ImageConverter;

public class Main {
    
    static Scanner scan = new Scanner(System.in);
    
    public static void main(String[] args) throws IOException {
        
        System.out.println("Welcome to the Steganographer!");
        int option = 5;
        
        do {
            
            menu();
            option = Integer.parseInt(scan.nextLine());
            handleOption(option);
            
            
        } while(option != 6);
        
    }
    
    public static void menu(){
        String menu = "\nSelect an option:\n"
                    + "---------------------\n"
                    + "1. Hide an text\n"
                    + "2. Hide an image\n"
                    + "3. Reveal text from stego image\n"
                    + "4. Reveal an image from a stego image\n"
                    + "5. Convert image to (ppm, jpg, jpeg, png, gif)\n"
                    + "6. Exit\n";
        System.out.println(menu);
    }
    
    public static void handleOption(int option) {
        if (option > 0 && option < 7) {
            switch(option) {
                case 1:
                    hideText();
                    break;
                case 2:
                    hideImage();
                    break;
                case 3:
                    revealText();
                    break;
                case 4:
                    revealImage();
                    break;
                case 5:
                    convertImage();
                    break;
                case 6:
                    System.exit(0);
                    break;
            }
        } else {
            System.err.println("Please select a valid option");
        }
    }
    
    public static void convertImage() {
        try {
            System.out.println("File name to convert: ");
            String filename = scan.nextLine();
            System.out.println("Format to convert(ppm, jpg, jpeg, png, gif): ");
            String format = scan.nextLine();
            
            ImageConverter.convert(filename, format);
            System.out.println("Succesfully converted file!");
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void hideText() {
        System.out.println("File name to hide the text: ");
        File f = new File(scan.nextLine());
        System.out.println("Text to hide: ");
        String text = scan.nextLine();
        
        Steganographer steg = new Steganographer(f);
        boolean hidden = steg.hide(text.getBytes(), "text");
        
        if (hidden) 
            System.out.println("Successfully hidden text into file (stego-image.ppm)!");
    }
    
    public static void hideImage() {
        try {
            System.out.println("File name to hide the image (must be substantially larger than the image to hide): ");
            File f = new File(scan.nextLine());
            System.out.println("File name to hide: ");
            byte[] image = Files.readAllBytes(Paths.get(scan.nextLine()));
            
            Steganographer steg = new Steganographer(f);
            boolean hidden = steg.hide(image, "image");
            
            if (hidden)
                System.out.println("Successfully hidden image into file (stego-image.ppm)!");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public static void revealText() {
        System.out.println("Key file name (original file): ");
        File f = new File(scan.nextLine());
        System.out.println("File name where text is hidden (stego-image): ");
        File f2 = new File(scan.nextLine());        
        
        Steganographer steg = new Steganographer(f);
        System.out.println("\nHidden Message:\n------------------");
        steg.reveal(f2, "text");
        System.out.println("\nSuccessfully revealed text from file (stego-image.ppm)!");
    }
    
    public static void revealImage() {
        System.out.println("Key file name (original file): ");
        File f = new File(scan.nextLine());
        System.out.println("File name where image is hidden (stego-image): ");
        File f2 = new File(scan.nextLine());        
        
        Steganographer steg = new Steganographer(f);
        steg.reveal(f2, "photo");
        System.out.println("Successfully revealed image from file (stego-image.ppm)!");
        System.out.println("File name is revealed-image.ppm");
    }
    
    public static String getFileExtensionFromPath(String path) {
        int i = path.lastIndexOf('.');
        if (i > 0) {
            return path.substring(i + 1);
        }
        return "";
    }
}
