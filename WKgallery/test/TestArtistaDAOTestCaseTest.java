/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.netbeans.junit.NbTestCase;
import org.netbeans.junit.NbTestSuite;

/**
 * A Test based on NbTestCase. It is a NetBeans extension to JUnit TestCase
 * which among othres allows to compare files via assertFile methods, create
 * working directories for testcases, write to log files, compare log files
 * against reference (golden) files, etc.
 * 
 * More details here http://xtest.netbeans.org/NbJUnit/NbJUnit-overview.html.
 * 
 * @author Marco Celesti
 */
public class TestArtistaDAOTestCaseTest extends NbTestCase {

    /** Default constructor.
     * @param testName name of particular test case
     */
    public TestArtistaDAOTestCaseTest(String testName) {
        super(testName);
    }

    /** Creates suite from particular test cases. You can define order of testcases here.
     * @return 
     */
    public static NbTestSuite suite() {
        NbTestSuite suite = new NbTestSuite();
        suite.addTest(new TestArtistaDAOTestCaseTest("testInsertArtista"));
        suite.addTest(new TestArtistaDAOTestCaseTest("testDeleteArtista"));
        suite.addTest(new TestArtistaDAOTestCaseTest("testFindArtista"));
        suite.addTest(new TestArtistaDAOTestCaseTest("testFindAllArtisti"));
        suite.addTest(new TestArtistaDAOTestCaseTest("testUpdateArtista"));
        suite.addTest(new TestArtistaDAOTestCaseTest("testDeleteAllArtisti"));
        return suite;
    }

    /* Method allowing test execution directly from the IDE. */
    public static void main(java.lang.String[] args) {
        // run whole suite
        junit.textui.TestRunner.run(suite());
    // run only selected test case
    //junit.textui.TestRunner.run(new TestArtistaDAOTestCaseTest("test1"));
    }

    /** Called before every test case. */
    @Override
    public void setUp() {
        System.out.println("########  " + getName() + "  #######");
    }

    /** Called after every test case. */
    @Override
    public void tearDown() {
    }

    // Add test methods here, they have to start with 'test'.
    /** Test case InsertArtista. */
    public void testInsertArtista() {
        System.out.println("ciao");
    }

    /** Test case DeleteArtista. */
    public void testDeleteArtista() {
    }
    
    /** Test case FindArtista. */
    public void testFindArtista() {
    }
    
    /** Test case FindAllArtisti. */
    public void testFindAllArtisti() {
    }
    
    /** Test case UpdateArtista. */
    public void testUpdateArtista() {
    }
    
    /** Test case DeleteAllArtisti. */
    public void testDeleteAllArtisti() {
    }
}