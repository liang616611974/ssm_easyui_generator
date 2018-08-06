package cn.org.rapid_framework.generator.provider.db.table.model.util;

import cn.org.rapid_framework.generator.provider.db.table.model.Column;
import cn.org.rapid_framework.generator.util.typemapping.DatabaseDataTypesUtils;
import org.apache.commons.lang3.StringUtils;

public class ColumnHelper {
	
	public static String[] removeHibernateValidatorSpecialTags(String str) {
		if(str == null || str.trim().length() == 0) return new String[]{};
		return str.trim().replaceAll("@", "").replaceAll("\\(.*?\\)", "").trim().split("\\s+");
	}
    	
	/** 得到JSR303 bean validation的验证表达式 */
	public static String getHibernateValidatorExpression(Column c) {
		if(!c.isPk() && !c.isNullable()) {
			if(DatabaseDataTypesUtils.isString(c.getJavaType())) {
				if(StringUtils.isNoneBlank(getNotRequiredHibernateValidatorExpression(c))){
					return  "@NotBlank " + "\r\n    " + getNotRequiredHibernateValidatorExpression(c);
				}else {
					return  "@NotBlank";
				}
			}else {
				if(StringUtils.isNoneBlank(getNotRequiredHibernateValidatorExpression(c))){
					return  "@NotNull " + "\r\n    " + getNotRequiredHibernateValidatorExpression(c);
				}else {
					return  "@NotNull";
				}
			}
		}else {
			return getNotRequiredHibernateValidatorExpression(c);
		}
	}

	public static String getNotRequiredHibernateValidatorExpression(Column c) {
		String result = "";
		
		if(DatabaseDataTypesUtils.isString(c.getJavaType())) {
			result += String.format("@Length(max=%s)",c.getSize()) + "\r\n    ";
		}
		if(DatabaseDataTypesUtils.isIntegerNumber(c.getJavaType())) {
			String javaType = DatabaseDataTypesUtils.getPreferredJavaType(c.getSqlType(), c.getSize(), c.getDecimalDigits());
			if(javaType.toLowerCase().indexOf("short") >= 0) {
				result += " @Max("+Short.MAX_VALUE+")" + "\r\n    ";
			}else if(javaType.toLowerCase().indexOf("byte") >= 0) {
				result += " @Max("+Byte.MAX_VALUE+")" + "\r\n    ";
			}
		}
		if(c.getSqlName().indexOf("email") >= 0) {
			result += "@Validator(checkType = CheckType.EMAIL, message=\"不是合法的电子邮箱\")";
		}
		if(c.getSqlName().indexOf("mobile") >= 0) {
			result += "@Validator(checkType = CheckType.MOBILE, message=\"不是合法的手机号码\")";
		}
		if(c.getSqlName().indexOf("idcard") >= 0) {
			result += "@Validator(checkType = CheckType.IDCARD, message=\"不是合法的身份证号码\")";
		}
		return result.trim();
	}
	
	/** 得到rapid validation的验证表达式 */
	public static String getRapidValidation(Column c) {
		String result = "";
		if(c.getSqlName().indexOf("mail") >= 0) {
			result += "validate-email ";
		}
		if(DatabaseDataTypesUtils.isFloatNumber(c.getJavaType())) {
			result += "validate-number ";
		}
		if(DatabaseDataTypesUtils.isIntegerNumber(c.getJavaType())) {
			result += "validate-integer ";
			if(c.getJavaType().toLowerCase().indexOf("short") >= 0) {
				result += "max-value-"+Short.MAX_VALUE;
			}else if(c.getJavaType().toLowerCase().indexOf("integer") >= 0) {
				result += "max-value-"+Integer.MAX_VALUE;
			}else if(c.getJavaType().toLowerCase().indexOf("byte") >= 0) {
				result += "max-value-"+Byte.MAX_VALUE;
			}
		}
		return result;
	}
}
