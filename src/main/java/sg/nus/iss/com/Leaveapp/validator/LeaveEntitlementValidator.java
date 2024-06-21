package sg.nus.iss.com.Leaveapp.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import sg.nus.iss.com.Leaveapp.model.LeaveEntitlement;

@Component
public class LeaveEntitlementValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return LeaveEntitlement.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LeaveEntitlement leaveEntitlement = (LeaveEntitlement) target;
		
		
	}

}
