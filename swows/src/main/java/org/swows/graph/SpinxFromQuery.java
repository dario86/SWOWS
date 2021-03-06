package org.swows.graph;

import com.hp.hpl.jena.graph.BulkUpdateHandler;
import com.hp.hpl.jena.graph.Capabilities;
import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.GraphEventManager;
import com.hp.hpl.jena.graph.GraphStatisticsHandler;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Reifier;
import com.hp.hpl.jena.graph.TransactionHandler;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.graph.TripleMatch;
import com.hp.hpl.jena.graph.impl.SimpleEventManager;
import com.hp.hpl.jena.graph.impl.SimpleTransactionHandler;
import com.hp.hpl.jena.graph.query.Query;
import com.hp.hpl.jena.graph.query.QueryHandler;
import com.hp.hpl.jena.graph.query.SimpleQueryHandler;
import com.hp.hpl.jena.shared.AddDeniedException;
import com.hp.hpl.jena.shared.DeleteDeniedException;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.shared.impl.PrefixMappingImpl;
import com.hp.hpl.jena.sparql.graph.Reifier2;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class SpinxFromQuery implements Graph {
	
//	private Query query;
	private QueryHandler queryHandler = new SimpleQueryHandler(this);
	private TransactionHandler transactionHandler = new SimpleTransactionHandler();
	private GraphEventManager eventManager = new SimpleEventManager(this);
	private GraphStatisticsHandler graphStatisticsHandler =
			new GraphStatisticsHandler() {
				@Override
				public long getStatistic(Node S, Node P, Node O) {
					return -1;
				}
			};
	private Reifier reifier = new Reifier2(this);
	private PrefixMapping prefixMapping = new PrefixMappingImpl();
	private boolean closed = false;
	
	public SpinxFromQuery(Query query) {
//		this.query = query;
	}

	@Override
	public void add(Triple t) throws AddDeniedException {
		throw new AddDeniedException("Read-Only Query-based Graph");
	}

	@Override
	public boolean dependsOn(Graph other) {
		return false;
	}

	@Override
	public QueryHandler queryHandler() {
		return queryHandler;
	}

	@Override
	public TransactionHandler getTransactionHandler() {
		return transactionHandler;
	}

	@Override
	public BulkUpdateHandler getBulkUpdateHandler() {
		// Should be ok to return null for a readonly graph
		return null;
	}

	@Override
	public Capabilities getCapabilities() {
		return new Capabilities() {
			
			@Override
			public boolean sizeAccurate() {
				return false;
			}
			
			@Override
			public boolean iteratorRemoveAllowed() {
				return false;
			}
			
			@Override
			public boolean handlesLiteralTyping() {
				return true;
			}
			
			@Override
			public boolean findContractSafe() {
				// ???
				return false;
			}
			
			@Override
			public boolean deleteAllowed(boolean everyTriple) {
				return false;
			}
			
			@Override
			public boolean deleteAllowed() {
				return false;
			}
			
			@Override
			public boolean canBeEmpty() {
				// there should be at least the root xml element
				return false;
			}
			
			@Override
			public boolean addAllowed(boolean everyTriple) {
				return false;
			}
			
			@Override
			public boolean addAllowed() {
				return false;
			}
			
		};
	}

	@Override
	public GraphEventManager getEventManager() {
		// TODO Auto-generated method stub
		return eventManager;
	}

	@Override
	public GraphStatisticsHandler getStatisticsHandler() {
		return graphStatisticsHandler;
	}

	@Override
	public Reifier getReifier() {
		return reifier;
	}

	@Override
	public PrefixMapping getPrefixMapping() {
		return prefixMapping;
	}

	@Override
	public void delete(Triple t) throws DeleteDeniedException {
		throw new DeleteDeniedException("Read-Only Query-based Graph");
	}

	@Override
	public ExtendedIterator<Triple> find(TripleMatch m) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExtendedIterator<Triple> find(Node s, Node p, Node o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isIsomorphicWith(Graph g) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Node s, Node p, Node o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Triple t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void close() {
		closed = true;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		/* By graph interface contract, implementors are
		 * forced just to give a lower bound, we return
		 * 0 for simplicity. 
		 */
		return 0;
	}

	@Override
	public boolean isClosed() {
		return closed;
	}

}
