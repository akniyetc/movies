package com.silence.movies.domain.entity;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Movies{

	public Movies(String response, List<Movie> movies) {
		this.response = response;
		this.search = movies;
	}

	@SerializedName("Response")
	private String response;

	@SerializedName("Search")
	private List<Movie> search;

	public void setResponse(String response){
		this.response = response;
	}

	public String getResponse(){
		return response;
	}

	public void setSearch(List<Movie> search){
		this.search = search;
	}

	public List<Movie> getSearch(){
		return search;
	}

	@Override
 	public String toString(){
		return 
			"Movies{" + 
			"response = '" + response + '\'' +
			",search = '" + search + '\'' + 
			"}";
		}
}