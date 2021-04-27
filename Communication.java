package application;

public class Communication {

	private String country = null;
	private String population = null;
	private String electricityProduction = null;
	private String electricityConsumption = null;
	public Communication(String country, String population, String electricityProduction,
			String electricityConsumption) {
		this.country = country;
		this.population = population;
		this.electricityProduction = electricityProduction;
		this.electricityConsumption = electricityConsumption;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPopulation() {
		return population;
	}
	public void setPopulation(String population) {
		this.population = population;
	}
	public String getElectricityProduction() {
		return electricityProduction;
	}
	public void setElectricityProduction(String electricityProduction) {
		this.electricityProduction = electricityProduction;
	}
	public String getElectricityConsumption() {
		return electricityConsumption;
	}
	public void setElectricityConsumption(String electricityConsumption) {
		this.electricityConsumption = electricityConsumption;
	}
	
	
}
