package org.swows.test;

import java.util.List;
import java.util.Vector;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.swows.datatypes.SmartFileManager;
import org.swows.xmlinrdf.DomDecoder;
import org.w3c.dom.Document;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.DatasetFactory;

public class LiteralParsingTest {

//	public static void test1() {
//    	String sparqlQuery = "PREFIX foaf:  <http://xmlns.com/foaf/0.1/> SELECT ?name WHERE { ?person foaf:name ?name . }";
//    	RDFExpressable re = new SparqlJenaQuery(sparqlQuery);
//    	Graph graph = ModelFactory.createDefaultModel().getGraph();
//    	Node root = re.addRootedGraph(graph);
//    	Model resultModel = ModelFactory.createModelForGraph(graph);
//    	Resource rootRes = resultModel.wrapAsResource(root);
//    	System.out.println(rootRes.toString());
//    	System.out.println("");
//    	resultModel.write(System.out,"N-TRIPLES");
//		
//	}
	
	public static void main(final String[] args) throws TransformerException {
    	
    	
		String baseUri = "/home/miguel/TUIO/";

		String mainGraphUrl = baseUri + "test2.n3";
		//String mainGraphUrl = baseUri + "test2-2.n3";
		//String defaultInputUrl = baseUri + "test3.n3";
		List<String> graphUris = new Vector<String>();
		//String schemaUrl = "/home/miguel/TBCFreeWorkspace/Prova/spinx.n3";
		//graphUris.add(schemaUrl);

		final Dataset inputDataset = DatasetFactory.create(mainGraphUrl, graphUris, SmartFileManager.get(), null);
		//Graph defaultGraph = inputDataset.asDatasetGraph().getDefaultGraph();
		//SparqlJenaQuery.developInRDF(defaultGraph);
		inputDataset.getDefaultModel().write(System.out,"N3");
		Graph defaultGraph =  inputDataset.asDatasetGraph().getDefaultGraph();
		Document xmlDoc = DomDecoder.decodeOne(defaultGraph);

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(xmlDoc);
		StreamResult result =  new StreamResult(System.out);
		transformer.transform(source, result);

	}
}