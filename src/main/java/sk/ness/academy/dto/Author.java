package sk.ness.academy.dto;

public class Author {

	//BONUS
	public Author(String name) {
		this.name = name;
	}
	//BONUS

	private String name;

	public String getName() {
	    return this.name;
	}

	public void setName(final String name) {
	    this.name = name;
	}

}
