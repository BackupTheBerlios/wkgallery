/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package print;

import abstractlayer.Cliente;
import abstractlayer.Fattura;
import abstractlayer.Opera;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Vector;
import utilities.Data;

/**
 *
 * @author Marco Celesti
 */
public class FatturaPdfCreator {

    Fattura fattura;
    Cliente cliente;
    File fatturaFile;
    Document document;

    public FatturaPdfCreator(Fattura fattura, boolean proforma) throws DocumentException {
        this.fattura = fattura;
        this.cliente = fattura.getCliente();
        Data dataFatt = fattura.getDataFattura();
        int numeroFatt = fattura.getNumeroFattura();
        Cliente cliente = fattura.getCliente();
        Vector<Opera> opere = fattura.getOpere();

        String nomefile = dataFatt.getAnno() + "_" + numeroFatt + ".pdf";
        document = new Document();
        try {
        PdfWriter.getInstance(document, new FileOutputStream(nomefile));
        } catch (FileNotFoundException fnfe) {
        }
        document.addAuthor("Galleria d’arte Wunderkammer");
        document.addSubject("Fattura numero" + numeroFatt + " del " + dataFatt.toString());
        document.open();
        writeHeader();
        writeCliente();
        document.close();
    }

    private void writeHeader() throws DocumentException {
        Paragraph line1 = new Paragraph(13.0f, "Galleria d’arte Wunderkammer", new Font(Font.TIMES_ROMAN, 13));
        Paragraph line2 = new Paragraph(13.0f, "di Maria Rita Cimiero", new Font(Font.TIMES_ROMAN, 13));
        Paragraph line3 = new Paragraph(13.0f, "Via Tasso, 107", new Font(Font.TIMES_ROMAN, 13));
        Paragraph line4 = new Paragraph(13.0f, "24121 - Bergamo", new Font(Font.TIMES_ROMAN, 13));
        Paragraph line5 = new Paragraph(13.0f, "P. Iva 03388530168", new Font(Font.TIMES_ROMAN, 13));
        document.add(line1);
        document.add(line2);
        document.add(line3);
        document.add(line4);
        document.add(line5);
        
        
    }
    
    private void writeCliente() throws DocumentException {
        Paragraph line1 = new Paragraph(13.0f, cliente.getNomeRsoc2() + " " + cliente.getCognRsoc1(), new Font(Font.TIMES_ROMAN, 13));
        line1.setSpacingBefore(3.0f);
        line1.setAlignment(Element.ALIGN_RIGHT);
        Paragraph line2 = new Paragraph(13.0f, cliente.getIndirizzo(), new Font(Font.TIMES_ROMAN, 13));
        line2.setAlignment(Element.ALIGN_RIGHT);
        Paragraph line3 = new Paragraph(13.0f, cliente.getCap() + " - " + cliente.getCitta() + " (" + cliente.getProvincia() + ")" , new Font(Font.TIMES_ROMAN, 13));
        line3.setAlignment(Element.ALIGN_RIGHT);
        Paragraph line4 = new Paragraph(13.0f, cliente.getCfPiva(), new Font(Font.TIMES_ROMAN, 13));
        line4.setAlignment(Element.ALIGN_RIGHT);
        document.add(line1);
        document.add(line2);
        document.add(line3);
        document.add(line4);
    }
}
