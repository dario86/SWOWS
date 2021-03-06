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
package org.swows.producer;

import com.hp.hpl.jena.graph.Graph;
import com.hp.hpl.jena.sparql.core.DatasetGraph;

/**
 * The Abstract Class DatasetProducer is the common
 * ancestor of all producer classes that return a dataset.
 * Derived concrete classes must implement the
 * {@code createDataset} method.
 */
public abstract class DatasetProducer implements Producer {

	/* (non-Javadoc)
	 * @see org.swows.producer.Producer#createDataset(com.hp.hpl.jena.sparql.core.DatasetGraph)
	 */
	@Override
	public abstract DatasetGraph createDataset(DatasetGraph inputDataset);

	/**
	 * Return the default graph of the dataset returned by {@code createDataset(inputDataset)}.
	 *
	 * @param inputDataset the input dataset
	 * @return the default graph
	 * @see org.swows.producer.Producer#createGraph(com.hp.hpl.jena.sparql.core.DatasetGraph)
	 */
	@Override
	public Graph createGraph(DatasetGraph inputDataset) {
		return createDataset(inputDataset).getDefaultGraph();
	}

}
