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
package dita.causeway.replicator.tables.serialize;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.BiConsumer;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.functional.Try;
import org.apache.causeway.commons.internal.base._Casts;
import org.apache.causeway.commons.internal.reflection._GenericResolver.ResolvedMethod;
import org.apache.causeway.commons.internal.reflection._MethodFacades.MethodFacade;
import org.apache.causeway.commons.internal.reflection._Reflect;
import org.apache.causeway.core.metamodel.commons.ParameterConverters;
import org.apache.causeway.core.metamodel.facets.ImperativeFacet;
import org.apache.causeway.core.metamodel.facets.properties.property.modify.PropertyModifyFacet;
import org.apache.causeway.core.metamodel.facets.properties.update.modify.PropertySetterFacet;
import org.apache.causeway.core.metamodel.spec.feature.ObjectAssociation;

import lombok.SneakyThrows;

public record BatchSetter(BiConsumer<Object, Object> setter,
		Class<?> exactPropertyType) {

	@SneakyThrows
	public static Optional<BatchSetter> create(
			final ObjectAssociation assoc,
			final Lookup lookup) {
    	return assoc.lookupFacet(PropertySetterFacet.class)
			.filter(PropertyModifyFacet.class::isInstance)
			.map(PropertyModifyFacet.class::cast)
	    	.flatMap(modifyFacet->extractSetterMethod(modifyFacet))
	    	.map(method->Try.call(()->lookup.unreflect(method))
	    			.valueAsNonNullElseFail())
	    	.map(BatchSetter::new);
    }

	public BatchSetter(final MethodHandle mh) {
		this((obj, value)->{
			try {
				mh.invoke(obj, value);
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		}, mh.type().parameterType(0));
	}

	public void setAssociationValue(final Object domainPojo, final Object valuePojo) {
		setter.accept(domainPojo,
				ParameterConverters.DEFAULT.convert(exactPropertyType, valuePojo));
	}

	@SneakyThrows
	private static Optional<Method> extractSetterMethod(final PropertyModifyFacet propertyModifyFacet) {
		var setterFacetField = PropertyModifyFacet.class.getDeclaredField("setterFacet");
		var setterFacet = (PropertySetterFacet) _Reflect.getFieldOn(setterFacetField, propertyModifyFacet);
    	return _Casts.castTo(ImperativeFacet.class, setterFacet)
    		.map(ImperativeFacet::getMethods)
    		.flatMap(Can::getFirst)
    		.flatMap(MethodFacade::asMethod)
    		.map(ResolvedMethod::method);
    }
}
