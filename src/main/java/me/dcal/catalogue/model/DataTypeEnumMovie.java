package me.dcal.catalogue.model;

public enum DataTypeEnumMovie {
	ALL("?Name ?Director ?Thumbnail  ?Starring"),NAME("?Name")
	,DIRECTOR("?Director"),THUMBNAIL("?Thumbnail"),UNIQUE("?Name ?Director")
	,STARRING("?Starring"),EMPTY("");

	private String query;

	private DataTypeEnumMovie(String query) {
		this.setQuery(query);
	}
	public String getQuery() {
		return query;
	}
	void setQuery(String query) {
		this.query = query;
	}

}
