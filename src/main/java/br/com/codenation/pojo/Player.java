package br.com.codenation.pojo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Player {

	private Long id;
	private String name;
	private LocalDate dateOfBirth;
	private Integer skillLevel;
	private BigDecimal salary;
	
	public Player(Long id, String name, LocalDate dateOfBirth, Integer skillLevel, BigDecimal salary) {
		super();
		this.id = id;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.skillLevel = skillLevel;
		this.salary = salary;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public Integer getSkillLevel() {
		return skillLevel;
	}
	
	public void setSkillLevel(Integer skillLevel) {
		this.skillLevel = skillLevel;
	}

	public BigDecimal getSalary() {
		return salary;
	}
	
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", dateOfBirth=" + dateOfBirth + ", skillLevel=" + skillLevel
				+ ", salary=" + salary;
	}
	
	public static int compareById(Player p1, Player p2) {
        return Long.compare(p1.id, p2.id);
	}
	
	public static int compareByBiggestSalary(Player p1, Player p2) {
	    if (p1.salary.equals(p2.salary)) {
	        return Long.compare(p1.id, p2.id);
	    } else {
	        return  p2.salary.compareTo(p1.salary);
	    }
	}
	
	public static int compareByOldestPlayer(Player p1, Player p2) {
		if(p1.getDateOfBirth() == p2.getDateOfBirth()) {
			return Long.compare(p1.id, p2.id);
		} else {
			return p1.getDateOfBirth().compareTo(p2.getDateOfBirth());
		}
	}
	
	public static int compareByBestPlayer(Player p1, Player p2) {
	    if (p1.skillLevel.equals(p2.skillLevel)) {
	        return Long.compare(p1.id, p2.id);
	    } else {
	        return  p2.skillLevel.compareTo(p1.skillLevel);
	    }
	}
}
