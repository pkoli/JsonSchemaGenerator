import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.pkoli.jsonSchema.Food;

import static java.lang.System.err;
import static java.lang.System.out;

/**
 * Created by pkoli on 9/7/17.
 */
public class Main {

    private static void writeToStandardOutputWithDeprecatedJsonSchema(
            final String fullyQualifiedClassName)
    {
        final SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        final ObjectMapper mapper = new ObjectMapper();
        try
        {
            mapper.acceptJsonFormatVisitor(mapper.constructType(Class.forName(fullyQualifiedClassName)), visitor);
            final JsonSchema jsonSchema = visitor.finalSchema();
            out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema));
        }
        catch (ClassNotFoundException cnfEx)
        {
            err.println("Unable to find class " + fullyQualifiedClassName);
        }
        catch (com.fasterxml.jackson.core.JsonProcessingException jsonEx)
        {
            err.println("Unable to map JSON: " + jsonEx);
        }
    }

    public static void main(String[] args) {
        Food food = new Food();
        writeToStandardOutputWithDeprecatedJsonSchema(food.getClass().getCanonicalName());
    }
}
