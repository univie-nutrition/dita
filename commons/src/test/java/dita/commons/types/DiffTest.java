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
package dita.commons.types;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class DiffTest {

	record Item(int id, String name) {
	}

	@Test
	void diff() {

		var base = List.of(new Item(1, "one"), new Item(2, "two"), new Item(3, "three"));
		var main = List.of(new Item(1, "one"), new Item(2, "two!"), new Item(4, "four"));


		var diff = Diff.typed(Item.class, Item.class);

		diff.process(main, base, i->"" + i.id(), i->"" + i.id(), Item::equals);

		assertEquals(1, diff.leftOuter().size());
		assertEquals(1, diff.rightOuter().size());
		assertEquals(1, diff.innerMatch().size());
		assertEquals(1, diff.innerMismatch().size());

		// addition
		assertEquals(new Item(4, "four"), diff.leftOuter().getFirst());
		// deletion
		assertEquals(new Item(3, "three"), diff.rightOuter().getFirst());
		// unchanged
		assertEquals(new Pair<>(new Item(1, "one"), new Item(1, "one")),
				diff.innerMatch().getFirst());
		// changed
		assertEquals(new Pair<>(new Item(2, "two!"), new Item(2, "two")),
				diff.innerMismatch().getFirst());
	}

}
