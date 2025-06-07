package mate.academy.springboot.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.beans.PropertyDescriptor;
import java.util.Objects;
import org.springframework.beans.BeanUtils;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.firstFieldName = constraintAnnotation.first();
        this.secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            PropertyDescriptor firstDescriptor =
                    BeanUtils.getPropertyDescriptor(value.getClass(), firstFieldName);
            PropertyDescriptor secondDescriptor =
                    BeanUtils.getPropertyDescriptor(value.getClass(), secondFieldName);

            if (firstDescriptor == null || secondDescriptor == null) {
                return false;
            }

            Object first = firstDescriptor.getReadMethod().invoke(value);
            Object second = secondDescriptor.getReadMethod().invoke(value);

            return Objects.equals(first, second);
        } catch (Exception e) {
            return false;
        }
    }

}

