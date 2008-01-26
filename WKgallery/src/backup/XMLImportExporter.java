/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backup;

import abstractlayer.Artista;
import abstractlayer.Cliente;
import abstractlayer.Fattura;
import abstractlayer.Opera;
import abstractlayer.Regione;
import daorules.ArtistaDAO;
import daorules.ClienteDAO;
import daorules.FatturaDAO;
import daorules.OperaDAO;
import exceptions.RecordCorrelatoException;
import exceptions.RecordNonPresenteException;
import java.io.File;
import java.util.Vector;
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
import org.xml.sax.SAXParseException;
import utilities.Data;
import utilities.EMail;

/**
 *
 * @author Marco Celesti
 */
public class XMLImportExporter {

    File fileBkp;
    ArtistaDAO artistaDao;
    ClienteDAO clienteDao;
    OperaDAO operaDao;
    FatturaDAO fatturaDao;

    public XMLImportExporter(File fileBkp, ArtistaDAO artistaDao, ClienteDAO clienteDao, OperaDAO operaDao, FatturaDAO fatturaDao) {
        this.artistaDao = artistaDao;
        this.clienteDao = clienteDao;
        this.fatturaDao = fatturaDao;
        this.operaDao = operaDao;
        this.fileBkp = fileBkp;
    }

    public void saveToFile() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            Element e = doc.createElement("wkgallery");
            doc.appendChild(e);

            for (Artista a : artistaDao.findAllArtisti()) {
                Element artista = doc.createElement("artista");
                artista.setAttribute("codiceartista", "" + a.getCodiceArtista());
                artista.setAttribute("cognome", a.getCognome());
                artista.setAttribute("nome", a.getNome());
                artista.setAttribute("notebiografiche", a.getNoteBiografiche());
                e.appendChild(artista);
            }

            for (Cliente c : clienteDao.findAllClienti()) {
                Element cliente = doc.createElement("cliente");
                cliente.setAttribute("id", c.getCodiceCliente());
                cliente.setAttribute("cogn_rsoc1", c.getCognRsoc1());
                cliente.setAttribute("nome_rsoc2", c.getNomeRsoc2());
                cliente.setAttribute("indirizzo", c.getIndirizzo());
                cliente.setAttribute("id", c.getCodiceCliente());
                cliente.setAttribute("nciv", "" + c.getNCiv());
                cliente.setAttribute("citta", c.getCitta());
                cliente.setAttribute("provincia", c.getProvincia());
                cliente.setAttribute("regione", c.getRegione().getNomeRegione());
                cliente.setAttribute("stato", c.getStato());
                cliente.setAttribute("telefono1", c.getTel1());
                cliente.setAttribute("telefono2", c.getTel2());
                cliente.setAttribute("cellulare1", c.getCell1());
                cliente.setAttribute("cellulare2", c.getCell2());
                cliente.setAttribute("mail1", EMail.toString(c.getMail1()));
                cliente.setAttribute("mail2", EMail.toString(c.getMail2()));
                cliente.setAttribute("cf_piva", c.getCfPiva());
                cliente.setAttribute("note", c.getNote());
                cliente.setAttribute("professionista", "" + c.isProfessionista());
                cliente.setAttribute("cap", c.getCap());
                e.appendChild(cliente);
            }

            for (Opera o : operaDao.findAllOpere()) {
                Element opera = doc.createElement("opera");
                opera.setAttribute("codiceopera", o.getCodiceOpera());
                opera.setAttribute("artista", "" + o.getArtista().getCodiceArtista());
                opera.setAttribute("titolo", o.getTitolo());
                opera.setAttribute("tecnica", o.getTecnica());
                opera.setAttribute("dimensioni", o.getDimensioni());
                opera.setAttribute("tipo", o.getTipo());
                opera.setAttribute("venduto", "" + o.isVenduto());
                opera.setAttribute("foto", o.getFoto());
                opera.setAttribute("prezzo", "" + o.getPrezzo());
                try {
                    opera.setAttribute("numerofattura", "" + fatturaDao.selectFatturaPerOpera(o).getNumeroFattura());
                    opera.setAttribute("annofattura", "" + fatturaDao.selectFatturaPerOpera(o).getDataFattura().getAnno());
                } catch (RecordNonPresenteException exc) {
                    opera.setAttribute("numerofattura", "");
                    opera.setAttribute("annofattura", "");
                }
                e.appendChild(opera);
            }

