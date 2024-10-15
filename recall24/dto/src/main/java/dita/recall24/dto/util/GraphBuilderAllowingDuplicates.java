/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package dita.recall24.dto.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.collections.ImmutableEnumSet;
import org.apache.causeway.commons.graph.GraphUtils.Graph;
import org.apache.causeway.commons.graph.GraphUtils.GraphKernel;
import org.apache.causeway.commons.graph.GraphUtils.GraphKernel.GraphCharacteristic;
import org.apache.causeway.commons.internal.collections._PrimitiveCollections.IntList;

import lombok.NonNull;

class GraphBuilderAllowingDuplicates<T> {
    @SuppressWarnings("unused")
    private final Class<T> nodeType;
    private final ImmutableEnumSet<GraphCharacteristic> characteristics;
    private final List<T> nodeList;
    private final IntList fromNode = new IntList(4); // best guess initial edge capacity
    private final IntList toNode = new IntList(4); // best guess initial edge capacity

    // -- FACTORIES

    public static <T> GraphBuilderAllowingDuplicates<T> directed(final Class<T> nodeType) {
        return new GraphBuilderAllowingDuplicates<T>(nodeType, GraphCharacteristic.directed());
    }

    public static <T> GraphBuilderAllowingDuplicates<T> undirected(final Class<T> nodeType) {
        return new GraphBuilderAllowingDuplicates<T>(nodeType, GraphCharacteristic.undirected());
    }

    /**
     * Adds a new node to the graph.
     * @apiNote allows duplicates with respect to {@link Objects#equals} are not added
     */
    public GraphBuilderAllowingDuplicates<T> addNode(final @NonNull T node) {
        addNodeHonoringIndexMap(node);
        return this;
    }

    /**
     * Adds a new edge to the graph. Indices are zero-based references to the node list.
     * @apiNote Indices are not bound checked until the {@link #build()} method is called.
     */
    public GraphBuilderAllowingDuplicates<T> addEdge(final int fromIndex, final int toIndex) {
        // no bound check here, but later when the kernel is built
        fromNode.add(fromIndex);
        toNode.add(toIndex);
        return this;
    }

    /**
     * Current node count. It increments with each node added.
     */
    public int nodeCount() {
        return nodeList.size();
    }

    /**
     * Current edge count. It increments with each edge added.
     */
    public int edgeCount() {
        return fromNode.size();
    }

    // -- CONSTRUCTION

    private GraphBuilderAllowingDuplicates(final Class<T> nodeType, final ImmutableEnumSet<GraphCharacteristic> characteristics) {
        this.nodeType = nodeType;
        this.characteristics = characteristics;
        this.nodeList = new ArrayList<>();
    }

    public Graph<T> build() {
        var kernel = new GraphKernel(nodeList.size(), characteristics);
        var edgeCount = edgeCount();
        for (int edgeIndex = 0; edgeIndex<edgeCount; edgeIndex++) {
            kernel.addEdge(fromNode.get(edgeIndex), toNode.get(edgeIndex));
        }
        var graph = new Graph<T>(kernel,
                Can.ofCollection(nodeList),
                Collections.emptyMap());
        return graph;
    }

    // -- HELPER

    /**
     * Created only if triggered by {@link #addEdge(Object, Object)}
     * or {@link #addEdge(Object, Object, Object)}.
     */
    private Map<T, Integer> nodeIndexByNode = null;

    private int addNodeHonoringIndexMap(final T node) {
        final int nextIndex = nodeList.size();
        nodeList.add(node);
        if(nodeIndexByNode!=null) {
            nodeIndexByNode.put(node, nextIndex);
        }
        return nextIndex;
    }

}

