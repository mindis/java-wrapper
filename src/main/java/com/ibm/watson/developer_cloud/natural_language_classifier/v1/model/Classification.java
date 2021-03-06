/**
 * Copyright 2015 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ibm.watson.developer_cloud.natural_language_classifier.v1.model;

import java.util.List;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;

/**
 * Classification class used by the {@link NaturalLanguageClassifier} service
 * 
 * @author German Attanasio Ruiz <germanatt@us.ibm.com>
 */
public class Classification {

	@SerializedName("classifier_id")
	private String id;
	private String url;
	private String text;

	@SerializedName("top_class")
	private String topClass;
	private List<ClassifiedClass> classes;

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Sets the url.
	 * 
	 * @param url
	 *            the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Sets the text.
	 * 
	 * @param text
	 *            the new text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Sets the top class.
	 * 
	 * @param top class
	 *            the new top class
	 */
	public void setTopClass(String topClass) {
		this.topClass = topClass;
	}

	/**
	 * Sets the classes.
	 * 
	 * @param classes
	 *            the new classes
	 */
	public void setClasses(List<ClassifiedClass> classes) {
		this.classes = classes;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the url.
	 * 
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Gets the text.
	 * 
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Gets the top lass.
	 * 
	 * @return the top class
	 */
	public String getTopClass() {
		return topClass;
	}

	/**
	 * Gets the classes.
	 * 
	 * @return the classes
	 */
	public List<ClassifiedClass> getClasses() {
		return classes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + " "
				+ new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}
}
