package webservice.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Indicators {

	private String birthRate;		// SP.DYN.CBRT.IN Birth rate, crude (per 1,000 people)
	private String deathRate;		// SP.DYN.CDRT.IN Death rate, crude (per 1,000 people)
	private String lifeExpectancyFemale;	// SP.DYN.LE00.FE.IN Life expectancy at birth, female (years)
	private String lifeExpectancyMale;	// SP.DYN.LE00.MA.IN Life expectancy at birth, male (years)
	
	/**
	 * @return the birthRate
	 */
	public String getBirthRate() {
		return birthRate;
	}
	/**
	 * @param birthRate the birthRate to set
	 */
	public void setBirthRate(final String birthRate) {
		this.birthRate = birthRate;
	}
	/**
	 * @return the deathRate
	 */
	public String getDeathRate() {
		return deathRate;
	}
	/**
	 * @param deathRate the deathRate to set
	 */
	public void setDeathRate(final String deathRate) {
		this.deathRate = deathRate;
	}
	/**
	 * @return the lifeExpectancyFemale
	 */
	public String getLifeExpectancyFemale() {
		return lifeExpectancyFemale;
	}
	/**
	 * @param lifeExpectancyFemale the lifeExpectancyFemale to set
	 */
	public void setLifeExpectancyFemale(final String lifeExpectancyFemale) {
		this.lifeExpectancyFemale = lifeExpectancyFemale;
	}
	/**
	 * @return the lifeExpectancyMale
	 */
	public String getLifeExpectancyMale() {
		return lifeExpectancyMale;
	}
	/**
	 * @param lifeExpectancyMale the lifeExpectancyMale to set
	 */
	public void setLifeExpectancyMale(final String lifeExpectancyMale) {
		this.lifeExpectancyMale = lifeExpectancyMale;
	}
	
	
	
}
