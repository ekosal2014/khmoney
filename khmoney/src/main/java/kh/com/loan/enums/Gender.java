package kh.com.loan.enums;

public enum Gender {
	Male("1"),
	Female("2")
	;
	
	private String value;
	private Gender(String value){
		this.value = value;
	}
	public String getValue(){
		return this.value;
	}
	
	public Gender fromValue(String value){
		for(Gender my: Gender.values()){
			if (my.value == value){
				return my;
			}
		}
		return null;
	}
	public String getLabel(){
		
		String label = "";
		if ("1".equals(value)){
			label = "ACTIVE";
		}else if ("2".equals(value)){
			label = "INACTIVE";
		}else{
			label = "DELETE";
		}
		
		return label;
	}
}
