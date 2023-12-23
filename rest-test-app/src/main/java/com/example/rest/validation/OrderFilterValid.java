package com.example.rest.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OrderFilterValidValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderFilterValid {

    String message() default "Pageable fields should be filled! If you pun min- or max- cost, both of them should be filled";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
