package com.sourabh.businessService;

import java.util.ArrayList;

import com.sourabh.entity.Category;
import com.sourabh.entity.Subcategory;
import com.sourabh.entity.Subcategory2;
import com.sourabh.entity.Subcategory3;

public interface ICategoriesService {

	public ArrayList<Category> getCategories(String lastCategoryId);
	public ArrayList<Subcategory> getSubcategories(String lastSubCategoryId);
	public ArrayList<Subcategory2> getSubcategories2(String lastSubCategory2Id);
	public ArrayList<Subcategory3> getSubcategories3(String lastSubcategory3Id);
	
}
