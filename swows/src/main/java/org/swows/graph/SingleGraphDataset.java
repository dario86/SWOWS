/*
 * Copyright (c) 2011 Miguel Ceriani
 * miguel.ceriani@gmail.com

 * This file is part of Semantic Web Open Web Server (SWOWS).

 * SWOWS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.

 * SWOWS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.

 * You should have received a copy of the GNU Affero General
 * Public License along with SWOWS.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.swows.graph;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.sparql.core.DatasetGraphCollection;

/**
 * The Class SingleGraphDataset it's a dataset containing
 * only the default graph (not the named graphs).
 */
public class SingleGraphDataset extends DatasetGraphCollection {
	
	private Graph graph;

	/**
	 * Instantiates a new single graph dataset.
	 *
	 * @param graph the graph
	 */
	public SingleGraphDataset(Graph graph) {
		this.graph = graph;
	}
	
	/* (non-Javadoc)
	 * @see com.hp.hpl.jena.sparql.core.DatasetGraphCollection#listGraphNodes()
	 */
	@Override
	public Iterator<Node> listGraphNodes() {
		return new Iterator<Node>() {
			@Override
			public boolean hasNext() {
				return false;
			}
			@Override
			public Node next() {
				throw new NoSuchElementException();
			}
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	/* (non-Javadoc)
	 * @see com.hp.hpl.jena.sparql.core.DatasetGraphBase#getDefaultGraph()
	 */
	@Override
	public Graph getDefaultGraph() {
		return graph;
	}

	/* (non-Javadoc)
	 * @see com.hp.hpl.jena.sparql.core.DatasetGraphBase#getGraph(com.hp.hpl.jena.graph.Node)
	 */
	@Override
	public Graph getGraph(Node graphNode) {
		return null;
	}

}
