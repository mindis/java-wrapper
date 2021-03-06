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

package com.ibm.watson.developer_cloud.speech_to_text.v1.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class SpeechModelSet {

	@Expose
	private List<SpeechModel> models = new ArrayList<SpeechModel>();

	/**
	 * Gets the models.
	 * 
	 * @return The models
	 */
	public List<SpeechModel> getModels() {
		return models;
	}

	/**
	 * Sets the models.
	 * 
	 * @param models
	 *            The models
	 */
	public void setModels(List<SpeechModel> models) {
		this.models = models;
	}

	/**
	 * With models.
	 * 
	 * @param models
	 *            the models
	 * @return the speech models
	 */
	public SpeechModelSet withModels(List<SpeechModel> models) {
		this.models = models;
		return this;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getName() + " "
				+ new GsonBuilder().setPrettyPrinting().create().toJson(this);
	}
}
