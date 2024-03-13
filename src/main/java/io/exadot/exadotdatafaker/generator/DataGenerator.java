package io.exadot.exadotdatafaker.generator;

import io.exadot.exadotdatafaker.entity.Field;
import io.exadot.exadotdatafaker.entity.FilterParam;
import io.exadot.exadotdatafaker.controller.exceptions.InvalidFakerResources;
import io.exadot.exadotdatafaker.controller.exceptions.ResourceNotFoundException;
import io.exadot.exadotdatafaker.service.dto.FieldDto;
import io.exadot.exadotdatafaker.service.dto.FilterParamsDto;
import io.exadot.exadotdatafaker.service.dto.enums.FilterParamStatus;
import net.datafaker.Faker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class DataGenerator {
    private static final Faker faker = new Faker();

    public static List<Map<String, Object>> generate(List<FieldDto> fields, int count) throws InvocationTargetException {
        List<Map<String, Object>> result = new ArrayList<>();

        for (int i = 0; i < count; i++) {

            Map<String, Object> data = new LinkedHashMap<>();
            for (FieldDto value : fields) {
                String type = value.getGenerateBaseType();
                String field = value.getGenerateValue();
                String fieldName = value.getFieldName();
                data.put(fieldName, generator(type, field, value));
            }

            result.add(data);
        }

        return result;
    }

    /**
     * Generates a result word based on the given function type, function name, and modelDto.
     *
     * @param fType the type of the function to be generated
     * @param fName the name of the function to be generated
     * @param field the modelDto containing filter parameters
     * @return the generated result word
     */
    private static Object generator(String fType, String fName, FieldDto field) throws InvocationTargetException {
//        System.out.println(faker.phoneNumber().phoneNumber());
        Optional<Method> baseMethods;

        try {
            try {
                baseMethods = Optional.of(faker.getClass().getMethod(fType));
            } catch (Exception e) {
                throw new InvalidFakerResources("Class '" + fType + "' not found");
            }

            baseMethods.get().setAccessible(true);
            Object o = Faker.class.getConstructor().newInstance();

            Object returnValue = baseMethods.orElseThrow(() ->
                    new InvalidFakerResources("Method " + fName + " not found")).invoke(o);

            Optional<Method[]> methods = Optional.of(Arrays.stream(returnValue.getClass().getMethods())
                    .filter(method -> method.getName().equals(fName)).toArray(Method[]::new));

            if (methods.get().length > 1) {
                String[] expectedTypes = field.getFilterParams()
                        .stream().filter(p -> p.getFilterParamStatus() == FilterParamStatus.ACTIVE).map(FilterParamsDto::getValueType).toArray(String[]::new);

                Class<?>[] expectedParams = parameterTypes(expectedTypes);

                Optional<Method> matchMethods = Arrays.stream(Arrays.stream(methods.orElseThrow(
                                () -> new InvalidFakerResources("Method " + fName + " not found"))).toArray(Method[]::new))
                        .filter(method -> isParameterMatch(method.getParameterTypes(),
                                expectedParams)).findFirst();

                matchMethods.orElseThrow(() ->
                        new InvalidFakerResources("Method " + fName + " not found")).setAccessible(true);

                Object[] parameterized = parameters(matchMethods.orElseThrow(), field);

                return matchMethods.orElseThrow(() ->
                        new InvalidFakerResources("Method " + fName + " not found")).invoke(returnValue, parameterized);

            } else if (methods.get().length == 0) {
                throw new InvalidFakerResources("Class '" + fType + "' has no method " + fName);
            }
            return methods.get()[0].invoke(returnValue);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException |
                 InvalidFakerResources e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }


    private static boolean isParameterMatch(Class<?>[] parameterTypes, Class<?>[] expectedTypes) {
        if (parameterTypes.length != expectedTypes.length) {
            return false;
        }
        for (int i = 0; i < parameterTypes.length; i++) {
            if (!parameterTypes[i].equals(expectedTypes[i])) {
                return false;
            }
        }

        return true;
    }

    private static Object[] parameters(Method method, FieldDto field) {
        Parameter[] parameters = method.getParameters();
        Object[] objects = new Object[parameters.length];

        try {
            for (int i = 0; i < parameters.length; i++) {
                int finalI = i;
                objects[i] = typeCast(parameters[i].getType().getSimpleName(),
                        field.getFilterParams().get(i).getValue())
                        .orElseThrow(
                                () -> new InvalidFakerResources("Parameter " + parameters[finalI].getName() + " not found")
                        );
            }
        } catch (InvalidFakerResources e) {
            throw new RuntimeException(e.getMessage());
        }

        return objects;
    }

    private static Class<?>[] parameterTypes(String[] parameters) {
        Class<?>[] classes = new Class[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].toLowerCase().startsWith("str"))
                classes[i] = String.class;
            else if (parameters[i].toLowerCase().startsWith("int"))
                classes[i] = int.class;
            else if (parameters[i].toLowerCase().startsWith("dou"))
                classes[i] = double.class;
            else if (parameters[i].toLowerCase().startsWith("bool"))
                classes[i] = boolean.class;
            else if (parameters[i].toLowerCase().startsWith("long"))
                classes[i] = long.class;
        }

        return classes;
    }

    private static Optional<Object> typeCast(String clazz, String str) throws InvalidFakerResources {
        Optional<Object> obj = Optional.empty();

        try {
            if (clazz.toLowerCase().startsWith("str"))
                obj = Optional.of(str);
            else if (clazz.toLowerCase().startsWith("int"))
                obj = Optional.of(Integer.parseInt(str));
            else if (clazz.toLowerCase().startsWith("dou"))
                obj = Optional.of(Double.parseDouble(str));
            else if (clazz.toLowerCase().startsWith("bool"))
                obj = Optional.of(Boolean.parseBoolean(str));
            else if (clazz.toLowerCase().startsWith("long"))
                obj = Optional.of(Long.parseLong(str));
        } catch (Exception e) {
            throw new InvalidFakerResources("type Cast Exception\n" + e.getMessage());
        }

        return obj;
    }
}
