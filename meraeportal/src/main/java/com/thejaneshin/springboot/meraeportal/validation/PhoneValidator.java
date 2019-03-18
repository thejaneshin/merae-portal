package com.thejaneshin.springboot.meraeportal.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {
	private Pattern pattern;
	private Matcher matcher;
	private static final String PHONE_PATTERN = "^\\d{3}-\\d{3}-\\d{4}$";
	
	@Override
	public boolean isValid(final String phone, final ConstraintValidatorContext context) {
		pattern = Pattern.compile(PHONE_PATTERN);
		if (phone == null) {
			return false;
		}
		matcher = pattern.matcher(phone);
		return matcher.matches();
	}
	
}
