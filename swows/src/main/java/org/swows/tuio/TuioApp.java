package org.swows.tuio;

import java.awt.GraphicsConfiguration;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.apache.batik.util.RunnableQueue;
import org.swows.producer.DataflowProducer;
import org.swows.runnable.RunnableContext;
import org.swows.xmlinrdf.DomDecoder;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.sparql.core.DatasetGraph;
import com.hp.hpl.jena.sparql.core.DatasetGraphFactory;

public class TuioApp extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RunnableQueue batikRunnableQueue;

	public TuioApp(String title, final GraphicsConfiguration gc, Graph dataflowGraph) {
		this(title, gc, dataflowGraph, true);
	}
		
	public TuioApp(String title, final GraphicsConfiguration gc, Graph dataflowGraph, final boolean fullscreen) {
		this(title, gc, dataflowGraph, fullscreen, gc.getBounds().width, gc.getBounds().height,false);
	}
	
	public TuioApp(String title, final GraphicsConfiguration gc, Graph dataflowGraph, final boolean fullscreen, boolean autoRefresh ) {
		this(title, gc, dataflowGraph, fullscreen, gc.getBounds().width, gc.getBounds().height,autoRefresh);
	}
	
	public TuioApp(
			String title, final GraphicsConfiguration gc, Graph dataflowGraph,
			final boolean fullscreen, int width, int height) {
		this(title, gc, dataflowGraph, fullscreen, width, height, false);
	}
	
	public TuioApp(
			String title, final GraphicsConfiguration gc, Graph dataflowGraph,
			final boolean fullscreen, int width, int height, boolean autoRefresh ) {
		super(title, gc);
    	final TuioGateway tuioGateway = new TuioGateway(autoRefresh);
		final DatasetGraph inputDatasetGraph = DatasetGraphFactory.create(tuioGateway.getGraph());
		DataflowProducer applyOps =	new DataflowProducer(dataflowGraph, inputDatasetGraph);
		Graph outputGraph = applyOps.createGraph(inputDatasetGraph);

		final JSVGCanvas svgCanvas = new JSVGCanvas();
        svgCanvas.setSize(width,height);
        
        // Set the JSVGCanvas listeners.
        svgCanvas.addSVGDocumentLoaderListener(new SVGDocumentLoaderAdapter() {
            public void documentLoadingStarted(SVGDocumentLoaderEvent e) {
                //label.setText("Document Loading...");
            }
            public void documentLoadingCompleted(SVGDocumentLoaderEvent e) {
                //label.setText("Document Loaded.");
            }
        });

        svgCanvas.addGVTTreeBuilderListener(new GVTTreeBuilderAdapter() {
            public void gvtBuildStarted(GVTTreeBuilderEvent e) {
                //label.setText("Build Started...");
            }
            public void gvtBuildCompleted(GVTTreeBuilderEvent e) {
                //label.setText("Build Done.");
//                frame.pack();
            }
        });

        svgCanvas.addGVTTreeRendererListener(new GVTTreeRendererAdapter() {
            public void gvtRenderingPrepare(GVTTreeRendererEvent e) {
                //label.setText("Rendering Started...");
            }
            public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
                // Display the frame.
            	batikRunnableQueue = svgCanvas.getUpdateManager().getUpdateRunnableQueue();
            	if (fullscreen)
            		gc.getDevice().setFullScreenWindow(TuioApp.this);
                pack();
                setVisible(true);
            	tuioGateway.connect();
            }
        });
		
		getContentPane().setSize(width, height);
        getContentPane().add(svgCanvas);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        setSize(width, height);

		DOMImplementation domImpl = SVGDOMImplementation.getDOMImplementation();
		Document xmlDoc =
				DomDecoder.decodeOne(
						outputGraph,
						domImpl,
						new RunnableContext() {
							@Override
							public void run(Runnable runnable) {
								try {
									batikRunnableQueue.invokeAndWait(runnable);
								} catch(InterruptedException e) {
									throw new RuntimeException(e);
								}
							}
						});

        svgCanvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
		svgCanvas.setDocument(xmlDoc);

//        TransformerFactory transformerFactory = TransformerFactory.newInstance();
//		Transformer transformer;
//		try {
//			transformer = transformerFactory.newTransformer();
//			DOMSource source = new DOMSource(xmlDoc);
//			StreamResult result =  new StreamResult(System.out);
//			transformer.transform(source, result);
//		} catch (TransformerException e) {
//			e.printStackTrace();
//		}

	}
	
}
