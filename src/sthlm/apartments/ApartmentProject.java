package sthlm.apartments;

import java.io.Serializable;

public class ApartmentProject implements Serializable {

	private static final long serialVersionUID = 939519361147730645L;
	private String area;
	private String address;
	private String link; 
	private String criteria;
	
	public ApartmentProject(String area, String address, String link,
			String criteria) {
		if (area == null || address == null) {
			throw new IllegalArgumentException("Area and address cannot be null.");
		}
		if (area.isEmpty() || address.isEmpty()) {
			throw new IllegalArgumentException("Area and address cannot be empty.");
		}
		if (area.isEmpty() && address.isEmpty() && link.isEmpty() && criteria.isEmpty()) {
			throw new IllegalArgumentException("Arguments cannot be empty.");
		}

		this.area = area;
		this.address = address;
		this.link = link;
		this.criteria = criteria;
	}

	public String getArea() {
		return area;
	}

	public String getAddress() {
		return address;
	}

	public String getLink() {
		return link;
	}

	public String getCriteria() {
		return criteria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApartmentProject other = (ApartmentProject) obj;
		return this.hashCode() == other.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(area + " ");
		builder.append(address);

		if (criteria != null && !criteria.isEmpty()) {
			builder.append(" | " + criteria);
		}
		
		if (link != null && !link.isEmpty()) {
			builder.append(" | " + link);
		}
		
		return builder.toString();
	}
	
	
}
