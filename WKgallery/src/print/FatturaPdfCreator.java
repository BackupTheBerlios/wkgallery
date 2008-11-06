package print;

import abstractlayer.Cliente;
import abstractlayer.Fattura;
import abstractlayer.Opera;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import exceptions.TitoloNonPresenteException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Vector;
import utilities.Data;

/**
 * Classe per la stampa della fattura su pdf.
 * @author Marco Celesti
 */
public class FatturaPdfCreator {

    private Fattura fattura;
    private Data dataFatt;
    private int numeroFatt;
    private Cliente cliente;
    private String pagamento;
    private Vector<Opera> opere;
    private File destDir;
    private Document document;
    private DecimalFormat formatoEuro;
    private DecimalFormat formatoPerc;

    /**
     * Crea l'istanza con cui stampare il documento in formato pdf.
     * @param fattura La fattura da stampare
     * @param pagamento Specifica il tipo di pagamento (e.g. "Rimessa diretta")
     * @param destDir La cartella di destinazione del file generato
     * @throws exceptions.TitoloNonPresenteException se un'opera nella fattura non ha titolo
     */
    public FatturaPdfCreator(Fattura fattura, String pagamento, File destDir) throws TitoloNonPresenteException {
        this.destDir = destDir;
        this.pagamento = pagamento;
        this.fattura = fattura;
        this.cliente = fattura.getCliente();
        dataFatt = fattura.getDataFattura();
        numeroFatt = fattura.getNumeroFattura();
        opere = fattura.getOpere();
        for (Opera o : opere) {
            if (o.getTitolo().isEmpty()) {
                throw new TitoloNonPresenteException("L'opera " + o.getCodiceOpera() + " non ha titolo. Prima di emettere la fattura è necessario specificarne uno.", o);
            }
        }
        formatoEuro = new DecimalFormat("#0.00 €");
        formatoPerc = new DecimalFormat("#0 %");
    }

