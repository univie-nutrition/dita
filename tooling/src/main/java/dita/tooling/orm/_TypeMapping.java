package dita.tooling.orm;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import org.apache.causeway.commons.internal.exceptions._Exceptions;

import lombok.experimental.UtilityClass;

@UtilityClass
class _TypeMapping {

    TypeName dbToJava(final String typeName) {
        if(typeName.startsWith("nvarchar")) {
            return ClassName.get("java.lang", "String");
        }
        if(typeName.equals("smallint")
                || typeName.equals("tinyint")) {
            return TypeName.INT;
        }
        if(typeName.equals("datetime")) {
            return ClassName.get("java.sql", "Timestamp");
        }
        throw _Exceptions.unmatchedCase(typeName);
    }

}