            for (Fattura f : fatturaDao.findAllFatture()) {
                Element fattura = doc.createElement("fattura");
                fattura.setAttribute("numero", "" + f.getNumeroFattura());
                fattura.setAttribute("anno", "" + f.getDataFattura().getAnno());
                fattura.setAttribute("idcliente", f.getCliente().getCodiceCliente());
                fattura.setAttribute("data", f.getDataFattura().toString());
                fattura.setAttribute("sconto", "" + f.getSconto());
                fattura.setAttribute("totale", "" + f.getTotale());
                e.appendChild(fattura);
            }
            // SAVE
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

    }

    public void restoreFromFile() {
        Vector<Object[]> opereFatture = new Vector<Object[]>();
        try {
            operaDao.deleteAllOpere();
            fatturaDao.deleteAllFatture();
            clienteDao.deleteAllClienti();
            artistaDao.deleteAllArtisti();
        } catch (RecordCorrelatoException rce) {
            System.err.println(rce);
        }

        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(fileBkp);

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
                    artistaDao.insertArtista(artista);
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
                    String codCli = atts.getNamedItem("id").getNodeValue();
                    String cognRsoc1 = atts.getNamedItem("cogn_rsoc1").getNodeValue();
                    String nomeRagSoc2 = atts.getNamedItem("nome_rsoc2").getNodeValue();
                    String indirizzo = atts.getNamedItem("indirizzo").getNodeValue();
                    int nCiv = Integer.parseInt(atts.getNamedItem("nciv").getNodeValue());
                    String citta = atts.getNamedItem("citta").getNodeValue();
                    String prov = atts.getNamedItem("provincia").getNodeValue();
                    Regione regione = Regione.getRegione(atts.getNamedItem("regione").getNodeValue());
                    String stato = atts.getNamedItem("stato").getNodeValue();
                    String tel1 = atts.getNamedItem("telefono1").getNodeValue();
                    String tel2 = atts.getNamedItem("telefono2").getNodeValue();
                    String cell1 = atts.getNamedItem("cellulare1").getNodeValue();
                    String cell2 = atts.getNamedItem("cellulare2").getNodeValue();
                    EMail email1 = EMail.toEMail(atts.getNamedItem("mail1").getNodeValue());
                    EMail email2 = EMail.toEMail(atts.getNamedItem("mail2").getNodeValue());
                    String cfPiva = atts.getNamedItem("cf_piva").getNodeValue();
                    String note = atts.getNamedItem("note").getNodeValue();
                    boolean professionista = Boolean.getBoolean(atts.getNamedItem("professionista").getNodeValue());
                    String cap = atts.getNamedItem("cap").getNodeValue();
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
                    clienteDao.insertCliente(cliente);
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
                    String codiceOpera = atts.getNamedItem("codiceopera").getNodeValue();
                    int codiceArtista = Integer.parseInt(atts.getNamedItem("artista").getNodeValue());
                    String titolo = atts.getNamedItem("titolo").getNodeValue();
                    String tecnica = atts.getNamedItem("tecnica").getNodeValue();
                    String dimensioni = atts.getNamedItem("dimensioni").getNodeValue();
                    String tipo = atts.getNamedItem("tipo").getNodeValue();
                    boolean venduto = Boolean.getBoolean(atts.getNamedItem("venduto").getNodeValue());
                    String foto = atts.getNamedItem("foto").getNodeValue();
                    String numFattString = atts.getNamedItem("numerofattura").getNodeValue();
                    String annoFattString = atts.getNamedItem("annofattura").getNodeValue();
                    float prezzo = Float.parseFloat(atts.getNamedItem("prezzo").getNodeValue());
                    Artista a = artistaDao.findArtista(codiceArtista);
                    opera.setArtista(a);
                    opera.setCodiceOpera(codiceOpera);
                    opera.setDimensioni(dimensioni);
                    opera.setFoto(foto);
                    opera.setPrezzo(prezzo);
                    opera.setTecnica(tecnica);
                    opera.setTipo(tipo);
                    opera.setTitolo(titolo);
                    opera.setVenduto(venduto);
                    operaDao.insertOpera(opera);
                    if (!numFattString.isEmpty() && !annoFattString.isEmpty()) {
                        Object[] o = new Object[3];
                        o[0] = Integer.parseInt(numFattString);
                        o[1] = Integer.parseInt(annoFattString);
                        o[2] = codiceOpera;
                        opereFatture.add(o);
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
                    Cliente cli = clienteDao.findCliente(atts.getNamedItem("idcliente").getNodeValue());
                    String data = atts.getNamedItem("data").getNodeValue();
                    float sconto = Float.parseFloat(atts.getNamedItem("sconto").getNodeValue());
                    float totale = Float.parseFloat(atts.getNamedItem("totale").getNodeValue());
                    Vector<Opera> opere = new Vector<Opera>();
                    for (Object[] obj : opereFatture) {
                        if (((Integer) obj[0] == numero) && ((Integer) obj[1] == anno)) {
                            Opera opera = operaDao.findOpera((String) obj[2]);
                            opere.add(opera);
                        }
                    }

                    Data dataFattura = new Data(data);
                    fattura.setCliente(cli);
                    fattura.setDataFattura(dataFattura);
                    fattura.setNumeroFattura(numero);
                    fattura.setOpere(opere);
                    fattura.setSconto(sconto);
                    fattura.setTotale(totale);
                    fatturaDao.insertFattura(fattura);
                }
            }
        } catch (SAXParseException err) {
            System.out.println("** Parsing error" + ", line " + err.getLineNumber() + ", uri " + err.getSystemId());
            System.out.println(" " + err.getMessage());

        } catch (SAXException e) {
            Exception x = e.getException();
            ((x == null) ? e : x).printStackTrace();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }//end of main
}
