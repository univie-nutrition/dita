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
package dita.foodex.validate.api;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.base._Strings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor(staticName = "of") @NoArgsConstructor
public class ValidationResponse {
    
    @Data @AllArgsConstructor(staticName = "of") @NoArgsConstructor
    public static class Message {
        String message;
        String risk;
        String risk2;
        
        public static Message failure(String msg) {
            return Message.of(msg, "ERROR", "ERROR");
        }
        
    }
    
    String fullCode;
    
    List<Message> messages;
    
    boolean isEmpty;
    
    public String asSingleMessage() {
        return _NullSafe.stream(getMessages())
        .map(msg->String.format("%s ;%s;%s", msg.getMessage(), msg.getRisk(), msg.getRisk2()))
        .collect(Collectors.joining("|"));
    }
    
    public static ValidationResponse parse(String rawValidation, String fullCode) {
        if(_NullSafe.isEmpty(rawValidation)) {
            return empty();
        }
        
        var messages = _Strings.splitThenStream(rawValidation, "|")
                .map(String::trim)
                .filter(_Strings::isNotEmpty)
                .<Message>map(rawMessage->{
            
                    var chunks = _Strings.splitThenStream(rawMessage, ";")
                            .map(String::trim)
                            .collect(Collectors.toList());
                    
                    if(chunks.size()!=3) {
                        return Message.of(rawMessage, "UNKNOWN", "UNKNOWN");
                    }
                    
                    return Message.of(chunks.get(0), chunks.get(1), chunks.get(2));
                    
                })
                .collect(Collectors.toList());
        
        if(messages.isEmpty()) {
            return empty();
        }
        
        return ValidationResponse.of(fullCode, messages, false);
    }
    
    public static ValidationResponse empty() {
        return ValidationResponse.of(null, null, true);
    }

    public static ValidationResponse failure(String fullCode, Exception failureCause) {
        return ValidationResponse.of(
                fullCode, 
                Collections.singletonList(Message.failure(failureCause.getMessage())), false);
    }
    
}