    /**
     * Crea il documento pdf.
     * @return il file pdf creato
     * @throws com.lowagie.text.DocumentException in caso di errore nella generazione del documento
     */
    public File generateFattura() throws DocumentException {
        String nomefile = dataFatt.getAnno() + "_" + numeroFatt + ".pdf";
        document = new Document(PageSize.A4, 60.0f, 60.0f, 70.0f, 70.0f);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(destDir + "\\" + nomefile));
        } catch (FileNotFoundException fnfe) {
        }
        document.addAuthor("Galleria d'arte Wunderkammer");
        document.addSubject("Fattura numero" + numeroFatt + " del " + dataFatt.toString());
        document.open();
        writeHeader();
        writeCliente();
        if (!fattura.isProforma()) {
            writeNumFatt();
        }
        writeTable();
        writePagamento();
        document.close();
        return new File(destDir + "\\" + nomefile);
    }

    /**
     * Scrive l'intestazione della fattura.
     * @throws com.lowagie.text.DocumentException in caso di errore nella generazione del documento
     */
    private void writeHeader() throws DocumentException {
        Paragraph lineNome = new Paragraph(15.0f, "Galleria d’arte Wunderkammer", new Font(Font.TIMES_ROMAN, 14, Font.BOLD));
        Paragraph lineDi = new Paragraph(11.0f, "di Maria Rita Cimiero", new Font(Font.TIMES_ROMAN, 12, Font.BOLD));
        Paragraph lineVia = new Paragraph(11.0f, "Via Tasso, 107", new Font(Font.TIMES_ROMAN, 12, Font.BOLD));
        Paragraph lineCap = new Paragraph(11.0f, "24121 - Bergamo", new Font(Font.TIMES_ROMAN, 12, Font.BOLD));
        Paragraph linePiva = new Paragraph(11.0f, "P. Iva 03388530168", new Font(Font.TIMES_ROMAN, 12, Font.BOLD));
        Paragraph lineTel = new Paragraph(10.0f, "Ph. / Fax +39 035 19900244", new Font(Font.TIMES_ROMAN, 11, Font.BOLD));
        Paragraph lineSito = new Paragraph(10.0f, "http://www.wunderkammer-arte.com", new Font(Font.TIMES_ROMAN, 11, Font.BOLD));
        Paragraph lineMail1 = new Paragraph(10.0f, "info@wunderkammer-arte.com", new Font(Font.TIMES_ROMAN, 11, Font.BOLD));
        Paragraph lineMail2 = new Paragraph(10.0f, "caravaggio600@yahoo.it", new Font(Font.TIMES_ROMAN, 11, Font.BOLD));
        document.add(lineNome);
        document.add(lineDi);
        document.add(lineVia);
        document.add(lineCap);
        document.add(linePiva);
        document.add(lineTel);
        document.add(lineSito);
        document.add(lineMail1);
        document.add(lineMail2);
    }

    /**
     * Scrive il cliente della fattura.
     * @throws com.lowagie.text.DocumentException in caso di errore nella generazione del documento
     */
    private void writeCliente() throws DocumentException {
        Paragraph line1 = new Paragraph(15.0f, cliente.getNomeRsoc2() + " " + cliente.getCognRsoc1(), new Font(Font.TIMES_ROMAN, 14, Font.BOLD));
        line1.setSpacingBefore(5.0f);
        line1.setAlignment(Element.ALIGN_RIGHT);
        Paragraph line2 = new Paragraph(15.0f, cliente.getIndirizzo(), new Font(Font.TIMES_ROMAN, 14, Font.BOLD));
        line2.setAlignment(Element.ALIGN_RIGHT);
        Paragraph line3 = new Paragraph(15.0f, cliente.getCap() + " - " + cliente.getCitta() + " (" + cliente.getProvincia() + ")", new Font(Font.TIMES_ROMAN, 14, Font.BOLD));
        line3.setAlignment(Element.ALIGN_RIGHT);
        Paragraph line4 = new Paragraph(15.0f, cliente.getCfPiva(), new Font(Font.TIMES_ROMAN, 14, Font.BOLD));
        line4.setAlignment(Element.ALIGN_RIGHT);
        document.add(line1);
        document.add(line2);
        document.add(line3);
        document.add(line4);
    }

    /**
     * Scrive il numero della fattura.
     * @throws com.lowagie.text.DocumentException in caso di errore nella generazione del documento
     */
    private void writeNumFatt() throws DocumentException {
        Paragraph line1 = new Paragraph(13.0f, "Fattura accompagnatoria numero " + fattura.getNumeroFattura() + " del " + fattura.getDataFattura().toStringIt() + ".", new Font(Font.TIMES_ROMAN, 14));
        line1.setSpacingBefore(70.0f);
        document.add(line1);
    }

    /**
     * Scrive la tabella con il riassunto dei pezzi acquistati.
     * @throws com.lowagie.text.DocumentException in caso di errore nella generazione del documento
     */
    private void writeTable() throws DocumentException {
        Paragraph line0 = new Paragraph();

        line0.setSpacingBefore(50.0f);
        document.add(line0);
        PdfPTable tableUp = new PdfPTable(2);
        int headerwidths[] = {70, 30};
        tableUp.setWidths(headerwidths);
        tableUp.setWidthPercentage(100);

        /* Intestazione tabella */
        PdfPCell cellHeader;
        cellHeader = new PdfPCell(new Paragraph(15.0f, "Descrizione", new Font(Font.TIMES_ROMAN, 14, Font.BOLDITALIC)));
        cellHeader.setPaddingBottom(10.0f);
        cellHeader.setPaddingTop(10.0f);
        tableUp.addCell(cellHeader);
        cellHeader = new PdfPCell(new Paragraph(15.0f, "Prezzo intero IVA escl.", new Font(Font.TIMES_ROMAN, 14, Font.BOLDITALIC)));
        cellHeader.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cellHeader.setPaddingBottom(10.0f);
        cellHeader.setPaddingTop(10.0f);
        tableUp.addCell(cellHeader);

        /* Righe fattura */
        PdfPCell cellDescr;
        PdfPCell cellPrezzoUni;
        for (Opera o : opere) {
            cellDescr = new PdfPCell(new Paragraph(13.0f, o.getTitolo(), new Font(Font.TIMES_ROMAN, 13, Font.NORMAL)));
            cellPrezzoUni = new PdfPCell(new Paragraph(13.0f, "" + formatoEuro.format(o.getPrezzo()), new Font(Font.TIMES_ROMAN, 13, Font.NORMAL)));
            cellDescr.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellDescr.setPaddingBottom(5.0f);
            cellDescr.setPaddingTop(5.0f);
            cellPrezzoUni.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellPrezzoUni.setPaddingBottom(5.0f);
            cellPrezzoUni.setPaddingTop(5.0f);
            tableUp.addCell(cellDescr);
            tableUp.addCell(cellPrezzoUni);
        }

        /* Riga sconto */
        if (fattura.getSconto() != 0.0f) {
            PdfPCell cellSconto = new PdfPCell(new Paragraph(13.0f, "Sconto: " + formatoPerc.format(fattura.getSconto()), new Font(Font.TIMES_ROMAN, 13, Font.NORMAL)));
            cellSconto.setColspan(2);
            cellSconto.setPaddingBottom(5.0f);
            cellSconto.setPaddingTop(5.0f);
            cellSconto.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tableUp.addCell(cellSconto);
        }

        /* Totale */
        PdfPTable tableDown = new PdfPTable(2);
        int finalwidths[] = {80, 20};
        tableDown.setWidths(finalwidths);
        tableDown.setWidthPercentage(100);
        PdfPCell cellSx = new PdfPCell(new Paragraph(13.0f, "Imponibile\nIVA 20%\nTotale fattura", new Font(Font.TIMES_ROMAN, 13, Font.BOLDITALIC)));
        cellSx.setPaddingBottom(5.0f);
        cellSx.setPaddingTop(5.0f);
        cellSx.setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        float totSenzaIva = fattura.getTotale() / 1.2f;
        float iva = totSenzaIva * 0.2f;
        
        PdfPCell cellDx = new PdfPCell(new Paragraph(13.0f, formatoEuro.format(totSenzaIva) + "\n" + formatoEuro.format(iva) + "\n" + formatoEuro.format(fattura.getTotale()), new Font(Font.TIMES_ROMAN, 13, Font.BOLDITALIC)));
        cellDx.setPaddingBottom(5.0f);
        cellDx.setPaddingTop(5.0f);
        cellDx.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tableDown.addCell(cellSx);
        tableDown.addCell(cellDx);

        document.add(tableUp);
        document.add(tableDown);
    }

    /**
     * Scrive il tipo di pagamento.
     * @throws com.lowagie.text.DocumentException in caso di errore nella generazione del documento
     */
    private void writePagamento() throws DocumentException {
        Paragraph line1 = new Paragraph(15.0f, "Pagamento: " + pagamento, new Font(Font.TIMES_ROMAN, 14, Font.NORMAL));
        line1.setSpacingBefore(30.0f);
        document.add(line1);
    }
}
