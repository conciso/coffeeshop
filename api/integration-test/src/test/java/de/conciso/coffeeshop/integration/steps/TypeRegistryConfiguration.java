package de.conciso.coffeeshop.integration.steps;

import static java.util.Locale.GERMAN;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.conciso.coffeeshop.integration.ObjectMapperProducer;
import io.cucumber.core.api.TypeRegistry;
import io.cucumber.core.api.TypeRegistryConfigurer;
import io.cucumber.cucumberexpressions.ParameterByTypeTransformer;
import io.cucumber.cucumberexpressions.ParameterType;
import io.cucumber.cucumberexpressions.Transformer;
import io.cucumber.datatable.TableCellByTypeTransformer;
import io.cucumber.datatable.TableEntryByTypeTransformer;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TypeRegistryConfiguration implements TypeRegistryConfigurer {

  @Override
  public Locale locale() {
    return GERMAN;
  }

  @Override
  public void configureTypeRegistry(TypeRegistry typeRegistry) {
    DefaultTransformer defaultTransformer = new DefaultTransformer();
    typeRegistry.setDefaultDataTableCellTransformer(defaultTransformer);
    typeRegistry.setDefaultDataTableEntryTransformer(defaultTransformer);
    typeRegistry.setDefaultParameterTransformer(defaultTransformer);

    typeRegistry.defineParameterType(new ParameterType<List>(
        "idList",
        "\"((\\w+)(,)?)+\"",
        List.class,
        new Transformer<List>() {

          @Override
          public List<String> transform(String arg) throws Throwable {
            return Arrays.asList(arg.split(","));
          }
        }
    ));
  }

  private static class DefaultTransformer implements
      ParameterByTypeTransformer,
      TableEntryByTypeTransformer,
      TableCellByTypeTransformer {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapperProducer()
        .produceObjectMapper();

    @Override
    public Object transform(String toTransform, Type type) {
      return OBJECT_MAPPER.convertValue(toTransform, OBJECT_MAPPER.constructType(type));
    }

    @Override
    public <T> T transform(Map<String, String> map, Class<T> clazz,
        TableCellByTypeTransformer tableCellByTypeTransformer) {
      return OBJECT_MAPPER.convertValue(map, clazz);
    }

    @Override
    public <T> T transform(String toTransform, Class<T> clazz) throws IllegalArgumentException {
      return OBJECT_MAPPER.convertValue(toTransform, clazz);
    }
  }
}
