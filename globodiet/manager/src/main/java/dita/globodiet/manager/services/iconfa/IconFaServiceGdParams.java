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
package dita.globodiet.manager.services.iconfa;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import org.apache.causeway.applib.fa.FontAwesomeLayers;

import dita.commons.services.iconfa.IconFaService;
import dita.globodiet.dom.params.classification.FoodSubgroup;

@Service
public class IconFaServiceGdParams
implements IconFaService {

    @Override
    public FontAwesomeLayers iconFaLayers(final @Nullable Object entity) {
        if(entity instanceof FoodSubgroup foodSubgroup) {
            return icon(foodSubgroup);
        }
        return FontAwesomeLayers.singleIcon("fa-cube");
    }

    // -- HELPER

    private FontAwesomeLayers icon(final FoodSubgroup foodSubgroup) {
        if(foodSubgroup.getFoodSubSubgroupCode()==null) {
            return FontAwesomeLayers.singleIcon("fa-solid fa-layer-group fa-lg food-color");
        }
        return FontAwesomeLayers.stackBuilder()
            .containerCssClasses("fa-lg")
            .containerCssStyle("width:1.25em")
            .addIconEntry("fa-solid fa-layer-group fa-stack-1x food-color")
            .addIconEntry("fa-solid fa-circle-chevron-down fa-stack-1x food-color overlay", "left:0.85em;top:0.85em")
            .build();
    }

// variant with 1 or 2 overlays
//    private FontAwesomeLayers icon(final FoodSubgroup foodSubgroup) {
//        var stackBuilder =  FontAwesomeLayers.stackBuilder()
//                .containerCssClasses("fa-lg")
//                .containerCssStyle("width:1.25em")
//                .addIconEntry("fa-solid fa-layer-group fa-stack-1x food-color");
//        if(foodSubgroup.getFoodSubSubgroupCode()==null) {
//            stackBuilder
//                .addIconEntry("fa-solid fa-circle-chevron-down fa-stack-1x food-color overlay", "left:0.85em;top:0.85em");
//        } else {
//            stackBuilder
//                .addIconEntry("fa-solid fa-circle-chevron-down fa-stack-1x food-color overlay", "left:0.25em;top:0.85em")
//                .addIconEntry("fa-solid fa-circle-chevron-down fa-stack-1x food-color overlay", "left:1em;top:0.85em");
//        }
//        return stackBuilder
//                .build();
//    }

/* icon experiments
<h2>Grouping Symbols</h2>
<p>

<span>
<i class="fa fa-solid fa-shapes food-color"></i>
food group
</span>

</p><p>

<span class="fa-stack">
   <i class="fa-solid fa-shapes fa-stack-1x food-color"></i>
   <i class="fa-circle-chevron-down fa-stack-1x food-color overlay" style="left:0.85em;top:0.85em;"></i>
</span>
food sub group

</p><p>

<span class="fa-stack">
   <i class="fa-solid fa-shapes fa-stack-1x food-color"></i>
   <i class="fa-circle-chevron-down fa-stack-1x food-color overlay" style="left:0.3em;top:0.85em;"></i>
   <i class="fa-circle-chevron-down fa-stack-1x food-color overlay" style="left:1em;top:0.85em;"></i>
</span>
food sub sub group

</p><p>

<span>
<i class="fa fa-solid fa-shapes recipe-color"></i>
recipe group
</span>

</p><p>

<span class="fa-stack">
   <i class="fa fa-solid fa-shapes fa-stack-1x recipe-color"></i>
   <i class="fa fa-circle-chevron-down fa-stack-1x recipe-color overlay" style="left:0.85em;top:0.85em;"></i>
</span>
recipe sub group

</p>

<h2>Pathway Symbols</h2>

<p>

<span class="fa-stack">
   <i class="fa fa-solid fa-person-walking-arrow-right fa-stack-1x food-color"></i>
   <i class="fa fa-solid fa-tag fa-stack-1x food-color overlay" style="left:0.5em;top:0.85em;"></i>
</span>
facet descriptor pathway for food group

</p><p>

<span class="fa-stack">
   <i class="fa fa-solid fa-person-walking-arrow-right fa-stack-1x food-color"></i>
   <i class="fa fa-solid fa-tag fa-stack-1x food-color overlay" style="left:0.5em;top:0.85em;"></i>
   <i class="fa fa-solid fa-exclamation fa-stack-1x food-color overlay" style="left:0.35em;top:-0.85em;"></i>
</span>
facet descriptor pathway for food

</p><p>

<span class="fa-stack">
   <i class="fa fa-solid fa-person-walking-arrow-right fa-stack-1x food-color"></i>
   <i class="fa fa-solid fa-scale-balanced fa-stack-1x food-color overlay" style="left:0.5em;top:0.85em;"></i>
</span>
quantification method pathway for food group

</p><p>

<span class="fa-stack">
   <i class="fa fa-solid fa-person-walking-arrow-right fa-stack-1x food-color"></i>
   <i class="fa fa-solid fa-scale-balanced fa-stack-1x food-color overlay" style="left:0.5em;top:0.85em;"></i>
   <i class="fa fa-solid fa-exclamation fa-stack-1x food-color overlay" style="left:0.35em;top:-0.85em;"></i>
</span>
quantification method pathway for food

</p><p>

<span class="fa-stack">
   <i class="fa fa-solid fa-person-walking-arrow-right fa-stack-1x food-color"></i>
   <i class="fa fa-regular fa-question fa-stack-1x food-color overlay" style="left:0.5em;top:0.85em;"></i>
</span>
probing question pathway for food

</p><p>

<span class="fa-stack">
   <i class="fa fa-solid fa-person-walking-arrow-right fa-stack-1x recipe-color"></i>
   <i class="fa fa-regular fa-question fa-stack-1x recipe-color overlay" style="left:0.5em;top:0.85em;"></i>
</span>
probing question pathway for recipe

</p>
*/

}
