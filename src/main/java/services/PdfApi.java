package services;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;

public class PdfApi {

    public void certif(String studentName, String result) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose where to save the certificate");
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try {
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);

                // Set font and position for text
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.beginText();  // Add this line
                contentStream.newLineAtOffset(100, 600);

                // Add certificate content
                contentStream.showText("Certificat d'achevement du cours");
                contentStream.newLineAtOffset(0, -50);
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText("C'est à certifier que");
                contentStream.newLineAtOffset(0, -20);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.showText(studentName);
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(0, -30);
                contentStream.showText("a terminé le cours avec succès");
                contentStream.newLineAtOffset(0, -20);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.showText("courseName");
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(0, -30);
                contentStream.showText("avec un resultat de");
                contentStream.newLineAtOffset(0, -20);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.showText(result+" %");

                contentStream.endText();  // Add this line
                contentStream.close();

                // Save the document using the file path
                document.save(fileToSave);
                document.close();

                System.out.println("Certificate generated successfully.");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
