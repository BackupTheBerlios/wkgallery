/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backupmanager;

import abstractlayer.Artista;
import abstractlayer.Cliente;
import abstractlayer.Fattura;
import abstractlayer.Opera;
import abstractlayer.Regione;
import daoabstract.ArtistaDAO;
import daoabstract.ClienteDAO;
import daoabstract.FatturaDAO;
import daoabstract.OperaDAO;
import exceptions.BadFormatException;
import exceptions.ChiavePrimariaException;
import exceptions.RecordCorrelatoException;
import exceptions.RecordGiaPresenteException;
import exceptions.RecordNonPresenteException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import utilities.Data;
import utilities.EMail;

/**
 * Classe per la gestione dei backup su/da file xml.
 * @author Marco Celesti
 */
public class XMLImportExporter {

    ArtistaDAO artistaDAO;
    ClienteDAO clienteDAO;
    OperaDAO operaDAO;
    FatturaDAO fatturaDAO;

    /**
     * Crea una nuova istanza della classe.
     * @param artistaDAO istanza di ArtistaDao
     * @param clienteDAO istanza di ClienteDao
     * @param fatturaDAO istanza di OperaDao
     * @param operaDAO istanza di FatturaDao
     */
    public XMLImportExporter(ArtistaDAO artistaDAO, ClienteDAO clienteDAO, OperaDAO operaDAO, FatturaDAO fatturaDAO) {
        this.artistaDAO = artistaDAO;
        this.clienteDAO = clienteDAO;
        this.fatturaDAO = fatturaDAO;
        this.operaDAO = operaDAO;
    }

    /**
     * Elimina i dati preesistenti su DB
     */
    private void eliminaDati() {
        try {
            operaDAO.deleteAllOpere();
            fatturaDAO.deleteAllFatture();
            clienteDAO.deleteAllClienti();
            artistaDAO.deleteAllArtisti();
        } catch (RecordCorrelatoException rce) {
            System.err.println(rce);
        }
    }

    /**
     * Salva il file nella cartella selezionata.
     * @param destDir la cartella selezionata
     * @return il file creato
     */
    public File creaBkp(File destDir) {
        File fileBkp = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element e = doc.createElement("wkgallery");
            doc.appendChild(e);

            for (Artista a : artistaDAO.findAllArtisti()) {
                Element artistaElement = doc.createElement("artista");
                artistaElement.setAttribute("codiceartista", "" + a.getCodiceArtista());
                artistaElement.setAttribute("cognome", a.getCognome());
                artistaElement.setAttribute("nome", a.getNome());
                artistaElement.setAttribute("notebiografiche", a.getNoteBiografiche());
                e.appendChild(artistaElement);
            }

            for (Cliente c : clienteDAO.findAllClienti()) {
                Element clienteElement = doc.createElement("cliente");
                clienteElement.setAttribute("codicecliente", "" + c.getCodiceCliente());
                clienteElement.setAttribute("cogn_rsoc1", c.getCognRsoc1());
                clienteElement.setAttribute("nome_rsoc2", c.getNomeRsoc2());
                clienteElement.setAttribute("indirizzo", c.getIndirizzo());
                clienteElement.setAttribute("codicecliente", "" + c.getCodiceCliente());
                clienteElement.setAttribute("nciv", "" + c.getNCiv());
                clienteElement.setAttribute("citta", c.getCitta());
                clienteElement.setAttribute("provincia", c.getProvincia());
                clienteElement.setAttribute("regione", c.getRegione().getNomeRegione());
                clienteElement.setAttribute("stato", c.getStato());
                clienteElement.setAttribute("telefono1", c.getTel1());
                clienteElement.setAttribute("telefono2", c.getTel2());
                clienteElement.setAttribute("cellulare1", c.getCell1());
                clienteElement.setAttribute("cellulare2", c.getCell2());
                clienteElement.setAttribute("mail1", c.getMail1().toString());
                clienteElement.setAttribute("mail2", c.getMail2().toString());
                clienteElement.setAttribute("cf_piva", c.getCfPiva());
                clienteElement.setAttribute("note", c.getNote());
                clienteElement.setAttribute("professionista", "" + c.isProfessionista());
                clienteElement.setAttribute("cap", c.getCap());
                e.appendChild(clienteElement);
            }

            for (Opera o : operaDAO.findAllOpere()) {
                Element operaElement = doc.createElement("opera");
                operaElement.setAttribute("codiceopera", "" + o.getCodiceOpera());
                operaElement.setAttribute("artista", "" + o.getArtista().getCodiceArtista());
                operaElement.setAttribute("titolo", o.getTitolo());
                operaElement.setAttribute("tecnica", o.getTecnica());
                operaElement.setAttribute("dimensioni", o.getDimensioni());
                operaElement.setAttribute("tipo", o.getTipo());
                operaElement.setAttribute("foto", o.getFoto().getPath());
                operaElement.setAttribute("prezzo", "" + o.getPrezzo());
                try {
                    operaElement.setAttribute("numerofattura", "" + fatturaDAO.selectFatturePerOpera(o).getNumeroFattura());
                    operaElement.setAttribute("annofattura", "" + fatturaDAO.selectFatturePerOpera(o).getDataFattura().getAnno());
                } catch (RecordNonPresenteException exc) {
                    operaElement.setAttribute("numerofattura", "");
                    operaElement.setAttribute("annofattura", "");
                }
                e.appendChild(operaElement);
            }

            for (Fattura f : fatturaDAO.findAllFatture()) {
                Element fatturaElement = doc.createElement("fattura");
                fatturaElement.setAttribute("numero", "" + f.getNumeroFattura());
                fatturaElement.setAttribute("anno", "" + f.getDataFattura().getAnno());
                fatturaElement.setAttribute("idcliente", "" + f.getCliente().getCodiceCliente());
                fatturaElement.setAttribute("data", f.getDataFattura().toString());
                fatturaElement.setAttribute("sconto", "" + f.getSconto());
                fatturaElement.setAttribute("totale", "" + f.getTotale());
                fatturaElement.setAttribute("proforma", "" + f.isProforma());
                e.appendChild(fatturaElement);
            }
            // SAVE
            fileBkp = new File(destDir + "\\" + new Data() + ".xml");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(fileBkp);

            // eventuali regole di traduzione parametro del traduttore
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "wkgallery.dtd");
            t.transform(source, result);
        } catch (TransformerException ex) {
            Logger.getLogger(XMLImportExporter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLImportExporter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileBkp;
    }

    /**
     * Recupera i dati salvati precedentemente su file xml.
     * @param source il file xml di backup
     * @throws javax.xml.parsers.ParserConfigurationException in caso di errori nel recupero dei dati
     * @throws org.xml.sax.SAXException in caso di errori nel recupero dei dati
     */
    public void ripristinaBkp(File source) throws ParserConfigurationException, SAXException {
        Vector<Vector<Integer>> opereFatture = new Vector<Vector<Integer>>();
        eliminaDati();
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        Document doc = null;
        try {
            doc = docBuilder.parse(source);
        } catch (IOException ex) {
            Logger.getLogger(XMLImportExporter.class.getName()).log(Level.SEVERE, null, ex);
        }

        // normalize text representation
        doc.getDocumentElement().normalize();

        // Ripristino artisti
        NodeList artistiNodeList = doc.getElementsByTagName("artista");
        int totArtisti = artistiNodeList.getLength();

        for (int s = 0; s < totArtisti; s++) {
            Node artistaNode = artistiNodeList.item(s);
            Artista artista = new Artista();
            if (artistaNode.getNodeType() == Node.ELEMENT_NODE) {
                NamedNodeMap atts = artistaNode.getAttributes();
                int codiceArtista = Integer.parseInt(atts.getNamedItem("codiceartista").getNodeValue());
                String cognome = atts.getNamedItem("cognome").getNodeValue();
                String nome = atts.getNamedItem("nome").getNodeValue();
                String noteBio = atts.getNamedItem("notebiografiche").getNodeValue();
                artista.setCodiceArtista(codiceArtista);
                artista.setCognome(cognome);
                artista.setNome(nome);
                artista.setNoteBiografiche(noteBio);
                try {
                    artistaDAO.insertArtista(artista);
                } catch (RecordGiaPresenteException ex) {
                    Logger.getLogger(XMLImportExporter.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ChiavePrimariaException ex) {
                    Logger.getLogger(XMLImportExporter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        // Ripristino clienti
        NodeList clientiNodeList = doc.getElementsByTagName("cliente");
        int totClienti = clientiNodeList.getLength();

        for (int s = 0; s < totClienti; s++) {
            Node clienteNode = clientiNodeList.item(s);
            Cliente cliente = new Cliente();
            if (clienteNode.getNodeType() == Node.ELEMENT_NODE) {
                NamedNodeMap atts = clienteNode.getAttributes();
                int codCli = Integer.parseInt(atts.getNamedItem("codicecliente").getNodeValue());
                String cognRsoc1 = atts.getNamedItem("cogn_rsoc1").getNodeValue();
                String nomeRagSoc2 = atts.getNamedItem("nome_rsoc2").getNodeValue();
                String indirizzo = atts.getNamedItem("indirizzo").getNodeValue();
                String nCiv = atts.getNamedItem("nciv").getNodeValue();
                String citta = atts.getNamedItem("citta").getNodeValue();
                String prov = atts.getNamedItem("provincia").getNodeValue();
                Regione regione = Regione.getRegione(atts.getNamedItem("regione").getNodeValue());
                String stato = atts.getNamedItem("stato").getNodeValue();
                String tel1 = atts.getNamedItem("telefono1").getNodeValue();
                String tel2 = atts.getNamedItem("telefono2").getNodeValue();
                String cell1 = atts.getNamedItem("cellulare1").getNodeValue();
                String cell2 = atts.getNamedItem("cellulare2").getNodeValue();
                EMail email1 = null;
                EMail email2 = null;
                String cfPiva = atts.getNamedItem("cf_piva").getNodeValue();
                String note = atts.getNamedItem("note").getNodeValue();
                boolean professionista = Boolean.getBoolean(atts.getNamedItem("professionista").getNodeValue());
                String cap = atts.getNamedItem("cap").getNodeValue();
                try {
                    email1 = EMail.parseEMail(atts.getNamedItem("mail1").getNodeValue());
                    email2 = EMail.parseEMail(atts.getNamedItem("mail2").getNodeValue());
                } catch (BadFormatException ex) {
                    Logger.getLogger(XMLImportExporter.class.getName()).log(Level.SEVERE, null, ex);
                }
                cliente.setCodiceCliente(codCli);
                cliente.setCognRsoc1(cognRsoc1);
                cliente.setNomeRsoc2(nomeRagSoc2);
                cliente.setIndirizzo(indirizzo);
                cliente.setNCiv(nCiv);
                cliente.setCap(cap);
                cliente.setCitta(citta);
                cliente.setProvincia(prov);
                cliente.setRegione(regione);
                cliente.setStato(stato);
                cliente.setCell1(cell1);
                cliente.setCell2(cell2);
                cliente.setTel1(tel1);
                cliente.setTel2(tel2);
                cliente.setMail1(email1);
                cliente.setMail2(email2);
                cliente.setProfessionista(professionista);
                cliente.setCfPiva(cfPiva);
                cliente.setNote(note);
                try {
                    clienteDAO.insertCliente(cliente);
                } catch (RecordGiaPresenteException ex) {
                    Logger.getLogger(XMLImportExporter.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ChiavePrimariaException ex) {
                    Logger.getLogger(XMLImportExporter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        // Ripristino opere
        NodeList opereNodeList = doc.getElementsByTagName("opera");
        int totOpere = opereNodeList.getLength();

        for (int s = 0; s < totOpere; s++) {
            Node operaNode = opereNodeList.item(s);
            Opera opera = new Opera();
            if (operaNode.getNodeType() == Node.ELEMENT_NODE) {
                NamedNodeMap atts = operaNode.getAttributes();
                int codiceOpera = Integer.parseInt(atts.getNamedItem("codiceopera").getNodeValue());
                int codiceArtista = Integer.parseInt(atts.getNamedItem("artista").getNodeValue());
                String titolo = atts.getNamedItem("titolo").getNodeValue();
                String tecnica = atts.getNamedItem("tecnica").getNodeValue();
                String dimensioni = atts.getNamedItem("dimensioni").getNodeValue();
                String tipo = atts.getNamedItem("tipo").getNodeValue();
                String fotoString = atts.getNamedItem("foto").getNodeValue();
                URL foto = null;
                String numFattString = atts.getNamedItem("numerofattura").getNodeValue();
                String annoFattString = atts.getNamedItem("annofattura").getNodeValue();
                float prezzo = Float.parseFloat(atts.getNamedItem("prezzo").getNodeValue());
                Artista a = null;
                if (fotoString != null) {
                    if (!fotoString.isEmpty()) {
                        try {
                            File imgFile = new File(fotoString);
                            opera.setFoto(imgFile.toURI().toURL());
                        } catch (MalformedURLException ex) {
                        }
                    }
                }
                try {
                    a = artistaDAO.findArtista(codiceArtista);
                } catch (RecordNonPresenteException ex) {
                    Logger.getLogger(XMLImportExporter.class.getName()).log(Level.SEVERE, null, ex);
                }
                opera.setArtista(a);
                opera.setCodiceOpera(codiceOpera);
                opera.setDimensioni(dimensioni);
                opera.setFoto(foto);
                opera.setPrezzo(prezzo);
                opera.setTecnica(tecnica);
                opera.setTipo(tipo);
                opera.setTitolo(titolo);
                try {
                    operaDAO.insertOpera(opera);
                } catch (RecordGiaPresenteException ex) {
                    Logger.getLogger(XMLImportExporter.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ChiavePrimariaException ex) {
                    Logger.getLogger(XMLImportExporter.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (!numFattString.isEmpty() && !annoFattString.isEmpty()) {
                    Vector<Integer> v = new Vector<Integer>();
                    v.add(Integer.parseInt(numFattString));
                    v.add(Integer.parseInt(annoFattString));
                    v.add(codiceOpera);
                    opereFatture.add(v);
                }
            }

        }

        // Ripristino fatture
        NodeList fattureNodeList = doc.getElementsByTagName("fattura");
        int totFatture = fattureNodeList.getLength();

        for (int s = 0; s < totFatture; s++) {
            Node fatturaNode = fattureNodeList.item(s);
            Fattura fattura = new Fattura();
            if (fatturaNode.getNodeType() == Node.ELEMENT_NODE) {
                NamedNodeMap atts = fatturaNode.getAttributes();
                int numero = Integer.parseInt(atts.getNamedItem("numero").getNodeValue());
                int anno = Integer.parseInt(atts.getNamedItem("anno").getNodeValue());
                Cliente cli = null;
                String data = atts.getNamedItem("data").getNodeValue();
                float sconto = Float.parseFloat(atts.getNamedItem("sconto").getNodeValue());
                float totale = Float.parseFloat(atts.getNamedItem("totale").getNodeValue());
                boolean proforma = Boolean.getBoolean(atts.getNamedItem("proforma").getNodeValue());
                Vector<Opera> opere = new Vector<Opera>();
                try {
                    cli = clienteDAO.findCliente(Integer.parseInt(atts.getNamedItem("idcliente").getNodeValue()));
                } catch (RecordNonPresenteException ex) {
                    Logger.getLogger(XMLImportExporter.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (Vector<Integer> v : opereFatture) {
                    if ((v.elementAt(0) == numero) && (v.elementAt(1) == anno)) {
                        Opera opera = null;
                        try {
                            opera = operaDAO.findOpera((Integer) v.elementAt(2));
                        } catch (RecordNonPresenteException ex) {
                            Logger.getLogger(XMLImportExporter.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        opere.add(opera);
                    }
                }

                Data dataFattura = null;
                try {
                    dataFattura = new Data(data);
                } catch (BadFormatException ex) {
                    Logger.getLogger(XMLImportExporter.class.getName()).log(Level.SEVERE, null, ex);
                }
                fattura.setCliente(cli);
                fattura.setDataFattura(dataFattura);
                fattura.setNumeroFattura(numero);
                fattura.setOpere(opere);
                fattura.setSconto(sconto);
                fattura.setTotale(totale);
                fattura.setProforma(proforma);
                try {
                    fatturaDAO.insertFattura(fattura);
                } catch (RecordGiaPresenteException ex) {
                    Logger.getLogger(XMLImportExporter.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ChiavePrimariaException ex) {
                    Logger.getLogger(XMLImportExporter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
